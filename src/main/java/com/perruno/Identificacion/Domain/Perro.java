package com.perruno.Identificacion.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Table("perros")
public class Perro {

    @Id
    private Integer idPerro;

    @Column("id_dueño")
    private Integer idDueño;

    private String nombre;
    private String raza;
    private Integer edad;
    private String color;
    private String ubicacion;
    private Comportamiento comportamiento;
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

    public enum Comportamiento {
        tranquilo, juguetón, agresivo, ansioso, entrenado
    }

    public Perro() {}

    public Integer getIdPerro() {
        return idPerro;
    }

    public void setIdPerro(Integer idPerro) {
        this.idPerro = idPerro;
    }

    public Integer getIdDueño() {
        return idDueño;
    }

    public void setIdDueño(Integer idDueño) {
        this.idDueño = idDueño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Comportamiento getComportamiento() {
        return comportamiento;
    }

    public void setComportamiento(Comportamiento comportamiento) {
        this.comportamiento = comportamiento;
    }

    public Tamaño getTamaño() {
        return tamaño;
    }

    public void setTamaño(Tamaño tamaño) {
        this.tamaño = tamaño;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getChipCodigo() {
        return chipCodigo;
    }

    public void setChipCodigo(String chipCodigo) {
        this.chipCodigo = chipCodigo;
    }

    public String getQrCodigo() {
        return qrCodigo;
    }

    public void setQrCodigo(String qrCodigo) {
        this.qrCodigo = qrCodigo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
