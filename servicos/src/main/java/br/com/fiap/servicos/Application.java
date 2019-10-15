package br.com.fiap.servicos;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class Application {

    private static final Logger LOG= Logger.getLogger( Application.class.getName() );

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/hello_clientes")
    public String hello() {
        LOG.log(Level.INFO, "requested Service 'clientes'");
        return "Hello from service 'clientes'.";
    }

    @Bean
    public Exchange fanoutExchange(@Value("${fanout.exchange.name}") final String exchangeName) {
        return new FanoutExchange(exchangeName);
    }
}

