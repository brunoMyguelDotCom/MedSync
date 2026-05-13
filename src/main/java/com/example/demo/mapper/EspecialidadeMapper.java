package com.example.demo.mapper;

import com.example.demo.Entities.Especialidade;
import com.example.demo.dto.Request.EspecialidadeRequestDTO;
import com.example.demo.dto.Response.EspecialidadeResponseDTO;

public class EspecialidadeMapper {

    public static EspecialidadeResponseDTO toEspecialidadeResponseDTO(Especialidade especialidade) {
        return new EspecialidadeResponseDTO(
                especialidade.getId(),
                especialidade.getNome());
    }

    public static Especialidade toEntityEspecialidade(EspecialidadeRequestDTO dto) {

        return new Especialidade(0, dto.nome());
    }

}
