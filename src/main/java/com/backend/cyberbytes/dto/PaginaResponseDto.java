package com.backend.cyberbytes.dto;

import com.backend.cyberbytes.model.Pagina;

import java.util.UUID;

public record PaginaResponseDto (String uuid, String tituloPrincipal, String tituloSecundario, String conteudo1, String conteudo2, String conteudo3){
    public PaginaResponseDto(Pagina pagina){
        this(pagina.getId(), pagina.getTituloPrinciapal(), pagina.getTituloSecunario(), pagina.getConteudo1(), pagina.getConteudo2(), pagina.getConteudo3());
    }
}
