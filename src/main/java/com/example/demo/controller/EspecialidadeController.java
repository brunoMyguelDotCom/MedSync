package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Request.EspecialidadeRequestDTO;
import com.example.demo.dto.Response.EspecialidadeResponseDTO;
import com.example.demo.service.EspecialidadeService;
import com.example.demo.service.Utils.ApiResponse;

import lombok.experimental.var;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/especialidade")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService; 

    public EspecialidadeController(EspecialidadeService especialidadeService){
        this.especialidadeService = especialidadeService;
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadeResponseDTO>> criarEspecialidade (@PathVariable Long id, @RequestBody EspecialidadeRequestDTO especialidadeRequestDTO) {
        
        var response = especialidadeService.criarEspecialidade(id, especialidadeRequestDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
