package com.example.demo.dto.Response;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record DisponibilidadeResponseDTO(

    Long id,
    String nomeMedico,
    DayOfWeek diaSemana,
    LocalTime horarioInicio,
    LocalTime horarioFim

) {}