package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Especialidade;
import com.example.demo.Entities.Medico;
import com.example.demo.dto.Request.MedicoRequestDTO;
import com.example.demo.dto.Response.MedicoResponseDTO;
import com.example.demo.mapper.MedicoMapper;
import com.example.demo.repository.EspecialidadeRepository;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.service.Utils.ApiResponse;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public MedicoService(MedicoRepository medicoRepository, EspecialidadeRepository especialidadeRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    //CRIAR MEDICO
    public ApiResponse<MedicoResponseDTO> criarMedico(MedicoRequestDTO medicoRequestDTO) {

    Especialidade especialidade = especialidadeRepository.findById(medicoRequestDTO.especialidadeId())
        .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

    Medico medico = MedicoMapper.toEntityMedico(medicoRequestDTO);
    medico.setEspecialidade(especialidade);

    medicoRepository.save(medico);

    MedicoResponseDTO response = MedicoMapper.toMedicoResponseDTO(medico);

    return new ApiResponse<>(response); // só passa o dado
}
    // listar medico
    // buscar medico
    // atualizar medico
    // remover medico

}
