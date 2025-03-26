package com.backend.cyberbytes.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Table(name = "Pagina")
@Entity(name = "Pagina")

@EqualsAndHashCode(of = "id")
public class Pagina {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "titulo1", nullable = false)
    private String tituloPrinciapl;
    @Column(name = "titulo2")
    private String tituloSecunario;
    @Column(name = "conteudo1", nullable = false)
    private String conteudo1;
    @Column(name = "conteudo2")
    private String conteudo2;
    @Column(name = "conteudo3")
    private String conteudo3;
}
