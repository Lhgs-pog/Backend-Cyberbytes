package com.backend.cyberbytes.model;

import com.backend.cyberbytes.dto.PaginaRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Table(name = "Pagina")
@Entity(name = "Pagina")

@EqualsAndHashCode(of = "id")
@Component
public class Pagina {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "titulo1", nullable = false, unique = true)
    private String titulo1;
    @Column(name = "titulo2")
    private String titulo2;
    @Column(name = "conteudo1", nullable = false)
    private String conteudo1;
    @Column(name = "conteudo2")
    private String conteudo2;
    @Column(name = "conteudo3")
    private String conteudo3;

    public Pagina(PaginaRequestDto dto){
        this.titulo1=dto.titulo1();
        this.titulo2=dto.titulo2();
        this.conteudo1=dto.conteudo1();
        this.conteudo2=dto.conteudo2();
        this.conteudo3=dto.conteudo3();
    }
}
