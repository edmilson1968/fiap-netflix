package br.com.fiap.servicos.service;

import br.com.fiap.servicos.amqp.SendLikeMessage;
import br.com.fiap.servicos.client.ClienteDiscoveryClient;
import br.com.fiap.servicos.client.FilmeDiscoveryClient;
import br.com.fiap.servicos.model.Cliente;
import br.com.fiap.servicos.model.Filme;
import br.com.fiap.servicos.model.FilmeClienteLikes;
import br.com.fiap.servicos.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikesService {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private FilmeDiscoveryClient filmeDiscoveryClient;

    @Autowired
    private ClienteDiscoveryClient clienteDiscoveryClient;

    @Autowired
    private SendLikeMessage sendLikeMessage;

    public FilmeClienteLikes marcar(long clienteId, long filmeId) throws Exception {
        Cliente cliente = clienteDiscoveryClient.findClienteById(clienteId);
        Filme filme = filmeDiscoveryClient.findFilmeById(filmeId);

        FilmeClienteLikes filmesalvo = null;
        FilmeClienteLikes filmeClienteLikes = new FilmeClienteLikes(clienteId, filmeId);
        if (likesRepository.existsByClienteIdAndFilmeId(clienteId, filmeId)) {
            filmesalvo = likesRepository.findByClienteIdAndFilmeId(clienteId, filmeId);
            likesRepository.deleteById(filmesalvo.getId());
            sendLikeMessage.sendMessage(filmeId, -1);
        } else {
            filmesalvo = likesRepository.save(filmeClienteLikes);
            sendLikeMessage.sendMessage(filmeId, 1);
        }
        return filmesalvo;
    }

    public void desmarcar(Long clienteId, Long filmeId) {
        Cliente cliente = clienteDiscoveryClient.findClienteById(clienteId);
        Filme filme = filmeDiscoveryClient.findFilmeById(filmeId);
        FilmeClienteLikes filmeClienteLikes = new FilmeClienteLikes(clienteId, filmeId);
        likesRepository.delete(filmeClienteLikes);
    }
}
