package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Request.EspecialidadeRequestDTO;
import com.example.demo.dto.Response.EspecialidadeResponseDTO;
import com.example.demo.service.EspecialidadeService;
import com.example.demo.service.Utils.ApiResponse;

import lombok.experimental.var;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/especialidade")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EspecialidadeResponseDTO>> criarEspecialidade(
            @RequestBody EspecialidadeRequestDTO especialidadeRequestDTO) {

        var response = especialidadeService.criarEspecialidade(especialidadeRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EspecialidadeResponseDTO>>> listarEspecialidade() {

        var response = especialidadeService.listarEspecialidade();

        return (ResponseEntity<ApiResponse<List<EspecialidadeResponseDTO>>>) ResponseEntity.ok();
    }

    // Buscar especialidade por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadeResponseDTO>> buscarPorId(
            @PathVariable Long id) {

        var response = especialidadeService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    // Atualizar especialidade
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadeResponseDTO>> atualizarEspecialidade(
            @PathVariable Long id,
            @RequestBody EspecialidadeRequestDTO dto) {

        var response = especialidadeService.atualizarEspecialidade(id, dto);

        return ResponseEntity.ok(response);
    }

    // Deletar especialidade
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletarEspecialidade(
            @PathVariable Long id) {

        var response = especialidadeService.deletarEspecialidade(id);

        return ResponseEntity.ok(response);
    }

}
