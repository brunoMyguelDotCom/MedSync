package com.example.demo.dto.Request;

import java.time.LocalTime;

import com.example.demo.Entities.Enums.DiaSemana;

import jakarta.validation.constraints.NotNull;

public record DisponibilidadeRequestDTO(

    @NotNull(message = "Dia da semana é obrigatório")
    DiaSemana diaSemana,

    @NotNull(message = "Horário de início é obrigatório")
    LocalTime horarioInicio,

    @NotNull(message = "Horário de fim é obrigatório")
    LocalTime horarioFim

) {}