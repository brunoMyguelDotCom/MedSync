package com.example.demo.dto;

import com.example.demo.Entities.Paciente;

public record PacienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    String telefone,
    String email
) {
    // Construtor que recebe uma entidade e mapeia os campos
    public PacienteResponseDTO(Paciente paciente){
        this(
            paciente.getId(),
            paciente.getNome(),
            paciente.getCpf(),
            paciente.getTelefone(),
            paciente.getEmail()
        );
    }
}