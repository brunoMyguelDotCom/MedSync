package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Request.MedicoRequestDTO;
import com.example.demo.dto.Response.MedicoResponseDTO;
import com.example.demo.service.MedicoService;
import com.example.demo.service.Utils.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MedicoResponseDTO>> criarMedico(
            @RequestBody MedicoRequestDTO medicoRequestDTO) {

        ApiResponse<MedicoResponseDTO> response = medicoService.criarMedico(medicoRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
