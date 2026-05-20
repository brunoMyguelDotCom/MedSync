package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Especialidade;
import com.example.demo.Entities.Medico;
import com.example.demo.dto.Request.MedicoRequestDTO;
import com.example.demo.dto.Response.MedicoResponseDTO;
import com.example.demo.mapper.MedicoMapper;
import com.example.demo.repository.EspecialidadeRepository;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.repository.ConsultaRepository;
import com.example.demo.repository.DisponibilidadeRepository;
import com.example.demo.service.Utils.ApiResponse;

@Service
public class MedicoService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final ConsultaRepository consultaRepository;

    public MedicoService(MedicoRepository medicoRepository, EspecialidadeRepository especialidadeRepository,
            DisponibilidadeRepository disponibilidadeRepository, ConsultaRepository consultaRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.consultaRepository = consultaRepository;
    }

    // CRIAR MEDICO
    public ApiResponse<MedicoResponseDTO> criarMedico(MedicoRequestDTO medicoRequestDTO) {

        Especialidade especialidade = especialidadeRepository.findById(medicoRequestDTO.especialidadeId())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        Medico medico = MedicoMapper.toEntityMedico(medicoRequestDTO);
        medico.setEspecialidade(especialidade);

        medicoRepository.save(medico);

        MedicoResponseDTO response = MedicoMapper.toMedicoResponseDTO(medico);

        return new ApiResponse<>(response);
    }

    // LISTAR MEDICO
    public ApiResponse<List<MedicoResponseDTO>> listarMedicos(Long especialidadeId, Boolean disponivel) {

        List<Medico> medicos;

        if (especialidadeId != null) {
            medicos = medicoRepository.findByEspecialidade_Id(especialidadeId);
        } else {
            medicos = medicoRepository.findAll();
        }

        if (Boolean.TRUE.equals(disponivel)) {
            medicos = medicos.stream()
                    .filter(m -> !disponibilidadeRepository.findByMedico(m).isEmpty())
                    .toList();
        }

        List<MedicoResponseDTO> listar = medicos.stream()
                .map(MedicoMapper::toMedicoResponseDTO)
                .toList();

        return new ApiResponse<>(listar);
    }

    // BUSCAR MEDICO
    public ApiResponse<MedicoResponseDTO> buscarMedico(Long id) {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medico não encontrado"));

        MedicoResponseDTO dto = MedicoMapper.toMedicoResponseDTO(medico);

        return new ApiResponse<>(dto);
    }

    // ATUALIZAR MEDICO
    public ApiResponse<MedicoResponseDTO> atualizarMedico(Long id, MedicoRequestDTO medicoRequestDTO) {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medico não encontrado"));

        Especialidade especialidade = especialidadeRepository.findById(medicoRequestDTO.especialidadeId())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        medico.setNome(medicoRequestDTO.nome());
        medico.setCrm(medicoRequestDTO.crm());
        medico.setEspecialidade(especialidade);

        medicoRepository.save(medico);

        MedicoResponseDTO response = MedicoMapper.toMedicoResponseDTO(medico);

        return new ApiResponse<>(response);
    }

    // REMOVER MEDICO
    public ApiResponse<String> removerMedico(Long id) {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        if (!medico.getAtivo()) {
            throw new RuntimeException("Médico já está inativo");
        }

        List<Consulta> consultasFuturas = consultaRepository
                .findByMedicoIdAndDataHoraAfter(id, LocalDateTime.now());

        if (!consultasFuturas.isEmpty()) {
            throw new RuntimeException("Não é possível remover o médico pois existem consultas futuras");
        }

        medico.setAtivo(false);
        medicoRepository.save(medico);

        return new ApiResponse<>("Médico desativado com sucesso");
    }

}
