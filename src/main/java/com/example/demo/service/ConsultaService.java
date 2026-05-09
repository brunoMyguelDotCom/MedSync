package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Consulta;
import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.repository.ConsultaRepository;
import com.example.demo.service.Utils.ApiResponse;

@Service
public class ConsultaService {

    // atributo / variavel pra receber o service
    private final ConsultaRepository consultaRepository;

    // construtor para injetar o service
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    // retorna ApiResponse com ConsultaResponseDTO dentro , mesma coisa do
    // Controller
    public ApiResponse<ConsultaResponseDTO> listarPorId(Long id) {

        // logica de criacao:
        // criamos uma consulta chamando repository com funcao do JPA (findById), e
        // sempre colocamos o OrElseThrow pra caso nao encontre a consulta
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        // depois criamos o DTO usando os dados da consulta acima
        ConsultaResponseDTO dto = new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getNome(),
                consulta.getDataHora(),
                consulta.getStatus(),
                consulta.getObservacoes());

        // por fim, retornamos o ApiResponse com o DTO dentro
        return new ApiResponse<>(dto);
    }

    public ApiResponse<List<ConsultaResponseDTO>> listaTodos() {
      
        // Uso de Stream para converter a lista de consultas em uma lista de ConsultaResponseDTO
        List<ConsultaResponseDTO> consultas = consultaRepository.findAll()
        .stream()
        .map(consulta -> new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getNome(),
                consulta.getDataHora(),
                consulta.getStatus(),
                consulta.getObservacoes()
        )).toList();

        // Retorna a lista de ConsultaResponseDTO dentro do ApiResponse
        return new ApiResponse<>(consultas);
    }
}
