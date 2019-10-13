package br.com.fiap.servicos.repository;

import br.com.fiap.servicos.model.Chamado;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChamadoRepository extends CrudRepository<Chamado, UUID> {
}
