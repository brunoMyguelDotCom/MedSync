package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // conflito de horario
    boolean existsByMedicoAndDataHora(Medico medico, LocalDateTime dataHora);

    // consulta futura do medico
    List<Consulta> findByMedicoIdAndDataHoraAfter(Long medicoId, LocalDateTime dataHora);

    // cnosuta paciente
    List<Consulta> findByPacienteId(Long pacienteId);

    // filtro por periodo
    List<Consulta> findByDataHoraBetween(LocalDateTime comeco, LocalDateTime fim);

    // filtro por status
    List<Consulta> findByStatus(StatusConsulta status);
}
