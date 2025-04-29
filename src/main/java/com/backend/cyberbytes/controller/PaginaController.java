package com.backend.cyberbytes.controller;

import com.backend.cyberbytes.dto.PaginaRequestDto;
import com.backend.cyberbytes.dto.PaginaResponseDto;
import com.backend.cyberbytes.service.PaginaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("pagina")
public class PaginaController {

    @Autowired
    private PaginaService paginaService;

    /*
     * Endpoint: Retorna todas as páginas
     * */
    @GetMapping
    public List<PaginaResponseDto> getAllPaginas(){
        return paginaService.getAllPaginas();
    }

    /*
     * Endpoint: Retorna uma página pelo título
     * */
    @GetMapping("/titulo")
    public ResponseEntity<PaginaResponseDto> getPaginaByTitulo(@RequestParam("titulo") String titulo) {
        PaginaResponseDto dto = paginaService.getPaginaByTitulo(titulo);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    /*
     * Endpoint: Salva uma página
     * */
    @PostMapping
    public ResponseEntity savePagina(@RequestBody PaginaRequestDto dto){
        return paginaService.savePagina(dto);
    }

    /*
     * Endpoint: Atualiza uma página
     * */
    @PutMapping
    public ResponseEntity updatePagina(@RequestBody PaginaRequestDto dto){
        return paginaService.updatePagina(dto);
    }

    /*
     * Endpoint: Deleta uma página
     * */
    @DeleteMapping
    public ResponseEntity deletePagina(@RequestBody PaginaRequestDto dto){
        return paginaService.deletePagina(dto);
    }
}
