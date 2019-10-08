package br.com.fiap.filmes.service;

import br.com.fiap.filmes.model.Filme;
import br.com.fiap.filmes.repository.FilmeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class FilmeServiceTest {

    @Mock
    private FilmeRepository filmeRepository;

    @InjectMocks
    private FilmeService filmeService;

    List<Filme> filmes;
    Filme fil1, fil2, fil3;

    @Before()
    public void setup() {
        fil1 = new Filme(1L, "Back To The Future", 1984, "ficção", "ingles", "filme");
        fil2 = new Filme(2L, "Stranger Things", 2017, "ficção", "ingles", "serie");
        fil3 = new Filme(3L, "The Wall", 1975, "documentario", "ingles", "filme");

        filmes = Arrays.asList(fil1, fil2);
    }

    @Test
    public void shouldRetrieveAllFilmes() {
        Page<Filme> page = new PageImpl<>(filmes);
        when(filmeRepository.findAll(any(Pageable.class))).thenReturn(page);

        assertThat(filmeService.findAll(PageRequest.of(0, 10))).isEqualTo(page);
        verify(filmeRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void shouldRetrieveAllFilmesByGenero() {
        final String BUSCA = "ficção";
        Page<Filme> page = new PageImpl<>(
                filmes
                        .stream()
                        .filter(f -> f.getGenero().equals(BUSCA)).collect(Collectors.toList())
        );

        when(filmeRepository.findAllByGenero(any(Pageable.class), anyString())).thenReturn(page);

        assertThat(filmeService.findAllByGenero(PageRequest.of(0, 10), BUSCA)).isEqualTo(page);
        verify(filmeRepository, times(1)).findAllByGenero(any(Pageable.class), anyString());
    }
    @Test
    public void shouldRetrieveAllFilmesLikeTitulo() {
        final String BUSCA = "Back To";
        filmes = filmes
                .stream()
                .filter(f -> f.getGenero().startsWith(BUSCA)).collect(Collectors.toList());
        Page<Filme> page = new PageImpl<>(filmes);

        when(filmeRepository.findByTituloContains(any(Pageable.class), anyString())).thenReturn(page);

        assertThat(filmeService.findAllLikeTitulo(PageRequest.of(0, 10), BUSCA)).isEqualTo(page);
        verify(filmeRepository, times(1)).findByTituloContains(any(Pageable.class), anyString());
    }

    @Test
    public void shouldFindClienteById() {
        when(filmeRepository.findById(1L)).thenReturn(Optional.of(fil1));
        assertThat(filmeService.findById(1L)).isEqualTo(fil1);
        verify(filmeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldThrowClienteNotFoundExceptionForClienteByIdNull() {
        assertThatExceptionOfType(FilmeNotFoundException.class)
                .isThrownBy(() -> filmeService.findById(null)).withMessage("id invalido");
        verify(filmeRepository, times(0)).findById(null);
    }

    @Test
    public void shouldAddAValidCliente() {
        fil1.setId(null);
        when(filmeRepository.save(any(Filme.class))).thenAnswer(i -> {fil1.setId(1L); return fil1;});

        Filme res = filmeService.addCliente(fil1);

        assertThat(res).hasNoNullFieldsOrProperties();
        assertThat(res.getId()).isEqualTo(fil1.getId());
    }
}
