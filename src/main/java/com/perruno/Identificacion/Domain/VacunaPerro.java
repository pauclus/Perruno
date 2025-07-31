package com.perruno.Identificacion.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

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

    public VacunaPerro() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPerro() {
        return idPerro;
    }

    public void setIdPerro(Integer idPerro) {
        this.idPerro = idPerro;
    }

    public Integer getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(Integer idVacuna) {
        this.idVacuna = idVacuna;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getVeterinaria() {
        return veterinaria;
    }

    public void setVeterinaria(String veterinaria) {
        this.veterinaria = veterinaria;
    }
}
