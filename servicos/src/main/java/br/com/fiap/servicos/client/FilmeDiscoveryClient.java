package br.com.fiap.servicos.client;

import br.com.fiap.servicos.model.Filme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FilmeDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    public Filme findFilmeById(long filmeId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("filmes");

        if (instances.size() == 0) {
            return null;
        }

        String serviceUri = String.format("%s/v1/filmes/%d", instances.get(0).getUri().toString(), filmeId);

        ResponseEntity<Filme> restExchange =
                restTemplate.getForEntity(serviceUri, Filme.class);

        return restExchange.getBody();
    }

}
