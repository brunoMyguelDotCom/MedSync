package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // O Spring JPA oferece automaticamente metodos como save, findById, findAll dentre outros...
}
