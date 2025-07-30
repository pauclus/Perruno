package com.perruno.Identificacion.Infrastructure;

import com.perruno.Identificacion.Domain.Perro;
import reactor.core.publisher.Flux;

public interface PerroRepositoryCustom {
    
    /**
     * Busca perros según criterios opcionales
     * @param nombre Nombre del perro (obligatorio, búsqueda parcial)
     * @param raza Raza del perro (opcional)
     * @param edad Edad del perro (opcional)
     * @param color Color del perro (opcional)
     * @param tamaño Tamaño del perro (opcional)
     * @return Flux de perros que coinciden con los criterios
     */
    Flux<Perro> buscarPerros(String nombre, String raza, Integer edad, String color, Perro.Tamaño tamaño);
}