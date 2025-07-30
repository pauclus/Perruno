package com.perruno.Identificacion.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@Table("usuarios_perros")
public class UsuarioPerro {

    @Id
    private UsuarioPerroKey id;

    @Column("es_dueño_principal")
    private Boolean esDueñoPrincipal;

    // Necesitas una clase para la clave compuesta
    @Data
    @NoArgsConstructor
    public static class UsuarioPerroKey {
        private Integer idUsuario;
        private Integer idPerro;
    }
}
