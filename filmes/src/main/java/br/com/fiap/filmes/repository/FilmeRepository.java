package br.com.fiap.filmes.repository;

import br.com.fiap.filmes.model.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FilmeRepository extends PagingAndSortingRepository<Filme, Long> {

    Page<Filme> findAllByGenero(Pageable pageable, String genero);

    Page<Filme> findByTituloContains(Pageable pageable, String titulo);
}
