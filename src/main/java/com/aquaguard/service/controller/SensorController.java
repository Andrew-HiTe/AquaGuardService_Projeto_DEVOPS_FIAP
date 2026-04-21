package com.aquaguard.service.controller;

import com.aquaguard.service.model.Sensor;
import com.aquaguard.service.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Protegido: Apenas ADMIN pode cadastrar
    public ResponseEntity<Sensor> cadastrarSensor(@Valid @RequestBody Sensor sensor) {
        Sensor novoSensor = sensorService.cadastrarSensor(sensor);
        return new ResponseEntity<>(novoSensor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> buscarSensor(@PathVariable Long id) {
        return sensorService.buscarSensorPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
