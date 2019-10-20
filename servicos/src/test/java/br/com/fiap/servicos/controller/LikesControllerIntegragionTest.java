package br.com.fiap.servicos.controller;

import br.com.fiap.servicos.TestConfiguration;
import br.com.fiap.servicos.TestUtil;
import br.com.fiap.servicos.model.Cliente;
import br.com.fiap.servicos.model.Filme;
import br.com.fiap.servicos.model.Like;
import br.com.fiap.servicos.repository.LikesRepository;
import br.com.fiap.servicos.service.LikesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "server.port:0",
        "eureka.client.enabled:false",
        "spring.application.name=servicos",
        "spring.cloud.discovery.client.simple.instances.clientes[0].uri=http://localhost:8888",
        "spring.cloud.discovery.client.simple.instances.filmes[0].uri=http://localhost:8888"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LikesControllerIntegragionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LikesRepository repo;

    @Autowired
    private MockMvc mvc;

    Like like;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Value("${fanout.exchange.name}")
    String fanoutExchangeName;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8888);

    private static final String QUEUE_NAME = "filmes_events_queue";

    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
        like = new Like(1L, 1L);

        //like = repo.save(like);
        Cliente cli1 = new Cliente(1L, "cliente1", "1234567890", 25);
        Filme fil1 = new Filme(2L, "Back To The Future", 1984, "ficção", "ingles", "filme", 0, 0);
        ObjectMapper om = new ObjectMapper();

        wireMockRule
                .stubFor(get(urlPathMatching("/v1/clientes/[0-9]+"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200)
                                .withBody(om.writeValueAsString(cli1)))
                );
        wireMockRule
                .stubFor(get(urlPathMatching("/v1/filmes/[0-9]+"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200)
                                .withBody(om.writeValueAsString(fil1)))
                );

    }

    @After
    public void clear() throws SQLException {
        repo.deleteAll();
        TestUtil.cleanupDatabase(dataSource);
    }

    @Test
    public void shouldMarkALikeOnAFilme() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class)) {

            queueAndExchangeSetup(context);

            MvcResult result = mvc.perform(post("/v1/likes")
                    .content(TestUtil.asJsonString(like))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(notNullValue())))
//                    .andExpect(jsonPath("$.dataAbertura", is(notNullValue())))
                    .andDo(print())
                    .andReturn()
                    ;

            Message message = rabbitTemplate.receive(QUEUE_NAME);

            boolean res = repo.existsByClienteIdAndFilmeId(1L, 1L);

            assertTrue(res);
            assertThat(message).isNotNull();

            wireMockRule.verify(1, getRequestedFor(urlPathEqualTo("/v1/clientes/1")));
            wireMockRule.verify(1, getRequestedFor(urlPathEqualTo("/v1/filmes/1")));
        }

    }

    @Test
    public void shouldUnMarkALikeOnAFilme() throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class)) {

            queueAndExchangeSetup(context);

            like = new Like(1L, 1L);
            repo.save(like);

            mvc.perform(post("/v1/likes")
                    .content(TestUtil.asJsonString(like))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(notNullValue())))
//                    .andExpect(jsonPath("$.dataAbertura", is(notNullValue())))
                    .andDo(print())
                    .andReturn()
                    ;

            Message message = rabbitTemplate.receive(QUEUE_NAME);

            boolean res = repo.existsByClienteIdAndFilmeId(1L, 1L);

            assertFalse(res);
            assertThat(message).isNotNull();

            wireMockRule.verify(1, getRequestedFor(urlPathEqualTo("/v1/clientes/1")));
            wireMockRule.verify(1, getRequestedFor(urlPathEqualTo("/v1/filmes/1")));
        }

    }
    private void queueAndExchangeSetup(BeanFactory context) {

        Queue queue = new Queue(QUEUE_NAME, false);
        rabbitAdmin.declareQueue(queue);
        FanoutExchange exchange = new FanoutExchange(fanoutExchangeName);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));

    }

}
