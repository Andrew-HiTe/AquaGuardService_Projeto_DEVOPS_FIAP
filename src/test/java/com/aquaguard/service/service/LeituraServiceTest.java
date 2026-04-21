package com.aquaguard.service.service;

import com.aquaguard.service.model.Alerta;
import com.aquaguard.service.model.Leitura;
import com.aquaguard.service.model.Sensor;
import com.aquaguard.service.repository.AlertaRepository;
import com.aquaguard.service.repository.LeituraRepository;
import com.aquaguard.service.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeituraServiceTest {

    @Mock
    private LeituraRepository leituraRepository;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private LeituraService leituraService;

    @Test
    void registrarLeitura_sensorNaoEncontrado_lancaExcecao() {
        when(sensorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> leituraService.registrarLeitura(99L, 10.0));

        verify(leituraRepository, never()).save(any());
    }

    @Test
    void registrarLeitura_valorAbaixoLimite_naoGeraAlerta() {
        Sensor sensor = new Sensor();
        sensor.setId(1L);
        sensor.setTipo("CONSUMO");
        sensor.setLimiteConsumo(50.0);

        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));
        when(leituraRepository.save(any(Leitura.class))).thenAnswer(i -> i.getArgument(0));

        leituraService.registrarLeitura(1L, 30.0);

        verify(alertaRepository, never()).save(any(Alerta.class));
        verify(leituraRepository, times(1)).save(any(Leitura.class));
    }

    @Test
    void registrarLeitura_valorAcimaLimite_geraAlerta() {
        Sensor sensor = new Sensor();
        sensor.setId(1L);
        sensor.setTipo("CONSUMO");
        sensor.setLimiteConsumo(50.0);

        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));
        when(leituraRepository.save(any(Leitura.class))).thenAnswer(i -> i.getArgument(0));

        leituraService.registrarLeitura(1L, 75.0);

        verify(alertaRepository, times(1)).save(any(Alerta.class));
    }

    @Test
    void registrarLeitura_sensorTipoVazamento_naoGeraAlerta() {
        Sensor sensor = new Sensor();
        sensor.setId(2L);
        sensor.setTipo("VAZAMENTO");
        sensor.setLimiteConsumo(null);

        when(sensorRepository.findById(2L)).thenReturn(Optional.of(sensor));
        when(leituraRepository.save(any(Leitura.class))).thenAnswer(i -> i.getArgument(0));

        leituraService.registrarLeitura(2L, 999.0);

        verify(alertaRepository, never()).save(any(Alerta.class));
    }
}
