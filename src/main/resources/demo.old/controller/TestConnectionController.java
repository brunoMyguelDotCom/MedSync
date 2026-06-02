package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class TestConnectionController {

    private final DataSource dataSource;

    public TestConnectionController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/test-db")
    public String testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "Conectado com sucesso!";
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
}