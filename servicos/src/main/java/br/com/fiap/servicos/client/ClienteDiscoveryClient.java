package br.com.fiap.servicos.client;

import br.com.fiap.servicos.model.Cliente;
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
public class ClienteDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    public Cliente findClienteById(long clienteId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("clientes");

        if (instances.size() == 0) {
            return null;
        }

        String serviceUri = String.format("%s/v1/clientes/%d", instances.get(0).getUri().toString(), clienteId);

        ResponseEntity<Cliente> restExchange =
                restTemplate.getForEntity(serviceUri, Cliente.class);

        return restExchange.getBody();
    }

}
