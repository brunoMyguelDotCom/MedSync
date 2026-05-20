package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.Request.ConsultaRequestDTO;
import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.service.ConsultaService;
import com.example.demo.service.Utils.ApiResponse;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultaResponseDTO>> listarPorId(
            @PathVariable Long id) {

        var response = consultaService.listarPorId(id);

        return ResponseEntity.ok(response);
    }

    // Listar consultas
    @GetMapping
    public ResponseEntity<ApiResponse<List<ConsultaResponseDTO>>> listaTodos() {

        var response = consultaService.listarTodos();

        return ResponseEntity.ok(response);
    }

    // Criar consulta
    @PostMapping
    public ResponseEntity<ApiResponse<ConsultaResponseDTO>> criarConsulta(
            @RequestBody ConsultaRequestDTO consultaRequestDTO) {

        var response = consultaService.criarConsulta(consultaRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Atualizar status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ConsultaResponseDTO>> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String novoStatus = body.get("status");

        var response = consultaService.atualizarStatus(id, novoStatus);

        return ResponseEntity.ok(response);
    }

    // Cancelar consulta
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> cancelarConsulta(
            @PathVariable Long id) {

        var response = consultaService.cancelarConsulta(id);

        return ResponseEntity.ok(response);
    }
}