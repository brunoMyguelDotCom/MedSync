package com.example.demo.service;

import com.example.demo.Entities.Especialidade;
import com.example.demo.dto.Request.EspecialidadeRequestDTO;
import com.example.demo.dto.Response.EspecialidadeResponseDTO;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.mapper.EspecialidadeMapper;
import com.example.demo.repository.EspecialidadeRepository;

public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository){
        this.especialidadeRepository = especialidadeRepository;
    }

    public ApiResponse<EspecialidadeResponseDTO> criarEspecialidade(EspecialidadeRequestDTO especialidadeRequestDTO) {

        Especialidade especialidade = new EspecialidadeMapper().toEntityEspecialidade(especialidadeRequestDTO);

        especialidadeRepository.save(especialidade);

        EspecialidadeResponseDTO dto = EspecialidadeMapper.toEspecialidadeResponseDTO(especialidade);

        return new ApiResponse<>(dto);
    }

}
