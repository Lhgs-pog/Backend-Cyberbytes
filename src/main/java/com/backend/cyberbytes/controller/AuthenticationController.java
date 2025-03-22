package com.backend.cyberbytes.controller;

import com.backend.cyberbytes.dto.AuthenticationDto;
import com.backend.cyberbytes.dto.LoginResponseDto;
import com.backend.cyberbytes.dto.UserRequestDto;
import com.backend.cyberbytes.model.User;
import com.backend.cyberbytes.repository.UserRepository;
import com.backend.cyberbytes.service.TokenService;
import com.backend.cyberbytes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("auth")

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;


    /*
     * Função para fazer o login do usuário
     * */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());//Verifica os dados do usuário
        var auth = this.authenticationManager.authenticate(usernamePassword);//Verifica a autenticação

        var token = tokenService.generateToken((User) auth.getPrincipal());//Gerá um token com os dados do usuário
        return ResponseEntity.ok(new LoginResponseDto(token));//Status da resposta
    }

    /*
     * Função para fazer o registro do usuário
     * */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRequestDto data){
        try {
            return userService.registerUser(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
