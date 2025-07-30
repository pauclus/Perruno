package com.perruno.Identificacion.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table("perros")
public class Perro {

    @Id
    private Integer idPerro;

    private String nombre;
    private String raza;
    private Integer edad;
    private String color;

    private Tamaño tamaño;

    @Column("foto_url")
    private String fotoUrl;

    @Column("chip_codigo")
    private String chipCodigo;

    @Column("qr_codigo")
    private String qrCodigo;

    private Estado estado;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    public enum Tamaño {
        pequeño, mediano, grande
    }

    public enum Estado {
        activo, perdido, adoptado
    }
}

