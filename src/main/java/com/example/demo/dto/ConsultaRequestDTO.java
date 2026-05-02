package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConsultaRequestDTO {

    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime dataHora;
    private String observacoes;

}