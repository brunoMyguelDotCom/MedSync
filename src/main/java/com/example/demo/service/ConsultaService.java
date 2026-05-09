package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Consulta;
import com.example.demo.dto.Request.ConsultaRequestDTO;
import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.repository.ConsultaRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public ConsultaResponseDTO listarPorId(Long id) {

        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getNome(),
                consulta.getDataHora(),
                consulta.getStatus(),
                consulta.getObservacoes()
        );
        
    }

}
