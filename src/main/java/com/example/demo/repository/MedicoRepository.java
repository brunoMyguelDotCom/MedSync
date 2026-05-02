package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{
    //Ja esta funcionando o spring gera automaticamente: save, findById, findAll, delete, etc.
}   
