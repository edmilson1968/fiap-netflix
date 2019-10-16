package br.com.fiap.filmes.amqp;

import br.com.fiap.filmes.model.Assistir;
import br.com.fiap.filmes.repository.AssistirRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@RabbitListener(queues = "filmes_events_queue")
public class Receiver {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AssistirRepository assistirRepository;

    @RabbitHandler
    public void process(byte[] event) throws IOException {
        Assistir watch = objectMapper.readValue(new String(event), Assistir.class);

        System.out.println(watch);

        assistirRepository.save(watch);
    }

    @RabbitHandler
    public void processJson(Assistir json) {
        System.out.println(json);
    }
}
