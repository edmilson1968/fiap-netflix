package br.com.fiap.clientes.client;

import br.com.fiap.clientes.model.Chamado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ChamadoDiscoveryClient {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    public Chamado openTicket(Chamado chamado) {
        List<ServiceInstance> instances = discoveryClient.getInstances("servicos");

        if (instances.size() == 0) {
            return null;
        }

        String serviceUri = String.format("%s/v1/chamados", instances.get(0).getUri().toString());

        ResponseEntity<Chamado> restExchange =
                restTemplate.postForEntity(serviceUri, chamado, Chamado.class);

        return restExchange.getBody();
    }
}
