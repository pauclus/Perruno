package com.perruno.Identificacion.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table("dueños")
public class Dueño {

    @Id
    private Integer idDueño;

    private String nombre;
    private String email;
    private String telefono;
    private String direccion;


    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;


}
