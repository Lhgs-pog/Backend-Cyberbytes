package com.backend.cyberbytes.repository;

import com.backend.cyberbytes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findOptionalByEmail(String email);
    UserDetails findByEmail(String email);
}
