package com.example.demo.mapper;

import com.example.demo.Entities.Medico;
import com.example.demo.dto.Request.MedicoRequestDTO;
import com.example.demo.dto.Response.MedicoResponseDTO;

public class MedicoMapper {

    public static MedicoResponseDTO toMedicoResponseDTO(Medico medico) {
        return new MedicoResponseDTO(
            medico.getId(),
            medico.getNome(),
            medico.getCrm(),
            medico.getEspecialidade().getNome() // retorna só o nome
        );
    }

    public static Medico toEntityMedico(MedicoRequestDTO dto) {
        Medico medico = new Medico();
        medico.setNome(dto.nome());
        medico.setCrm(dto.crm());
        // especialidade NÃO é setada aqui — ela é buscada no banco pelo Service
        return medico;
    }
}
