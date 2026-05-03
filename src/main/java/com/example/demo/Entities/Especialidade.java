package com.example.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "especialidades")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    public Especialidade(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Especialidade() {

    }

}
