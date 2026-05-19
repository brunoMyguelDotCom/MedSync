package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.Entities.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    List<Medico> findByEspecialidade_Id(Long especialidadeId);

}