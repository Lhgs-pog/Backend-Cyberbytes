package com.backend.cyberbytes.repository;

import com.backend.cyberbytes.model.Codigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CodigoRepository extends JpaRepository<Codigo, BigInteger> {
    Codigo findByEmail(String email);
}
