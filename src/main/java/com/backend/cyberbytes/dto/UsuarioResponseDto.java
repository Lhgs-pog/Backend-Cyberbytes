package com.backend.cyberbytes.dto;

import com.backend.cyberbytes.model.Usuario;
import com.backend.cyberbytes.model.UsuarioRole;

public record UsuarioResponseDto(String id, String nome, String email, String senha, UsuarioRole role) {
    public UsuarioResponseDto(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getRole());
    }
}
