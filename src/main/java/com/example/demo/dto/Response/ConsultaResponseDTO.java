package com.example.demo.dto.Response;

import java.time.LocalDateTime;
import com.example.demo.Entities.Enums.StatusConsulta;
import com.example.demo.service.ConsultaService;

public record ConsultaResponseDTO(

        Long id,
        String nomePaciente,
        String nomeMedico,
        LocalDateTime dataHora,
        StatusConsulta status,
        String observacoes) {

        public ConsultaResponseDTO() {

                this(null, null, null, null, null, null);
        }
}
