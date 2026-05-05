package com.example.demo.dto;

import com.example.demo.Entities.Especialidade;

public record MedicoResponseDTO(
    Long id,
    String nome,
    String emai,
    String crm,
    Especialidade especialidade
) {}
