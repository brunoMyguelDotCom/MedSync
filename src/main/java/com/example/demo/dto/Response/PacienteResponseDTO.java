package com.example.demo.dto.Response;

import com.example.demo.Entities.Paciente;

public record PacienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    String telefone,
    String email
) {}