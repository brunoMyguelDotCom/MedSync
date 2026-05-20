package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Paciente;
import com.example.demo.Entities.Enums.StatusConsulta;
import com.example.demo.dto.Request.PacienteRequestDTO;
import com.example.demo.dto.Response.PacienteResponseDTO;
import com.example.demo.mapper.PacienteMapper;
import com.example.demo.repository.ConsultaRepository;
import com.example.demo.repository.PacienteRepository;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public PacienteService(PacienteRepository pacienteRepository, ConsultaRepository consultaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
    }

    // Método para criar um novo paciente
    public ApiResponse<PacienteResponseDTO> criarPaciente(PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = PacienteMapper.toEntityPaciente(pacienteRequestDTO);
        pacienteRepository.save(paciente);
        PacienteResponseDTO response = PacienteMapper.toPacienteResponseDTO(paciente);
        return new ApiResponse<>(response);
    }

    // Método para listar todos os pacientes com paginação opcional
    public ApiResponse<List<PacienteResponseDTO>> listarTodos(Integer page, Integer size) {
        List<PacienteResponseDTO> pacientes;
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            pacientes = pacienteRepository.findAll(pageable)
                    .map(PacienteMapper::toPacienteResponseDTO)
                    .toList();
        } else {
            pacientes = pacienteRepository.findAll()
                    .stream()
                    .map(PacienteMapper::toPacienteResponseDTO)
                    .toList();
        }
        return new ApiResponse<>(pacientes);
    }

    // Método para buscar um paciente por ID
    public ApiResponse<PacienteResponseDTO> buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> new ApiResponse<>(PacienteMapper.toPacienteResponseDTO(paciente)))
                .orElseGet(() -> new ApiResponse<>(new ErrorResponse("Not Found", "Paciente não encontrado")));
    }

    // Método para atualizar um paciente existente
    public ApiResponse<PacienteResponseDTO> atualizarPaciente(Long id, PacienteRequestDTO pacienteRequestDTO) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setNome(pacienteRequestDTO.nome());
                    paciente.setTelefone(pacienteRequestDTO.telefone());
                    paciente.setEmail(pacienteRequestDTO.email());
                    pacienteRepository.save(paciente);
                    return new ApiResponse<>(PacienteMapper.toPacienteResponseDTO(paciente));
                })
                .orElseGet(() -> new ApiResponse<>(new ErrorResponse("Not Found", "Paciente não encontrado")));
    }

    public ApiResponse<String> removerPaciente(Long id) {

        return pacienteRepository.findById(id)
                .map(paciente -> {

                    if (!paciente.isAtivo()) {
                        return new ApiResponse<String>(
                                new ErrorResponse("Forbidden",
                                        "Paciente já está inativo"));
                    }

                    boolean temConsultasAgendadas = consultaRepository.findByPacienteId(id)
                            .stream()
                            .anyMatch(consulta -> consulta.getStatus() == StatusConsulta.AGENDADA);

                    if (temConsultasAgendadas) {
                        return new ApiResponse<String>(
                                new ErrorResponse(
                                        "Forbidden",
                                        "Não é permitido remover paciente com consultas agendadas"));
                    }

                    paciente.setAtivo(false);

                    pacienteRepository.save(paciente);

                    return new ApiResponse<>("Paciente desativado com sucesso");
                })
                .orElseGet(() -> new ApiResponse<>(
                        new ErrorResponse(
                                "Not Found",
                                "Paciente não encontrado")));
    }
}
