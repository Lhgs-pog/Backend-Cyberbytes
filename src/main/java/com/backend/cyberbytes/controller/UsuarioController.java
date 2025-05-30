package com.backend.cyberbytes.controller;


import com.backend.cyberbytes.dto.UsuarioRequestDto;
import com.backend.cyberbytes.dto.UsuarioResponseDto;
import com.backend.cyberbytes.model.Usuario;
import com.backend.cyberbytes.service.CodigoService;
import com.backend.cyberbytes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    @Autowired
    private CodigoService codigoService;
    /*
     * Retorna todos os usuários
     * */
    @GetMapping
    public List<UsuarioResponseDto> getAllUsuarios(){
        return userService.findAllUsuarios();
    }

    /*
     * Retorna um usuário específico pelo ID
     * */
    @GetMapping("/{id}")
    public Optional<Usuario> getUserById(@PathVariable("id")String id){
        return userService.findUsuarioById(id);
    }

    /*
     * Retorna um usuário específico pelo EMAIL
     * */
    @GetMapping("/email/{email}")
    public Optional<Usuario> getUserByEmail(@PathVariable("email")String email){
        return userService.findUsuarioByEmail(email);
    }

    @PostMapping("/codigo")
    public ResponseEntity<String> gerarCodigo(@RequestBody String email){
        try {
            codigoService.salvarCodigo(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Ouve um erro ao gerar um código");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(202)).body("Um email contendo o código de verificação foi enviado");
    }

    /*
     * Atualizar dados do usuário
     * */
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UsuarioRequestDto data) {
        return userService.updateUser(id, data);
    }

    /*
     * Deletar todos os usuários do banco de dados
     * */
    @DeleteMapping
    public ResponseEntity deleteAllUsuarios(){
        return userService.deleteAll();
    }

    /*
     * Deleta um usuário específico
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuarioById(@PathVariable("id") String id){
        return userService.deleteByID(id);
    }

}
