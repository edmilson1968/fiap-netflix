package br.com.fiap.servicos.controller;

import br.com.fiap.servicos.model.FilmeClienteAssistidos;
import br.com.fiap.servicos.repository.AssistidosRepository;
import br.com.fiap.servicos.service.AssistidosService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/assistidos")
@Api(tags="serviço de marcacao de filmes assistidos")
public class AssistidosController {

    @Autowired
    AssistidosService assistidosService;

    @PostMapping
    public ResponseEntity<?> marcar(@RequestBody FilmeClienteAssistidos filmeClienteAssistidos) throws Exception {
        filmeClienteAssistidos = assistidosService.marcar(filmeClienteAssistidos.getClienteId(), filmeClienteAssistidos.getFilmeId());
        return new ResponseEntity<>(filmeClienteAssistidos, HttpStatus.OK);
    }

}
