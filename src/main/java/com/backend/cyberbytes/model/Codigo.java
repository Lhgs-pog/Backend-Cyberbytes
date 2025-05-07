package com.backend.cyberbytes.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "codigo")
@Entity(name = "codigo")
@EqualsAndHashCode(of = "id")

@Component
public class Codigo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    @Column(name = "codigo", nullable = false)
    private int codigo;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "dia", nullable = false)
    private LocalDateTime dia;

    public Codigo(String email, int codigo, LocalDateTime now){
        this.email=email;
        this.codigo=codigo;
        this.dia=now;
    }
}
