package com.example.demo.dto.Request;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public record DisponibilidadeRequestDTO(

    @NotNull(message = "Dia da semana é obrigatório")
    DayOfWeek diaSemana,

    @NotNull(message = "Horário de início é obrigatório")
    LocalTime horarioInicio,

    @NotNull(message = "Horário de fim é obrigatório")
    LocalTime horarioFim

) {}