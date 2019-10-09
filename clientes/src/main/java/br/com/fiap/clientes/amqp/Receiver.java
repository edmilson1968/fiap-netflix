package br.com.fiap.clientes.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Arrays;
import java.util.stream.Collectors;

@RabbitListener(queues = "events")
public class Receiver {

    @RabbitHandler
    public void process(String event) {
    }
}
