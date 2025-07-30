package com.perruno.Identificacion.Presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.Vacuna;
import com.perruno.Identificacion.Domain.VacunaPerro;
import com.perruno.Identificacion.Application.VacunaService;
import com.perruno.Identificacion.Infrastructure.VacunaRepository;
import com.perruno.Identificacion.Infrastructure.VacunaPerroRepository;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/vacunas")
public class VacunaController {

    @Autowired
    private VacunaService vacunaService;
    
    @Autowired
    private VacunaRepository vacunaRepository;
    
    @Autowired
    private VacunaPerroRepository vacunaPerroRepository;
    
    /**
     * Crea una nueva vacuna
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Vacuna> crearVacuna(@RequestBody Vacuna vacuna) {
        return vacunaRepository.save(vacuna);
    }
    
    /**
     * Registra la aplicación de una vacuna a un perro
     */
    @PostMapping("/aplicar")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VacunaPerro> registrarVacunaPerro(@RequestBody VacunaPerro vacunaPerro) {
        return vacunaService.registrarVacunaPerro(vacunaPerro);
    }
    
    /**
     * Vacuna a un perro (crea un registro de vacunación)
     */
    @PostMapping("/perros/{idPerro}/vacunar/{idVacuna}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VacunaPerro> vacunarPerro(
            @PathVariable Integer idPerro,
            @PathVariable Integer idVacuna,
            @RequestParam(required = false) String veterinaria,
            @RequestParam(required = false) LocalDate fechaVencimiento) {
        
        VacunaPerro vacunaPerro = new VacunaPerro();
        vacunaPerro.setIdPerro(idPerro);
        vacunaPerro.setIdVacuna(idVacuna);
        vacunaPerro.setFechaAplicacion(LocalDate.now());
        vacunaPerro.setVeterinaria(veterinaria);
        
        if (fechaVencimiento != null) {
            vacunaPerro.setFechaVencimiento(fechaVencimiento);
        }
        
        return vacunaService.registrarVacunaPerro(vacunaPerro);
    }
    
    /**
     * Obtiene todas las vacunas disponibles
     */
    @GetMapping
    public Flux<Vacuna> obtenerTodasLasVacunas() {
        return vacunaService.obtenerTodasLasVacunas();
    }
    
    /**
     * Obtiene todas las vacunas aplicadas a un perro
     */
    @GetMapping("/perros/{idPerro}")
    public Flux<VacunaPerro> obtenerVacunasPerro(@PathVariable Integer idPerro) {
        return vacunaService.obtenerVacunasPerro(idPerro);
    }
    
    /**
     * Estima el tiempo restante de efectividad de una vacuna en días
     */
    @GetMapping("/aplicaciones/{idVacunaPerro}/tiempo-restante")
    public Mono<Long> estimarTiempoRestanteVacuna(@PathVariable Integer idVacunaPerro) {
        return vacunaService.estimarTiempoRestanteVacuna(idVacunaPerro);
    }
    
    /**
     * Verifica las vacunas próximas a vencer y genera notificaciones
     */
    @PostMapping("/verificar-vencimientos")
    public Flux<Notificaciones> verificarVacunasPorVencer() {
        return vacunaService.verificarVacunasPorVencer();
    }
}