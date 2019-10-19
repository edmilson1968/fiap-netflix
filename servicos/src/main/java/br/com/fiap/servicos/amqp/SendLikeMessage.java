package br.com.fiap.servicos.amqp;

import br.com.fiap.servicos.model.FilmeClienteLikes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendLikeMessage {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public SendLikeMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Long filmeId, int val) throws JsonProcessingException {
        Map<String, String> payload = new HashMap<>();
        payload.put("tipo", "likes");
        payload.put("filmeId", String.valueOf(filmeId));
        payload.put("val", String.valueOf(val));
        String msg = objectMapper.writeValueAsString(payload);
        Message message = MessageBuilder
                .withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        this.rabbitTemplate.convertAndSend("filmes_events_queue", message);
    }
}