package com.example.demo.dto;

import com.example.demo.Entities.Especialidade;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MedicoRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email,

    @NotBlank(message = "CRM é obrigatório")
    String crm,

    @NotBlank(message = "Especialidade é obrigatória")
    Especialidade especialidade

) {}
