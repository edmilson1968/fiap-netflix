package br.com.fiap.servicos.repository;

import br.com.fiap.servicos.model.FilmeClienteAssistidos;
import org.springframework.data.repository.CrudRepository;

public interface AssistidosRepository extends CrudRepository<FilmeClienteAssistidos, Long> {
    boolean existsByClienteIdAndFilmeId(long clienteId, long filmeId);
}
