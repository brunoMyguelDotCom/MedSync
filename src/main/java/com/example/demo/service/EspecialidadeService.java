package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Especialidade;
import com.example.demo.dto.Request.EspecialidadeRequestDTO;
import com.example.demo.dto.Response.EspecialidadeResponseDTO;
import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.mapper.EspecialidadeMapper;
import com.example.demo.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    public ApiResponse<EspecialidadeResponseDTO> criarEspecialidade(EspecialidadeRequestDTO especialidadeRequestDTO) {

        Especialidade especialidade = new EspecialidadeMapper().toEntityEspecialidade(especialidadeRequestDTO);

        especialidadeRepository.save(especialidade);

        EspecialidadeResponseDTO dto = EspecialidadeMapper.toEspecialidadeResponseDTO(especialidade);

        return new ApiResponse<>(dto);
    }

    // Listar Especialidade
    public ApiResponse<List<EspecialidadeResponseDTO>> listarEspecialidade() {

        List<EspecialidadeResponseDTO> especialidades = especialidadeRepository.findAll()
                .stream()
                .map(EspecialidadeMapper::toEspecialidadeResponseDTO)
                .toList();

        return new ApiResponse<>(especialidades);

    }

    // Buscar Especialidade
    public ApiResponse<EspecialidadeResponseDTO> buscarPorId(long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade nao encontrada"));

        EspecialidadeResponseDTO dto = EspecialidadeMapper.toEspecialidadeResponseDTO(especialidade);

        return new ApiResponse<>(dto);
    }

    // Atualizar especialidade
    public ApiResponse<EspecialidadeResponseDTO> atualizarEspecialidade(Long id, EspecialidadeRequestDTO dto) {

        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        especialidade.setNome(dto.nome());

        especialidadeRepository.save(especialidade);

        EspecialidadeResponseDTO responseDTO = EspecialidadeMapper.toEspecialidadeResponseDTO(especialidade);

        return new ApiResponse<>(responseDTO);
    }

    // Deletar especialidade
    public ApiResponse<String> deletarEspecialidade(Long id) {

        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada"));

        especialidadeRepository.delete(especialidade);

        return new ApiResponse<>("Especialidade removida com sucesso");
    }
}
