package com.example.demo.dto.Request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record ConsultaRequestDTO(

        //@NotBlank(message = "ID do Paciente é obrigatório")
        Long pacienteId,

        //@NotBlank(message = "ID do Médico é obrigatório")
        Long medicoId,

        //@NotBlank(message = "Data e hora são obrigatórios")
        LocalDateTime dataHora,

        String observacoes
) {}