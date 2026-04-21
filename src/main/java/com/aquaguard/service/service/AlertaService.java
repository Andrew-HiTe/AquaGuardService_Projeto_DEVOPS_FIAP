package com.aquaguard.service.service;

import com.aquaguard.service.model.Alerta;
import com.aquaguard.service.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    public List<Alerta> listarAlertas() {
        return alertaRepository.findAll();
    }

    public Optional<Alerta> resolverAlerta(Long id) {
        Optional<Alerta> alertaOpt = alertaRepository.findById(id);
        if (alertaOpt.isPresent()) {
            Alerta alerta = alertaOpt.get();
            alerta.setResolvido(true);
            return Optional.of(alertaRepository.save(alerta));
        }
        return Optional.empty();
    }
}
