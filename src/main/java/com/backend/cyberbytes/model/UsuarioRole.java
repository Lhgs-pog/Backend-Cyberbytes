package com.backend.cyberbytes.model;

public enum UsuarioRole {
    //Autoridades
    ADMIN("admin"),
    USER("user");

    private String role;

    //Construtor
    UsuarioRole(String role){
        this.role = role;
    }

    //Método get
    public String getRole(){
        return role;
    }
}
