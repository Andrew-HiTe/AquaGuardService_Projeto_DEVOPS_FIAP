package com.aquaguard.service.controller;

import com.aquaguard.service.model.Alerta;
import com.aquaguard.service.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    // Endpoint para listar alertas (PROTEGIDO - Apenas USER/ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Alerta>> listarAlertas() {
        return ResponseEntity.ok(alertaService.listarAlertas());
    }

    // Endpoint para resolver alerta (PROTEGIDO - Apenas ADMIN)
    @PutMapping("/{id}/resolver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Alerta> resolverAlerta(@PathVariable Long id) {
        return alertaService.resolverAlerta(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
