package com.backend.cyberbytes.service;

import com.backend.cyberbytes.dto.UserRequestDto;
import com.backend.cyberbytes.model.User;
import com.backend.cyberbytes.model.UserRole;
import com.backend.cyberbytes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //Dependencias
    @Autowired
    private UserRepository userRepository;

    /*
     * Salva um novo usuário no banco de dados
     * Observação: Por enquanto sem a implementação do código
     * */
    public ResponseEntity registerUser(UserRequestDto data){
        //Verificando se o usuário existe
        if (userRepository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().body("Este email de usuário já existe");

        //Definindo um usuário
        User user = new User();
        user.setName(data.name());
        user.setEmail(data.email());
        user.setRole(UserRole.USER);

        //Criptografando a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        user.setPassword(encryptedPassword);

        userRepository.save(user);
        return ResponseEntity.ok("Usuário salvo com sucesso!");
    }
}
