package com.example.demo.dto;

import com.example.demo.Entities.Medico;

public record MedicoResponseDTO(
    Long id,
    String nome,
    String emai,
    String crm,
    String especialidade
) {
    // Construtor que recebe uma entidade e mapeia os campos
    public MedicoResponseDTO(Medico medico){
        this(
            medico.getId(),
            medico.getNome(),
            medico.getEmail(),
            medico.getCrm(),
            medico.getEspecialidade()
        );
    }
}
