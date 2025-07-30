package com.perruno.Identificacion.Presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.perruno.Identificacion.Domain.Dueño;
import com.perruno.Identificacion.Application.DueñoService;
import com.perruno.Identificacion.Infrastructure.DueñoRepository;

@RestController
@RequestMapping("/api/dueños")
public class DueñoController {

    @Autowired
    private DueñoService dueñoService;
    
    @Autowired
    private DueñoRepository dueñoRepository;
    
    /**
     * Registra un nuevo dueño
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Dueño> crearDueño(@RequestBody Dueño dueño) {
        return dueñoRepository.save(dueño);
    }
    
    /**
     * Busca un dueño por su email
     */
    @GetMapping("/email/{email}")
    public Mono<Dueño> buscarPorEmail(@PathVariable String email) {
        return dueñoRepository.findByEmail(email);
    }
    
    /**
     * Obtiene un dueño por su ID
     */
    @GetMapping("/{idDueño}")
    public Mono<Dueño> obtenerDueñoPorId(@PathVariable Integer idDueño) {
        return dueñoRepository.findById(idDueño);
    }
    

    

    


}