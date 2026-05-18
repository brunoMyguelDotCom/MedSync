 package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Request.PacienteRequestDTO;
import com.example.demo.dto.Response.PacienteResponseDTO;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.PacienteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



 @RestController
 @RequestMapping("/pacientes")

 public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
            this.pacienteService = pacienteService;
    }

    @PostMapping("path")
    public ResponseEntity<ApiResponse<PacienteResponseDTO>> criarPaciente(@RequestBody PacienteRequestDTO pacienteRequestDTO) {
       
        ApiResponse<PacienteResponseDTO> response = pacienteService.criarPaciente(pacienteRequestDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
     
 }
