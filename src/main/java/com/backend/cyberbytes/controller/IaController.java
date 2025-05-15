package com.backend.cyberbytes.controller;

import com.backend.cyberbytes.service.IaService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/ia")
public class IaController {

    private IaService service;

    @PostMapping("/chat")
    public ResponseEntity<String> enviar_prompt(@RequestParam("prompt") String prompt) throws URISyntaxException, IOException, InterruptedException {
        try{
            String resposta = service.fazerRequisicao(prompt);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(resposta);
        } catch (Exception e) {
            System.out.println("Erro ao tentar fazer requiisção. Exeption: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/pagina")
    public  ResponseEntity<String> criar_pagina(@RequestParam("prompt") String prompt) throws  URISyntaxException, IOException, InterruptedException{
        try{
            String resposta = service.criarConteudo(prompt);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(resposta);
        } catch (Exception e) {
            System.out.println("Erro ao tentar fazer requiisção. Exeption: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
