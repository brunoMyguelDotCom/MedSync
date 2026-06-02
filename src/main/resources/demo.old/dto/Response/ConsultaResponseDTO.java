package com.example.demo.dto.Response;

import java.time.LocalDateTime;
import com.example.demo.Entities.Enums.StatusConsulta;

public record ConsultaResponseDTO(

        Long id,
        String nomePaciente,
        String nomeMedico,
        LocalDateTime dataHora,
        StatusConsulta status,
        String observacoes) {
}
