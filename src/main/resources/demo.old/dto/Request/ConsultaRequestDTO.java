package com.example.demo.dto.Request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record ConsultaRequestDTO(

        @NotNull(message = "ID do Paciente é obrigatório")
        Long pacienteId,

        @NotNull(message = "ID do Médico é obrigatório")
        Long medicoId,

        @NotNull(message = "Data e hora são obrigatórios")
        LocalDateTime dataHora,

        String observacoes
) {}