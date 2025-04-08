package aceleradora.socios.back.controller;

import aceleradora.socios.back.clases.espacio.Recurso;
import aceleradora.socios.back.services.AppService;
import aceleradora.socios.back.services.ParticipanteService;
import aceleradora.socios.back.services.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recurso")
public class RecursoController {

    @Autowired
    RecursoService recursoService;
    @Autowired
    AppService appService;

    @GetMapping("/recursos")
    public ResponseEntity<List<Recurso>> getAllRecursos() {
        return  new ResponseEntity<>(recursoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Recurso> getRecursoById(@PathVariable Long id) {
        return recursoService.findById(id)
                .map(recurso -> ResponseEntity.ok().body(recurso))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<Recurso> createRecurso(@RequestBody Recurso recurso) {
        Recurso aux = recursoService.save(recurso);
        return  new ResponseEntity<>(aux, HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Recurso> updateRecurso(@PathVariable Long id, @RequestBody Recurso recursoDetails) {
        return recursoService.findById(id)
                .map(existingRecurso -> {
                    existingRecurso.setNombre(recursoDetails.getNombre());
                    Recurso updatedRecurso = recursoService.save(existingRecurso);
                    return ResponseEntity.ok().body(updatedRecurso);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //TODO: CAMBIAR NUNCA DEBO DARLO DE BAJA
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteRecurso(@PathVariable Long id) {
        return recursoService.findById(id)
                .map(recurso -> {
                    recursoService.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
