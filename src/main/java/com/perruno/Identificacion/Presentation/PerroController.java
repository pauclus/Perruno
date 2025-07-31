package com.perruno.Identificacion.Presentation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import com.perruno.Identificacion.Domain.Perro;
import com.perruno.Identificacion.Application.PerroService;
import com.perruno.Identificacion.Infrastructure.PerroRepository;

@RestController
@RequestMapping("/api/perros")
public class PerroController {

    @Autowired
    private PerroService perroService;
    
    @Autowired
    private PerroRepository perroRepository;
    
    /**
     * Crea un nuevo perro y lo asocia a un usuario como dueño principal
     */
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Perro> crearPerro(@RequestBody Perro perro, @RequestParam Integer idUsuario) {
        return perroService.registrarPerro(perro, idUsuario);
    }
    
    /**
     * Guarda un perro sin asociarlo a un usuario
     */
    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Perro> guardarPerro(@RequestBody Perro perro) {
        return perroRepository.save(perro);
    }
    
    /**
     * Actualiza la información de un perro existente
     */
    @PutMapping("/{idPerro}")
    public Mono<Perro> actualizarPerro(@PathVariable Integer idPerro, @RequestBody Perro perro) {
        return perroService.actualizarPerro(idPerro, perro);
    }
    
    /**
     * Obtiene un perro por su ID
     */
    @GetMapping("/{idPerro}")
    public Mono<Perro> obtenerPerroPorId(@PathVariable Integer idPerro) {
        return perroService.obtenerPerroPorId(idPerro);
    }
    
    /**
     * Obtiene todos los perros registrados
     */
    @GetMapping
    public Flux<Perro> obtenerTodosLosPerros() {
        return perroService.obtenerTodosLosPerros();
    }
    
    /**
     * Obtiene todos los perros de un usuario
     */
    @GetMapping("/usuario/{idUsuario}")
    public Flux<Perro> obtenerPerrosPorUsuario(@PathVariable Integer idUsuario) {
        return perroService.obtenerPerrosPorDueño(idUsuario);
    }

    @GetMapping("/por-raza")
    public Flux<Integer> contarPorRazas(@RequestBody List<String> razas) {
        return perroService.contarPerrosPorRazas(razas);
    }

    
    /**
     * Busca perros por criterios
     */
    @GetMapping("/buscar")
    public Flux<Perro> buscarPerros(
            @RequestParam String nombre,
            @RequestParam(required = false) Integer dueñoId,
            @RequestParam(required = false) String raza,
            @RequestParam(required = false) Integer edad,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Perro.Comportamiento comportamiento,
            @RequestParam(required = false) Perro.Tamaño tamaño,
            @RequestParam(required = false) String ubicacion) {
        return perroRepository.buscarPerros(nombre, dueñoId, raza, edad, color, comportamiento, tamaño, ubicacion);
    }
}