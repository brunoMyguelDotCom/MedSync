package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Request.DisponibilidadeRequestDTO;
import com.example.demo.dto.Response.DisponibilidadeResponseDTO;
import com.example.demo.service.DisponibilidadeService;
import com.example.demo.service.Utils.ApiResponse;

@RestController
@RequestMapping("/api/medicos/{medicoId}/disponibilidades")
public class DisponibilidadeController {

        private final DisponibilidadeService disponibilidadeService;

        public DisponibilidadeController(
                        DisponibilidadeService disponibilidadeService) {

                this.disponibilidadeService = disponibilidadeService;
        }

        @PostMapping
        public ResponseEntity<ApiResponse<DisponibilidadeResponseDTO>> registrarDisponibilidade(
                        @PathVariable Long medicoId,

                        @RequestBody DisponibilidadeRequestDTO disponibilidadeRequestDTO) {

                ApiResponse<DisponibilidadeResponseDTO> response = disponibilidadeService.registrarDisponibilidade(
                                medicoId,
                                disponibilidadeRequestDTO);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<DisponibilidadeResponseDTO>>> listarDisponibilidadesPorMedico(

                        @PathVariable Long medicoId) {

                ApiResponse<List<DisponibilidadeResponseDTO>> response = disponibilidadeService
                                .listarDisponibilidadesPorMedico(medicoId);

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<String>> removerDisponibilidade(

                        @PathVariable Long medicoId,
                        @PathVariable Long id) {

                disponibilidadeService.removerDisponibilidade(id);

                return ResponseEntity.ok(
                                new ApiResponse<>("Disponibilidade desativada com sucesso"));
        }
}