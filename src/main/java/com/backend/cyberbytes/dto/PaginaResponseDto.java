package com.backend.cyberbytes.dto;

import com.backend.cyberbytes.model.Pagina;

import java.util.UUID;

public record PaginaResponseDto (String uuid, String titulo1, String titulo2, String conteudo1, String conteudo2, String conteudo3){
    public PaginaResponseDto(Pagina pagina){
        this(pagina.getId(), pagina.getTitulo1(), pagina.getTitulo2(), pagina.getConteudo1(), pagina.getConteudo2(), pagina.getConteudo3());
    }
}
