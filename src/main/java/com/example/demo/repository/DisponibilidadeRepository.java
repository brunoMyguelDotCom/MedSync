package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Disponibilidade;
import com.example.demo.Entities.DiaSemana;
import com.example.demo.Entities.Medico;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    // listar disponibilidades do medico
    List<Disponibilidade> findByMedico(Medico medico);

    // buscar por dia da semana
    List<Disponibilidade> findByMedicoAndDiaSemana(Medico medico, DiaSemana diaSemana);
}