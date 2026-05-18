package com.example.demo.service.Utils;

import com.example.demo.Entities.Paciente;
import com.example.demo.dto.Request.PacienteRequestDTO;
import com.example.demo.dto.Response.PacienteResponseDTO;
import com.example.demo.mapper.PacienteMapper;
import com.example.demo.repository.PacienteRepository;

public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    // CRIAR PACIENTE
    public ApiResponse<PacienteResponseDTO> criarPaciente(PacienteRequestDTO pacienteRequestDTO) {
    
    Paciente paciente = PacienteMapper.toEntityPaciente(pacienteRequestDTO);
        
    pacienteRepository.save(paciente);       
 
        PacienteResponseDTO response = PacienteMapper.toPacienteResponseDTO(paciente);
       
        return new ApiResponse<>(response); // só passa o dado
    }

}
