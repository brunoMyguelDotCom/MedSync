package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.repository.ConsultaRepository;
import com.example.demo.service.ConsultaService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    
    private final ConsultaService consultaService;

    public ConsultaController (ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/{id}")
    public ConsultaResponseDTO listarPorId( @PathVariable Long id) {

        return consultaService.listarPorId(id);
    }
    


}
