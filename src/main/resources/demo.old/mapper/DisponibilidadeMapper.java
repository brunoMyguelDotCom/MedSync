package com.example.demo.mapper;

import com.example.demo.Entities.Disponibilidade;
import com.example.demo.Entities.Medico;
import com.example.demo.dto.Request.DisponibilidadeRequestDTO;
import com.example.demo.dto.Response.DisponibilidadeResponseDTO;

public class DisponibilidadeMapper {

    // DTO para Entity
    public static Disponibilidade toEntityDisponibilidade(
            DisponibilidadeRequestDTO dto,
            Medico medico) {

        return new Disponibilidade(
                medico,
                dto.diaSemana(),
                dto.horarioInicio(),
                dto.horarioFim());
    }

    // Entity para ResponseDTO
    public static DisponibilidadeResponseDTO toDisponibilidadeResponseDTO(
            Disponibilidade disponibilidade) {

        return new DisponibilidadeResponseDTO(
                disponibilidade.getId(),
                disponibilidade.getMedico().getNome(),
                disponibilidade.getDiaSemana(),
                disponibilidade.getHorarioInicio(),
                disponibilidade.getHorarioFim());
    }

}