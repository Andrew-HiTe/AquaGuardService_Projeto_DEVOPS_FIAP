package com.aquaguard.service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "SENSOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    @SequenceGenerator(name = "sensor_seq", sequenceName = "SENSOR_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "LOCALIZACAO", nullable = false)
    private String localizacao;

    @Column(name = "TIPO", nullable = false)
    private String tipo; // Ex: "CONSUMO", "VAZAMENTO"

    @Column(name = "LIMITE_CONSUMO")
    private Double limiteConsumo; // Limite em litros/hora

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo = true;
}
