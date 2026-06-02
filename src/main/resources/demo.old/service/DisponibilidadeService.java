package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.Disponibilidade;
import com.example.demo.Entities.Medico;
import com.example.demo.dto.Request.DisponibilidadeRequestDTO;
import com.example.demo.dto.Response.DisponibilidadeResponseDTO;
import com.example.demo.mapper.DisponibilidadeMapper;
import com.example.demo.repository.DisponibilidadeRepository;
import com.example.demo.repository.MedicoRepository;
import com.example.demo.service.Utils.ApiResponse;

@Service
public class DisponibilidadeService {

        private final DisponibilidadeRepository disponibilidadeRepository;
        private final MedicoRepository medicoRepository;

        public DisponibilidadeService(
                        DisponibilidadeRepository disponibilidadeRepository,
                        MedicoRepository medicoRepository) {

                this.disponibilidadeRepository = disponibilidadeRepository;
                this.medicoRepository = medicoRepository;
        }

        // REGISTRAR DISPONIBILIDADE
        public ApiResponse<DisponibilidadeResponseDTO> registrarDisponibilidade(
                        Long medicoId,
                        DisponibilidadeRequestDTO disponibilidadeRequestDTO) {

                Medico medico = medicoRepository.findById(medicoId)
                                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

                // Validação de horário
                if (!disponibilidadeRequestDTO.horarioInicio()
                                .isBefore(disponibilidadeRequestDTO.horarioFim())) {

                        throw new RuntimeException(
                                        "Horário de início deve ser anterior ao horário de fim");
                }

                // Busca disponibilidades já cadastradas para o médico no mesmo dia
                List<Disponibilidade> disponibilidadesExistentes = disponibilidadeRepository.findByMedicoAndDiaSemana(
                                medico,
                                disponibilidadeRequestDTO.diaSemana());

                // Verificar horário
                for (Disponibilidade disponibilidadeExistente : disponibilidadesExistentes) {

                        boolean possuiConflito = disponibilidadeRequestDTO.horarioInicio()
                                        .isBefore(disponibilidadeExistente.getHorarioFim())

                                        &&

                                        disponibilidadeRequestDTO.horarioFim()
                                                        .isAfter(disponibilidadeExistente.getHorarioInicio());

                        if (possuiConflito) {
                                throw new RuntimeException(
                                                "O médico não tem disponibilidade nesse horário");
                        }
                }

                // DTO para Entity
                Disponibilidade disponibilidade = DisponibilidadeMapper.toEntityDisponibilidade(
                                disponibilidadeRequestDTO,
                                medico);

                disponibilidadeRepository.save(disponibilidade);

                // entity para ResponseDTO
                DisponibilidadeResponseDTO dto = DisponibilidadeMapper
                                .toDisponibilidadeResponseDTO(disponibilidade);

                return new ApiResponse<>(dto);
        }

        public ApiResponse<List<DisponibilidadeResponseDTO>> listarDisponibilidadesPorMedico(Long medicoId) {

                Medico medico = medicoRepository.findById(medicoId)
                                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

                List<DisponibilidadeResponseDTO> lista = disponibilidadeRepository.findByMedico(medico)
                                .stream()
                                .map(DisponibilidadeMapper::toDisponibilidadeResponseDTO)
                                .toList();

                return new ApiResponse<>(lista);
        }

        public void removerDisponibilidade(Long id) {

                Disponibilidade disponibilidade = disponibilidadeRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada"));

                if (!disponibilidade.isAtivo()) {
                        throw new RuntimeException("Disponibilidade já está inativa");
                }

                disponibilidade.setAtivo(false);

                disponibilidadeRepository.save(disponibilidade);
        }
}