package com.example.demo.dto;

import com.example.demo.Entities.Especialidade;

public record EspecialidadeResponseDTO(Long id, String nome){

    public EspecialidadeResponseDTO(Especialidade especialidade){
        this(especialidade.getId(), especialidade.getNome());
    }
    
}
