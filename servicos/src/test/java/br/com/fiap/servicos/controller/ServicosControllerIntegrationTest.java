package br.com.fiap.servicos.controller;

import br.com.fiap.servicos.TestUtil;
import br.com.fiap.servicos.model.Cliente;
import br.com.fiap.servicos.repository.ServicosRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ServicosControllerIntegrationTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ServicosRepository repo;

    @Autowired
    private MockMvc mvc;

    private Page<Cliente> page = null;

    List<Cliente> clientes = null;
    Cliente cli1, cli2;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll();

        cli1 = new Cliente(null, "cliente1", "1234567890", 25);
        cli2 = new Cliente(null, "cliente2", "0987654321", 32);

        clientes = Arrays.asList(cli1, cli2);

        repo.saveAll(clientes);
    }

    @After
    public void clear() throws SQLException {
        repo.deleteAll();
        TestUtil.cleanupDatabase(dataSource);
    }

    @Test
    public void shoudRetrieveAllClientes() throws Exception {
        mvc.perform(get("/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[*].id", hasSize(clientes.size())))
                .andExpect(jsonPath("$.content[*].nome", hasSize(clientes.size())))
                .andExpect(jsonPath("$.content[0].nome", is(cli1.getNome())))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andDo(print())
                .andReturn()
                ;
    }

    @Test
    public void shoudRetrieveClienteById() throws Exception {
        mvc.perform(get("/v1/clientes/{id}", cli1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                ;
    }

    @Test
    public void shoudThrowClienteNotFoundExceptionWhenFindClienteById() throws Exception {
        mvc.perform(get("/v1/clientes/{id}", 0)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.details").value(org.hamcrest.Matchers.containsString("/v1/clientes/0")))
                .andDo(print())
        ;
    }

    @Test
    public void shouldAddClienteAndVerify() throws Exception {
        Cliente cli3 = new Cliente(null, "cliente3", "000000000", 43);

        MvcResult result = mvc.perform(post("/v1/clientes")
                .content(TestUtil.asJsonString(cli3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.nome", is(cli3.getNome())))
                .andDo(print())
                .andReturn()
                ;

        String response = result.getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mvc.perform(get("/v1/clientes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.nome", is(cli3.getNome())))
                .andDo(print())
                .andReturn()
        ;
    }


}
