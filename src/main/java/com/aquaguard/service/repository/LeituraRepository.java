package com.aquaguard.service.repository;

import com.aquaguard.service.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeituraRepository extends JpaRepository<Leitura, Long> {

    // Query para relatório de consumo mensal
    @Query("SELECT l.valor FROM Leitura l WHERE l.sensor.id = :sensorId AND l.timestamp BETWEEN :inicio AND :fim ORDER BY l.timestamp")
    List<Double> findConsumoMensal(@Param("sensorId") Long sensorId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
