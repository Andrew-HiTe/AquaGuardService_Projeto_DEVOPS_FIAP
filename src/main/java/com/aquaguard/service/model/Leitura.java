package com.aquaguard.service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "LEITURA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leitura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leitura_seq")
    @SequenceGenerator(name = "leitura_seq", sequenceName = "LEITURA_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENSOR_ID", nullable = false)
    private Sensor sensor;

    @Column(name = "VALOR", nullable = false)
    private Double valor; // Valor lido (e.g., litros consumidos, nível de vazamento)

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}
