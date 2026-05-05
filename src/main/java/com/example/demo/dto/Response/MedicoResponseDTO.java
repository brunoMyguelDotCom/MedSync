package com.example.demo.dto.Response;

import com.example.demo.Entities.Especialidade;

public record MedicoResponseDTO(
    Long id,
    String nome,
    String emai,
    String crm,
    Especialidade especialidade
) {}
