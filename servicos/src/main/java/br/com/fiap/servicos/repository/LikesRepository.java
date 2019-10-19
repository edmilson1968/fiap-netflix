package br.com.fiap.servicos.repository;

import br.com.fiap.servicos.model.FilmeClienteLikes;
import br.com.fiap.servicos.service.LikesService;
import org.springframework.data.repository.CrudRepository;

public interface LikesRepository extends CrudRepository<FilmeClienteLikes, Long> {

    boolean existsByClienteIdAndFilmeId(Long clienteId, Long filmeId);

    FilmeClienteLikes findByClienteIdAndFilmeId(Long clienteId, Long filmeId);
}
