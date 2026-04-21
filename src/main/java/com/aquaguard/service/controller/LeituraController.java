package com.aquaguard.service.controller;

import com.aquaguard.service.service.LeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LeituraController {

    @Autowired
    private LeituraService leituraService;

    // Endpoint para registrar leitura (PÚBLICO - Simula a chegada de dados IoT)
    @PostMapping("/leituras")
    public ResponseEntity<?> registrarLeitura(@RequestParam Long sensorId, @RequestParam Double valor) {
        try {
            leituraService.registrarLeitura(sensorId, valor);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para relatório de consumo mensal (PROTEGIDO - Apenas USER/ADMIN)
    @GetMapping("/relatorios/consumo-mensal/{sensorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Double>> gerarRelatorioConsumoMensal(@PathVariable Long sensorId,
                                                                    @RequestParam int ano,
                                                                    @RequestParam int mes) {
        List<Double> consumos = leituraService.gerarRelatorioConsumoMensal(sensorId, ano, mes);
        return ResponseEntity.ok(consumos);
    }
}
