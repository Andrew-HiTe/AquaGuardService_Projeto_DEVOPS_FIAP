package com.aquaguard.service.service;

import com.aquaguard.service.model.Sensor;
import com.aquaguard.service.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public Sensor cadastrarSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Optional<Sensor> buscarSensorPorId(Long id) {
        return sensorRepository.findById(id);
    }
}
