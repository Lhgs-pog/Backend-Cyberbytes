package com.backend.cyberbytes.repository;

import com.backend.cyberbytes.model.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaginaRepository extends JpaRepository<Pagina, String> {
    Pagina findByTitulo1(String titulo1);
    List<Pagina> findByTitulo1ContainingIgnoreCase(String query);
}
