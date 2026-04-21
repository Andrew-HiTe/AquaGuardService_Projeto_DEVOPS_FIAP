package com.aquaguard.service.service;

import com.aquaguard.service.model.Alerta;
import com.aquaguard.service.model.Leitura;
import com.aquaguard.service.model.Sensor;
import com.aquaguard.service.repository.AlertaRepository;
import com.aquaguard.service.repository.LeituraRepository;
import com.aquaguard.service.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class LeituraService {

    @Autowired
    private LeituraRepository leituraRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    public Leitura registrarLeitura(Long sensorId, Double valor) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        Leitura leitura = new Leitura();
        leitura.setSensor(sensor);
        leitura.setValor(valor);

        // Lógica de Alerta (Exemplo: Alerta se o valor for maior que o limite de consumo)
        if ("CONSUMO".equals(sensor.getTipo()) && sensor.getLimiteConsumo() != null && valor > sensor.getLimiteConsumo()) {
            Alerta alerta = new Alerta();
            alerta.setSensor(sensor);
            alerta.setMensagem("ALERTA: Consumo excessivo detectado no sensor " + sensorId + ". Valor: " + valor + " > Limite: " + sensor.getLimiteConsumo());
            alertaRepository.save(alerta);
        }

        return leituraRepository.save(leitura);
    }

    public List<Double> gerarRelatorioConsumoMensal(Long sensorId, int ano, int mes) {
        LocalDateTime inicio = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fim = inicio.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        return leituraRepository.findConsumoMensal(sensorId, inicio, fim);
    }
}
