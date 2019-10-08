package br.com.fiap.filmes.controller;

import br.com.fiap.filmes.model.Filme;
import br.com.fiap.filmes.service.FilmeService;
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
@RequestMapping("/v1/filmes")
@Api(tags="filmes service")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="busca todos os filmes. Também utiliza os parâmetros genero e título.")
    public ResponseEntity<Page<Filme>> getFilmes(
            Pageable pageable,
            @RequestParam(value="genero", required=false) String genero,
            @RequestParam(value="titulo", required=false) String titulo) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        Page<Filme> clientes = null;
        if (genero != null && ! genero.isEmpty())
            clientes = filmeService.findAllByGenero(pageable, genero);
        else if (titulo != null && ! titulo.isEmpty())
            clientes = filmeService.findAllLikeTitulo(pageable, titulo);
        else
            clientes = filmeService.findAll(pageable);

        return new ResponseEntity<Page<Filme>>(clientes, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE} )
    @ApiOperation(value="busca um filme")
    public ResponseEntity<Filme> getOneFilme(@PathVariable final Long id) {
        Filme filme = filmeService.findById(id);
        return new ResponseEntity<Filme>(filme, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value="adiciona um filme")
    public ResponseEntity<Filme> addCliente(@RequestBody String cliente) {
        Filme aFilme = null;
        try {
            aFilme = objectMapper.readValue(cliente, Filme.class);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Filme novoFilme = filmeService.addCliente(aFilme);
        if (novoFilme != null) {
            return new ResponseEntity<Filme>(novoFilme, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
