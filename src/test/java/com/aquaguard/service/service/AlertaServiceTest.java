package com.aquaguard.service.service;

import com.aquaguard.service.model.Alerta;
import com.aquaguard.service.model.Sensor;
import com.aquaguard.service.repository.AlertaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private AlertaService alertaService;

    @Test
    void resolverAlerta_alertaExistente_marcaComoResolvido() {
        Sensor sensor = new Sensor();
        Alerta alerta = new Alerta();
        alerta.setId(1L);
        alerta.setSensor(sensor);
        alerta.setMensagem("Consumo excessivo");
        alerta.setResolvido(false);

        when(alertaRepository.findById(1L)).thenReturn(Optional.of(alerta));
        when(alertaRepository.save(any(Alerta.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Alerta> resultado = alertaService.resolverAlerta(1L);

        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().getResolvido());
        verify(alertaRepository, times(1)).save(alerta);
    }

    @Test
    void resolverAlerta_alertaInexistente_retornaEmpty() {
        when(alertaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Alerta> resultado = alertaService.resolverAlerta(99L);

        assertTrue(resultado.isEmpty());
        verify(alertaRepository, never()).save(any());
    }

    @Test
    void listarAlertas_retornaListaVazia_quandoNaoHaAlertas() {
        when(alertaRepository.findAll()).thenReturn(List.of());

        List<Alerta> resultado = alertaService.listarAlertas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarAlertas_retornaAlertas_quandoExistem() {
        Alerta a1 = new Alerta();
        a1.setId(1L);
        a1.setResolvido(false);

        Alerta a2 = new Alerta();
        a2.setId(2L);
        a2.setResolvido(true);

        when(alertaRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Alerta> resultado = alertaService.listarAlertas();

        assertEquals(2, resultado.size());
    }
}
