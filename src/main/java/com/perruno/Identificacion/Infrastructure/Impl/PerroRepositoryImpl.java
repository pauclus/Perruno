package com.perruno.Identificacion.Infrastructure.Impl;

import com.perruno.Identificacion.Domain.Perro;
import com.perruno.Identificacion.Infrastructure.Custom.PerroRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PerroRepositoryImpl implements PerroRepositoryCustom {

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Flux<Perro> buscarPerros(String nombre, Integer dueñoId, String raza, Integer edad, String color, Perro.Comportamiento comportamiento, Perro.Tamaño tamaño, String ubicacion) {
        StringBuilder query = new StringBuilder("SELECT * FROM perros WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))");
        List<Object> params = new ArrayList<>();
        params.add(nombre);
        
        // Agregar filtros opcionales si no son nulos
        if (raza != null) {
            query.append(" AND raza = :raza");
            params.add(raza);
        }
        
        if (edad != null) {
            query.append(" AND edad = :edad");
            params.add(edad);
        }
        
        if (color != null) {
            query.append(" AND color = :color");
            params.add(color);
        }
        
        if (tamaño != null) {
            query.append(" AND tamaño = :tamaño");
            params.add(tamaño.toString());
        }
        
        // Construir la consulta con DatabaseClient
        DatabaseClient.GenericExecuteSpec executeSpec = databaseClient.sql(query.toString())
                .bind("nombre", nombre);
        
        // Agregar parámetros opcionales a la consulta
        if (raza != null) {
            executeSpec = executeSpec.bind("raza", raza);
        }
        
        if (edad != null) {
            executeSpec = executeSpec.bind("edad", edad);
        }
        
        if (color != null) {
            executeSpec = executeSpec.bind("color", color);
        }
        
        if (tamaño != null) {
            executeSpec = executeSpec.bind("tamaño", tamaño.toString());
        }
        
        if (comportamiento != null) {
            executeSpec = executeSpec.bind("comportamiento", comportamiento.toString());
        }
        
        if (ubicacion != null) {
            executeSpec = executeSpec.bind("ubicacion", ubicacion);
        }

        if (dueñoId != null) {
            executeSpec = executeSpec.bind("id_dueño", dueñoId);
        }
        
        // Ejecutar la consulta y mapear los resultados a objetos Perro
        return executeSpec.map((row, metadata) -> {
            Perro perro = new Perro();
            perro.setIdPerro(row.get("id_perro", Integer.class));
            perro.setNombre(row.get("nombre", String.class));
            perro.setRaza(row.get("raza", String.class));
            perro.setEdad(row.get("edad", Integer.class));
            perro.setColor(row.get("color", String.class));
            
            String tamañoStr = row.get("tamaño", String.class);
            if (tamañoStr != null) {
                perro.setTamaño(Perro.Tamaño.valueOf(tamañoStr));
            }
            
            perro.setFotoUrl(row.get("foto_url", String.class));
            perro.setChipCodigo(row.get("chip_codigo", String.class));
            perro.setQrCodigo(row.get("qr_codigo", String.class));
            
            String estadoStr = row.get("estado", String.class);
            if (estadoStr != null) {
                perro.setEstado(Perro.Estado.valueOf(estadoStr));
            }
            
            perro.setFechaRegistro(row.get("fecha_registro", java.time.LocalDateTime.class));
            
            return perro;
        }).all();
    }
}