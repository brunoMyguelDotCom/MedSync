package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.StatusConsulta;

public record ConsultaResponseDTO(
        Long id,
        String nomePaciente,
        String nomeMedico,
        LocalDateTime dataHora,
        StatusConsulta status,
        String observacoes) {

    public ConsultaResponseDTO(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getNome(), consulta.getMedico().getNome(), consulta.getDataHora(), consulta.getStatus(), consulta.getObservacoes());
    }
}
