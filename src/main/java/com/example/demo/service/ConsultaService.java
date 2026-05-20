package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.Paciente;
import com.example.demo.Entities.Enums.StatusConsulta;
import com.example.demo.dto.Request.ConsultaRequestDTO;
import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.mapper.ConsultaMapper;
import com.example.demo.repository.ConsultaRepository;
import com.example.demo.repository.DisponibilidadeRepository;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.repository.PacienteRepository;
import com.example.demo.service.Utils.ApiResponse;

@Service
public class ConsultaService {

    // Atributos para receber os repositories
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;

    // Construtor para injetar os repositories
    public ConsultaService(
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository,
            ConsultaRepository consultaRepository,
            DisponibilidadeRepository disponibilidadeRepository) {

        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    public ApiResponse<ConsultaResponseDTO> criarConsulta(
            ConsultaRequestDTO consultaRequestDTO) {

        Paciente paciente = pacienteRepository.findById(
                consultaRequestDTO.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Medico medico = medicoRepository.findById(
                consultaRequestDTO.medicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        // validar data passada
        if (consultaRequestDTO.dataHora()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Não é permitido agendar consultas em datas passadas");
        }

        // validar conflito de horário
        boolean medicoOcupado = consultaRepository.existsByMedicoAndDataHoraAndStatus(
                medico,
                consultaRequestDTO.dataHora(),
                StatusConsulta.AGENDADA);

        if (medicoOcupado) {

            throw new RuntimeException(
                    "O médico já possui consulta agendada nesse horário");
        }

        // validar disponibilidade do médico
        var disponibilidades = disponibilidadeRepository.findByMedicoAndDiaSemana(
                medico,
                consultaRequestDTO.dataHora().getDayOfWeek());

        boolean disponivel = disponibilidades.stream()
                .anyMatch(disponibilidade ->

                !consultaRequestDTO.dataHora()
                        .toLocalTime()
                        .isBefore(disponibilidade.getHorarioInicio())

                        &&

                        !consultaRequestDTO.dataHora()
                                .toLocalTime()
                                .isAfter(disponibilidade.getHorarioFim()));

        if (!disponivel) {

            throw new RuntimeException(
                    "O médico não possui disponibilidade nesse horário");
        }

        Consulta consulta = ConsultaMapper.toEntityConsulta(
                consultaRequestDTO,
                paciente,
                medico);

        consultaRepository.save(consulta);

        ConsultaResponseDTO dto = ConsultaMapper.toConsultaResponseDTO(consulta);

        return new ApiResponse<>(dto);
    }

    // Método para listar consulta por ID
    public ApiResponse<ConsultaResponseDTO> listarPorId(Long id) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        ConsultaResponseDTO dto = ConsultaMapper.toConsultaResponseDTO(consulta);

        // por fim, retornamos o ApiResponse com o DTO dentro
        return new ApiResponse<>(dto);
    }

    // Método para listar todas as consultas
    public ApiResponse<List<ConsultaResponseDTO>> listarTodos() {

        List<ConsultaResponseDTO> consultas = consultaRepository.findAll()
                .stream()
                .map(ConsultaMapper::toConsultaResponseDTO)
                .toList();

        return new ApiResponse<>(consultas);
    }

    public ApiResponse<ConsultaResponseDTO> atualizarStatus(
            Long id,
            String novoStatus) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        StatusConsulta status;

        try {
            status = StatusConsulta.valueOf(novoStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido");
        }

        // impedir cancelamento via PATCH
        if (status == StatusConsulta.CANCELADA) {
            throw new RuntimeException(
                    "Use o endpoint DELETE /consultas/{id} para cancelar consultas");
        }

        if (status == StatusConsulta.CONCLUIDA) {
            consulta.concluir();
        }

        consultaRepository.save(consulta);

        return new ApiResponse<>(
                ConsultaMapper.toConsultaResponseDTO(consulta));
    }

    // cancelar consulta
    public ApiResponse<String> cancelarConsulta(Long id) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new RuntimeException("Consulta já está cancelada");
        }

        if (consulta.getStatus() == StatusConsulta.CONCLUIDA) {
            throw new RuntimeException("Consulta já foi concluída");
        }

        consulta.cancelar(LocalDateTime.now());

        consultaRepository.save(consulta);

        return new ApiResponse<>("Consulta cancelada com sucesso");
    }

}
