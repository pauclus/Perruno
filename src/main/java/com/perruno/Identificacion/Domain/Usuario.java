package com.perruno.Identificacion.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table("usuarios")
public class Usuario {

    @Id
    private Integer idUsuario;

    private String nombre;
    private String email;
    private String telefono;
    private String direccion;

    private Rol rol;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    public enum Rol {
        propietario,
        admin
    }
}
