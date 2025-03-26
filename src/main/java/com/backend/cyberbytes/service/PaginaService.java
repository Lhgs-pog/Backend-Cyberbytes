package com.backend.cyberbytes.service;

import com.backend.cyberbytes.dto.PaginaRequestDto;
import com.backend.cyberbytes.dto.PaginaResponseDto;
import com.backend.cyberbytes.model.Pagina;
import com.backend.cyberbytes.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginaService {

    @Autowired
    PaginaRepository repository;

    public List<PaginaResponseDto> getAllPaginas(){
        return repository.findAll()
                .stream()
                .map(PaginaResponseDto::new)
                .toList();
    }

    public PaginaResponseDto getPaginaByTitulo(String titulo){

        if(titulo.isBlank() || titulo == null)
            return null;

        Pagina pagina = repository.findByTitulo1(titulo);

        PaginaResponseDto dto = new PaginaResponseDto(pagina);

        return dto;
    }

    public ResponseEntity savePagina(PaginaRequestDto dto){

        if (repository.findByTitulo1(dto.tituloPrincipal()) != null)
            return ResponseEntity.badRequest().body("Este título já existe");

        Pagina pagina = new Pagina(dto);

        repository.save(pagina);

        return ResponseEntity.ok("Página criada com sucesso");
    }

    public ResponseEntity updatePagina(PaginaRequestDto dto){

        if (repository.findByTitulo1(dto.tituloPrincipal()) == null)
            return ResponseEntity.badRequest().body("Nenhuma página com este título");

        Pagina paginaExistente = repository.findByTitulo1(dto.tituloPrincipal());

        paginaExistente.setTituloPrinciapal(dto.tituloPrincipal());
        paginaExistente.setTituloSecunario(dto.tituloSecundario());
        paginaExistente.setConteudo1(dto.conteudo1());
        paginaExistente.setConteudo2(dto.conteudo2());
        paginaExistente.setConteudo3(dto.conteudo3());

        repository.save(paginaExistente);

        return ResponseEntity.ok("Página atualizada com sucesso");
    }

    public ResponseEntity deletePagina(PaginaRequestDto dto){

        if (repository.findByTitulo1(dto.tituloPrincipal()) == null)
            return ResponseEntity.badRequest().body("Nenhuma página com este título");

        Pagina pagina = repository.findByTitulo1(dto.tituloPrincipal());

        repository.deleteById(pagina.getId());

        return ResponseEntity.ok("Página deletada com sucesso");
    }
}
