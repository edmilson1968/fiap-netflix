package br.com.fiap.filmes.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Arrays;
import java.util.stream.Collectors;

@RabbitListener(queues = "filmes_events_queue")
public class Receiver {

    @RabbitHandler
    public void process(byte[] event) {
        System.out.println(new String(event));
    }
}
