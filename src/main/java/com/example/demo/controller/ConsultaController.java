package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Response.ConsultaResponseDTO;
import com.example.demo.service.ConsultaService;
import com.example.demo.service.Utils.ApiResponse;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    // padrao de projeto que vamos usar:

    // atributo / variavel pra receber o service
    private final ConsultaService consultaService;

    // construtor para injetar o service
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // endpoint para listar consulta por id
    @GetMapping("/{id}")
    // esse retorno "ApiResponse<>" ja estava criado pelo Fabricio, todo retorno de funcao do controller vai ser desse tipo (segure control e clique encima do ApiResponse<> pra ver a classe)

    // o que tem dentro do "<>" é o tipo RESPONSEDTO que cada classe tem (dentro de controller tambem seque esse padrao)
    public ApiResponse<ConsultaResponseDTO> listarPorId(@PathVariable Long id) {

        // ou seja, controller chama o service, o service retorna o DTO usando o ApiResponse, e o controller retorna esse ApiResponse pro cliente (frontend)
        return consultaService.listarPorId(id);
    }

    @GetMapping()
    // para o listar todos, a mesma coisa, como vai LISTAR, vamos retornar dentro do ApiResponse uma LISTA de ConsultaResponseDTO:
    public ApiResponse<List<ConsultaResponseDTO>> listaTodos() {

        return consultaService.listaTodos();
    }

}
