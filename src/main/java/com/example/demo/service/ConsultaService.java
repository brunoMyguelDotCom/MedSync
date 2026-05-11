package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.Paciente;
import com.example.demo.dto.Request.ConsultaRequestDTO;
import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.mapper.ConsultaMapper;
import com.example.demo.repository.ConsultaRepository;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.repository.PacienteRepository;
import com.example.demo.service.Utils.ApiResponse;

@Service
public class ConsultaService {

    // Atributos para receber os repositories
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    // Construtor para injetar os repositories
    public ConsultaService(PacienteRepository pacienteRepository, MedicoRepository medicoRepository,
            ConsultaRepository consultaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
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

    // Método para criar uma nova consulta
    public ApiResponse<ConsultaResponseDTO> criarConsulta(Long id, ConsultaRequestDTO consultaRequestDTO) {

        Paciente paciente = pacienteRepository.findById(consultaRequestDTO.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Medico medico = medicoRepository.findById(consultaRequestDTO.medicoId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        Consulta consulta = new ConsultaMapper().toEntityConsulta(consultaRequestDTO, paciente, medico);

        consultaRepository.save(consulta);

        ConsultaResponseDTO dto = ConsultaMapper.toConsultaResponseDTO(consulta);

        return new ApiResponse<>(dto);

    }
}
