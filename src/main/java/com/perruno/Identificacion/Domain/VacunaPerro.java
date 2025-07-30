package com.perruno.Identificacion.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Table("vacunas_perros")
public class VacunaPerro {

    @Id
    private Integer id;

    @Column("id_perro")
    private Integer idPerro;

    @Column("id_vacuna")
    private Integer idVacuna;

    @Column("fecha_aplicacion")
    private LocalDate fechaAplicacion;

    @Column("fecha_vencimiento")
    private LocalDate fechaVencimiento;

    private String veterinaria;
}
