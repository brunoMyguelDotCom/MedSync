package com.example.demo.mapper;

import com.example.demo.Entities.Consulta;
import com.example.demo.Entities.Medico;
import com.example.demo.Entities.Paciente;
import com.example.demo.dto.Request.ConsultaRequestDTO;
import com.example.demo.dto.Response.ConsultaResponseDTO;

public class ConsultaMapper {

    // converte os dados da Entidade para ResponseDTO
    public static ConsultaResponseDTO toConsultaResponseDTO(Consulta consulta) {

        // O Mapper pega o objeto inteiro que veio do banco e extrai só o que precisa.
        return new ConsultaResponseDTO(

                consulta.getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getNome(),
                consulta.getDataHora(),
                consulta.getStatus(),
                consulta.getObservacoes());
    }

    // Converte o RequestDTO em um objeto na Entidade
    public static Consulta toEntityConsulta(ConsultaRequestDTO dto, Paciente paciente, Medico medico) {

        return new Consulta(
                paciente, medico, dto.dataHora(), dto.observacoes());
    }

}
