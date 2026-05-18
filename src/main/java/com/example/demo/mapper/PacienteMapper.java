package com.example.demo.mapper;

import com.example.demo.Entities.Paciente;
import com.example.demo.dto.Request.PacienteRequestDTO;
import com.example.demo.dto.Response.PacienteResponseDTO;

public class PacienteMapper {

    public static PacienteResponseDTO toPacienteResponseDTO(Paciente paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getEmail(),
                paciente.getTelefone()
        );
    }

    public static Paciente toEntityPaciente(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());
        paciente.setTelefone(dto.telefone());
        return paciente;
    }
}
