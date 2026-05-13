package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Request.ConsultaRequestDTO;
import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.service.ConsultaService;
import com.example.demo.service.Utils.ApiResponse;

import lombok.experimental.var;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    // Atributo para receber o service
    private final ConsultaService consultaService;

    // Construtor para injetar o service
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // Endpoint para listar consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultaResponseDTO>> listarPorId(@PathVariable Long id) {

        var response = consultaService.listarPorId(id);

        return ResponseEntity.ok(response);
    }

    // Endpoint para listar todas as consultas
    @GetMapping()
    public ResponseEntity<ApiResponse<List<ConsultaResponseDTO>>> listaTodos() {

        var response = consultaService.listarTodos();

        return ResponseEntity.ok(response);
    }

    // Endpoint para criar uma nova consulta
    @PostMapping
    public ResponseEntity<ApiResponse<ConsultaResponseDTO>> criarConsulta(
            @RequestBody ConsultaRequestDTO consultaRequestDTO) {

        var response = consultaService.criarConsulta(consultaRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
