package br.com.fiap.filmes.amqp;

import br.com.fiap.filmes.model.Assistir;
import br.com.fiap.filmes.model.Filme;
import br.com.fiap.filmes.repository.AssistirRepository;
import br.com.fiap.filmes.repository.FilmeRepository;
import br.com.fiap.filmes.service.FilmeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RabbitListener(queues = "filmes_events_queue")
public class Receiver {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AssistirRepository assistirRepository;

    @Autowired
    FilmeRepository filmeRepository;

    @RabbitHandler
    public void process(byte[] event) throws IOException {
        Map<String, String> payload = objectMapper.readValue(new String(event), new TypeReference<Map<String, String>>(){});

        String tipo = payload.getOrDefault("tipo", "");
        int val = Integer.valueOf(payload.getOrDefault("val", "0"));
        if ("likes".equals(tipo)) {
            Long filmeId = Long.valueOf(payload.get("filmeId"));
            Optional<Filme> filme = filmeRepository.findById(filmeId);
            if (filme.isPresent()) {
                Filme fil = filme.get();
                if (fil.getLikes() > 0)
                    fil.setLikes(fil.getLikes() + val);
                filmeRepository.save(fil);
            }
        }

//        System.out.println(watch);
//
//        assistirRepository.save(watch);
    }

    @RabbitHandler
    public void processJson(Assistir json) {
        System.out.println(json);
    }
}
