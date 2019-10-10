package br.com.fiap.servicos.controller;

import br.com.fiap.servicos.model.Cliente;
import br.com.fiap.servicos.service.ServicosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/clientes")
@Api(tags="clientes service")
public class ServicosController {

    @Autowired
    private ServicosService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="busca todos os clientes")
    public ResponseEntity<Page<Cliente>> getClientes(Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        Page<Cliente> clientes = clienteService.findAll(pageable);
        return new ResponseEntity<Page<Cliente>>(clientes, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="busca um cliente")
    public ResponseEntity<Cliente> getOneCliente(@PathVariable final Long id) {
        Cliente cliente = clienteService.findById(id);
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value="adiciona um cliente")
    public ResponseEntity<Cliente> addCliente(@RequestBody String cliente) {
        Cliente aCliente = null;
        try {
            aCliente = objectMapper.readValue(cliente, Cliente.class);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Cliente novoCliente = clienteService.addCliente(aCliente);
        if (novoCliente != null) {
            return new ResponseEntity<Cliente>(novoCliente, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
