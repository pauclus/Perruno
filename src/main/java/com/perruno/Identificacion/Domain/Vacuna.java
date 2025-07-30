package com.perruno.Identificacion.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("vacunas")
public class Vacuna {

    @Id
    private Integer idVacuna;

    private String nombre;
    private String descripcion;
}
