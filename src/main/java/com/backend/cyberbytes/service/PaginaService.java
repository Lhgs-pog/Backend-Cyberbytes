package com.backend.cyberbytes.service;

import com.backend.cyberbytes.dto.PaginaRequestDto;
import com.backend.cyberbytes.dto.PaginaResponseDto;
import com.backend.cyberbytes.model.Pagina;
import com.backend.cyberbytes.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaginaService {

    @Autowired
    private PaginaRepository repository;

    /*
     * Retorna todas as páginas
     * */
    public List<PaginaResponseDto> getAllPaginas(){
        return repository.findAll()
                .stream()
                .map(PaginaResponseDto::new)
                .toList();
    }

    /*
     * Retorna uma página pelo título
     * */
    public PaginaResponseDto getPaginaByTitulo(String titulo){

        if(titulo == null || titulo.isBlank())
            return null;

        Pagina pagina = repository.findByTitulo1(titulo);

        PaginaResponseDto dto = new PaginaResponseDto(pagina);

        return dto;
    }

    /*
     * Retorna páginas para quando o usuário pesquisar
     * */
    public List<PaginaResponseDto> searchPaginasByTituloContaining(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyList();
        }


        List<Pagina> paginas = repository.findByTitulo1ContainingIgnoreCase(query);

        // Converte cada entidade Pagina em PaginaResponseDto
        List<PaginaResponseDto> dtos = paginas.stream()
                .map(PaginaResponseDto::new)
                .collect(Collectors.toList());

        return dtos;
    }


    /*
     * Salva uma página
     * */
    public ResponseEntity savePagina(PaginaRequestDto dto){

        if (repository.findByTitulo1(dto.titulo1()) != null)
            return ResponseEntity.badRequest().body("Este título já existe");

        Pagina pagina = new Pagina(dto);

        repository.save(pagina);

        return ResponseEntity.ok("Página criada com sucesso");
    }

    /*
     * Atualiza uma página
     * */
    public ResponseEntity updatePagina(PaginaRequestDto dto){

        if (repository.findByTitulo1(dto.titulo1()) == null)
            return ResponseEntity.badRequest().body("Nenhuma página com este título");

        Pagina paginaExistente = repository.findByTitulo1(dto.titulo1());

        paginaExistente.setTitulo1(dto.titulo1());
        paginaExistente.setTitulo2(dto.titulo2());
        paginaExistente.setConteudo1(dto.conteudo1());
        paginaExistente.setConteudo2(dto.conteudo2());
        paginaExistente.setConteudo3(dto.conteudo3());

        repository.save(paginaExistente);

        return ResponseEntity.ok("Página atualizada com sucesso");
    }

    /*
     * Deleta uma página
     * */
    public ResponseEntity deletePagina(PaginaRequestDto dto){

        if (repository.findByTitulo1(dto.titulo1()) == null)
            return ResponseEntity.badRequest().body("Nenhuma página com este título");

        Pagina pagina = repository.findByTitulo1(dto.titulo1());

        repository.deleteById(pagina.getId());

        return ResponseEntity.ok("Página deletada com sucesso");
    }
}
