package br.com.fiap.servicos.repository;

import br.com.fiap.servicos.model.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServicosRepository extends PagingAndSortingRepository<Cliente,Long> {

}
