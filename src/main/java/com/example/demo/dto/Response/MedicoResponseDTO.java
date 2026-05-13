package com.example.demo.dto.Response;

public record MedicoResponseDTO(
    Long id,
    String nome,
    String crm,
    String especialidade
) {}
