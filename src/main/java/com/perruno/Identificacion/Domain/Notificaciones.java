package com.perruno.Identificacion.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Table("notificaciones")
public class Notificaciones {

    @Id
    private Integer idNotificacion;

    @Column("id_usuario")
    private Integer idUsuario;

    private String mensaje;
    private Boolean leida;
    private Tipo tipo;

    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;

    public enum Tipo {
        vacuna, escaneo, alerta, sistema
    }
}

