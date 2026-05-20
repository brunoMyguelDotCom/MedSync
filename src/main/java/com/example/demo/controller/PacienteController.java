package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.Request.PacienteRequestDTO;
import com.example.demo.dto.Response.PacienteResponseDTO;
import com.example.demo.service.PacienteService;
import com.example.demo.service.Utils.ApiResponse;

import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // Cadastrar paciente
    @PostMapping
    public ResponseEntity<ApiResponse<PacienteResponseDTO>> criarPaciente(
            @RequestBody PacienteRequestDTO pacienteRequestDTO) {

        ApiResponse<PacienteResponseDTO> response = pacienteService.criarPaciente(pacienteRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Listar pacientes

    @GetMapping
    public ResponseEntity<ApiResponse<List<PacienteResponseDTO>>> listarTodos(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        var response = pacienteService.listarTodos(page, size);
        return ResponseEntity.ok(response);
    }

    // Buscar paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteResponseDTO>> buscarPaciente(@PathVariable Long id) {
        var response = pacienteService.buscarPorId(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    // Atualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PacienteResponseDTO>> atualizarPaciente(@PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO pacienteRequestDTO) {
        var response = pacienteService.atualizarPaciente(id, pacienteRequestDTO);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    // Remover paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removerPaciente(@PathVariable Long id) {
        var response = pacienteService.removerPaciente(id);
        if (!response.isSuccess()) {
            var status = response.getError().getError().equals("Forbidden") ? HttpStatus.FORBIDDEN
                    : HttpStatus.NOT_FOUND;
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
