package aceleradora.socios.back.controller;

import aceleradora.socios.back.clases.departamento.Puesto;
import aceleradora.socios.back.dto.AutoridadDTO;
import aceleradora.socios.back.dto.AutoridadPostDTO;
import aceleradora.socios.back.dto.DepartamentoDTO;
import aceleradora.socios.back.repositorios.PuestoRepository;
import aceleradora.socios.back.services.AutoridadService;
import aceleradora.socios.back.services.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autoridad")
public class AutoridadController {

    @Autowired
    DepartamentoService departamentoService;

    @Autowired
    AutoridadService autoridadService;

    @Autowired
    PuestoRepository puestoRepository;

    @PostMapping("/agregarAutoridad/{departamentoId}")
    public ResponseEntity<DepartamentoDTO> agregarAutoridad(
            @PathVariable Long departamentoId,
            @RequestBody List<AutoridadPostDTO> autoridadDTOS
    ) {
        DepartamentoDTO departamentoActualizado = autoridadService.agregarAutoridad(departamentoId,autoridadDTOS);
        return new ResponseEntity<>(departamentoActualizado, HttpStatus.OK);
    }


    @PutMapping("/editarAutoridades/{departamentoId}")
    public ResponseEntity<DepartamentoDTO> editarAutoridades(
            @PathVariable Long departamentoId,
            @RequestBody List<AutoridadPostDTO> autoridades
    ) {
        DepartamentoDTO departamentoActualizado = autoridadService.editarAutoridades(departamentoId, autoridades);
        return new ResponseEntity<>(departamentoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/borrarAutoridad/{autoridadId}")
    public ResponseEntity<AutoridadDTO> borrarAutoridad(
            @PathVariable Long autoridadId
    ){
        AutoridadDTO autoridadBorrada = autoridadService.borrarAutoridad(autoridadId);
        return new ResponseEntity<>(autoridadBorrada,HttpStatus.OK);
    }

    @GetMapping("/obtenerAutoridades/{departamentoId}")
    public ResponseEntity<List<AutoridadDTO>> obtenerAutoridades(@PathVariable Long departamentoId) {
        List<AutoridadDTO> autoridades = autoridadService.obtenerAutoridades(departamentoId);
        return new ResponseEntity<>(autoridades, HttpStatus.OK);
    }
    @Cacheable(value = "puestosCache")
    @GetMapping("/Puestos")
    public  ResponseEntity<List<String>> puestos(){
        List<Puesto> puestos = puestoRepository.findAll();

        List<String> nombresPuestos = puestos.stream()
                .map(Puesto::getNombre)
                .collect(Collectors.toList());

        return new ResponseEntity<>(nombresPuestos, HttpStatus.OK);
    }

}
