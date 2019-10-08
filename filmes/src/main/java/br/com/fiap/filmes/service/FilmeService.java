package br.com.fiap.filmes.service;

import br.com.fiap.filmes.model.Filme;
import br.com.fiap.filmes.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public Page<Filme> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Filme findById(Long id) {
        if (id == null)
            throw new FilmeNotFoundException("id invalido");
        Optional<Filme> filme = repository.findById(id);
        filme.orElseThrow(() -> new FilmeNotFoundException("id: " + id));
        return filme.get();
    }

    public Filme addCliente(Filme filme) {
        return repository.save(filme);
    }

    public Page<Filme> findAllByGenero(Pageable pageable, String genero) {
        return repository.findAllByGenero(pageable, genero);
    }

    public Page<Filme> findAllLikeTitulo(Pageable pageable, String titulo) {
        return repository.findByTituloContains(pageable, titulo);
    }
}
