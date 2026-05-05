package com.example.demo.dto.Response;

public record PacienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    String telefone,
    String email
) {}