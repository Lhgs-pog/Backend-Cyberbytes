package com.backend.cyberbytes.controller;

import com.backend.cyberbytes.dto.AuthenticationDto;
import com.backend.cyberbytes.dto.LoginResponseDto;
import com.backend.cyberbytes.dto.UsuarioRequestDto;
import com.backend.cyberbytes.model.Usuario;
import com.backend.cyberbytes.repository.UsuarioRepository;
import com.backend.cyberbytes.service.TokenService;
import com.backend.cyberbytes.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("auth")

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;


    /*
     * Função para fazer o login do usuário
     * */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());//Verifica os dados do usuário
            var auth = this.authenticationManager.authenticate(usernamePassword);//Verifica a autenticação

            var token = tokenService.generateToken((Usuario) auth.getPrincipal());//Gerá um token com os dados do usuário
            return ResponseEntity.ok(new LoginResponseDto(token));//Status da resposta
        }catch(Exception e){
            e.printStackTrace(); //ERRO
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário inexistente ou senha inválida");
        }
    }

    /*
     * Função para fazer o registro do usuário
     * */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsuarioRequestDto data, @RequestParam("tentativa") int tentativa){
        try {
            return usuarioService.registerUser(data, tentativa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
