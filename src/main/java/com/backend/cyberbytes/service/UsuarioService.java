package com.backend.cyberbytes.service;

import com.backend.cyberbytes.dto.ForgotPasswordDto;
import com.backend.cyberbytes.dto.UsuarioRequestDto;
import com.backend.cyberbytes.dto.UsuarioResponseDto;
import com.backend.cyberbytes.exceptions.ResourceNotFoundException;
import com.backend.cyberbytes.model.Usuario;
import com.backend.cyberbytes.model.UsuarioRole;
import com.backend.cyberbytes.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    //Dependencias
    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private CodigoService codigoService;

    @Autowired
    private EmailService emailService;

    /*
     * Salva um novo usuário no banco de dados
     * Observação: Por enquanto sem a implementação do código
     * */
    public ResponseEntity registerUser(UsuarioRequestDto data, int tentativa){
        //Verificando se o usuário existe
        if (userRepository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().body("Este email de usuário já existe");

        //Definindo um usuário
        Usuario usuario = new Usuario();
        usuario.setNome(data.nome());
        usuario.setEmail(data.email());
        usuario.setRole(UsuarioRole.USER);

        //Criptografando a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        usuario.setSenha(encryptedPassword);

        //Verifica se o código gerado é o mesmo informado
        if (codigoService.verificarCodigo(data.email(),tentativa)) {
            userRepository.save(usuario);
            return ResponseEntity.ok("Usuario salvo com sucesso");
        }
        return ResponseEntity.badRequest().body("Código expirado ou tentativa inválida. Faça uma nova tentaiva de criar uma conta para tentar novamente");
    }

    /*
   Retorna todos os usuários
   */
    public List<UsuarioResponseDto> findAllUsuarios(){
        return userRepository.findAll().stream()
                .map(UsuarioResponseDto::new)
                .toList();
    }

    /*
    Retorna um usuário específico pelo id dele
    * */
    public Optional<Usuario> findUsuarioById(String id){
        return userRepository.findById(id);
    }

    /*
    Retorna um usuário específico pelo Email dele
    * */
    public Optional<Usuario> findUsuarioByEmail(String email){
        return userRepository.findOptionalByEmail(email);
    }


    /*
    Atualizar o usuário
    * */
    @Transactional
    public ResponseEntity<Usuario> updateUser(String id, UsuarioRequestDto newUser){
        //Verifica se o usuário existe
        Usuario userExists = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        //Verifica se o email novo já existe
        Optional<Usuario> userEmail = userRepository.findOptionalByEmail(newUser.email());
        if (userEmail.isPresent() && !userEmail.get().getId().equals(userExists.getId())){
            throw new DataIntegrityViolationException("Já existe um usuário com este email");
        }

        //Atualiza o nome
        userExists.setNome(newUser.nome());
        //Atualiza o email
        userExists.setEmail(newUser.email());

        //Só criptografa se tiver um valor
        if (newUser.senha() != null && !newUser.senha().isBlank()) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.senha());
            userExists.setSenha(encryptedPassword);
        }

        userRepository.save(userExists);

        return ResponseEntity.ok(userExists);
    }


    public ResponseEntity changePassword(ForgotPasswordDto forgotPasswordDto, int tentativa){
        //Verifica se o usuário existe
        Usuario usuario = userRepository.findOptionalByEmail(forgotPasswordDto.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        //Verifica se o código gerado é o mesmo informado
        if (codigoService.verificarCodigo(forgotPasswordDto.email(), tentativa)) {
            //Criptografando a senha
            if(forgotPasswordDto.password() != null && !forgotPasswordDto.password().isBlank()){
                String encryptedPassword = new BCryptPasswordEncoder().encode(forgotPasswordDto.password());
                usuario.setSenha(encryptedPassword);
                // Salvar o usuário atualizado no banco de dados
                userRepository.save(usuario);
            }
            return ResponseEntity.ok("Senha modificada com sucesso!");
        }

        return ResponseEntity.badRequest().body("Código expirado ou tentativa inválida. Faça uma nova tentaiva de criar uma conta para tentar novamente");
    }

    /*
     * Deleta um usuário específico
     * */
    @Transactional
    public ResponseEntity<Usuario> deleteByID(String id){

        //Verifica se o usuário existe
        Usuario user = userRepository.findById(id)
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
