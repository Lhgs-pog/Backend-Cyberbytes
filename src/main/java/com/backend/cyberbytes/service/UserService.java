package com.backend.cyberbytes.service;

import com.backend.cyberbytes.dto.UserRequestDto;
import com.backend.cyberbytes.dto.UserResponseDto;
import com.backend.cyberbytes.exceptions.ResourceNotFoundException;
import com.backend.cyberbytes.model.User;
import com.backend.cyberbytes.model.UserRole;
import com.backend.cyberbytes.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    /*
   Retorna todos os usuários
   */
    public List<UserResponseDto> findAllUsuarios(){
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();
    }

    /*
    Retorna um usuário específico pelo id dele
    * */
    public Optional<User> findUsuarioById(String id){
        return userRepository.findById(id);
    }

    /*
    Retorna um usuário específico pelo Email dele
    * */
    public Optional<User> findUsuarioByEmail(String email){
        return userRepository.findOptionalByEmail(email);
    }

    /*
    Atualizar o usuário
    * */
    @Transactional
    public ResponseEntity<User> updateUser(String id, UserRequestDto newUser){
        //Verifica se o usuário existe
        User userExists = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        //Verifica se o email novo já existe
        Optional<User> userEmail = userRepository.findOptionalByEmail(newUser.email());
        if (userEmail.isPresent() && !userEmail.get().getId().equals(userExists.getId())){
            throw new DataIntegrityViolationException("Já existe um usuário com este email");
        }

        //Atualiza o nome
        userExists.setName(newUser.name());
        //Atualiza o email
        userExists.setEmail(newUser.email());

        //Criptografando nova senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.password());

        //Mudando a senha para a nova
        userExists.setPassword(encryptedPassword);

        userRepository.save(userExists);

        return ResponseEntity.ok(userExists);
    }

    /*
     * Deleta um usuário específico
     * */
    @Transactional
    public ResponseEntity<User> deleteByID(String id){

        //Verifica se o usuário existe
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        userRepository.deleteById(id);

        return ResponseEntity.ok(user);
    }

    /*
     * Deleta todos os usuários no banco de dados
     * */
    @Transactional
    public ResponseEntity deleteAll(){
        userRepository.deleteAll();
        return ResponseEntity.ok("Usuarios deletados com sucesso");
    }
}
