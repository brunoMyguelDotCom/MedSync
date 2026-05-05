package com.example.demo.dto.Response;

import java.time.LocalTime;

import com.example.demo.Entities.Disponibilidade;
import com.example.demo.Entities.Enums.DiaSemana;

public record DisponibilidadeResponseDTO(

        Long id,
        String nomeMedico,
        DiaSemana diaSemana,
        LocalTime horarioInicio,
        LocalTime horarioFim

) {

    public DisponibilidadeResponseDTO(Disponibilidade disponibilidade) {
        this(
            disponibilidade.getId(),
            disponibilidade.getMedico().getNome(),
            disponibilidade.getDiaSemana(),
            disponibilidade.getHorarioInicio(),
            disponibilidade.getHorarioFim()
        );
    }
}