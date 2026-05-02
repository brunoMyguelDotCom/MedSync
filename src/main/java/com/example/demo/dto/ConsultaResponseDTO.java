package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.Entities.StatusConsulta;

import lombok.Data;

@Data
public class ConsultaResponseDTO {

    private Long id;
    private String nomePaciente;
    private String nomeMedico;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private String observacoes;

}
