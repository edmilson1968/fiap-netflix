package br.com.fiap.servicos.service;

import br.com.fiap.servicos.repository.AssistidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssistidosService {

    @Autowired
    private AssistidosRepository assistidosRepository;

}
