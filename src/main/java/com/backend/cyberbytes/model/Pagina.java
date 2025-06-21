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
    @Column(name = "titulo", nullable = false, unique = true)
    private String titulo1;
    @Column(name = "subtitulo")
    private String titulo2;
    @Column(name = "introducao", nullable = false, columnDefinition = "TEXT")
    private String conteudo1;
    @Column(name = "conteudo", columnDefinition = "TEXT")
    private String conteudo2;
    @Column(name = "conclusao", columnDefinition = "TEXT")
    private String conteudo3;

    public Pagina(PaginaRequestDto dto){
        this.titulo1=dto.titulo1();
        this.titulo2=dto.titulo2();
        this.conteudo1=dto.conteudo1();
        this.conteudo2=dto.conteudo2();
        this.conteudo3=dto.conteudo3();
    }
}
