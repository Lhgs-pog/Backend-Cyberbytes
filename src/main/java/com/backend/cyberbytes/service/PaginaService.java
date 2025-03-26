package com.backend.cyberbytes.service;

import com.backend.cyberbytes.dto.PaginaResponseDto;
import com.backend.cyberbytes.model.Pagina;
import com.backend.cyberbytes.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
