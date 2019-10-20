package br.com.fiap.servicos.repository;

import br.com.fiap.servicos.model.FilmeClienteLikes;
import br.com.fiap.servicos.service.LikesService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends CrudRepository<FilmeClienteLikes, Long> {

    boolean existsByClienteIdAndFilmeId(@Param("clienteId") Long clienteId, @Param("filmeId") Long filmeId);

    FilmeClienteLikes findByClienteIdAndFilmeId(@Param("clienteId") Long clienteId, @Param("filmeId") Long filmeId);
}
