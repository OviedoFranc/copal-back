package aceleradora.socios.back.controller;


import aceleradora.socios.back.dto.*;
import aceleradora.socios.back.services.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/dpto")
public class DepartamentoController {

    @Autowired
    DepartamentoService departamentoService;

    @PostMapping("altaDepartamento")
    public ResponseEntity<DepartamentoDTO> guardarDepartamento(@Valid @RequestBody DepartamentoPostDTO departamentoDTO) {

        DepartamentoDTO nuevoDpto = departamentoService.altaDepartamento(departamentoDTO);
        return new ResponseEntity<>(nuevoDpto, HttpStatus.CREATED);
    }

    @PutMapping("bajaDepartamento/{id}")
    public ResponseEntity<Void> eliminarDepartamento(@PathVariable Long id) {
        DepartamentoDTO departamentoEliminado = departamentoService.bajaDepartamento(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("editarDepartamento/{id}")
    public ResponseEntity<DepartamentoDTO> editarDepartamento(@PathVariable Long id, @RequestBody DepartamentoPostDTO departamentoDTO){
        DepartamentoDTO departamentoActualizado = departamentoService.editarDepartamento(id ,departamentoDTO);
        return Optional.ofNullable(departamentoActualizado)
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Cacheable(value = "departamentosCache")
    @GetMapping("/departamentos")
    public ResponseEntity<List<DepartamentoDTO>> todosDepartamentos() {
        List<DepartamentoDTO> aux = departamentoService.traerTodos();
        return new ResponseEntity<>(aux, HttpStatus.OK);
    }

    @GetMapping("/departamento/{id}")
    public ResponseEntity<DepartamentoDTO> obtenerDepartamento(@PathVariable Long id) {
        DepartamentoDTO departamentoObtenido = departamentoService.obtenerDepartamento(id);
        return Optional.ofNullable(departamentoObtenido)
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/obtenerNombres")
    public ResponseEntity<List<String>> obtenerNombres() {
        List<String> nombres = departamentoService.obtenerNombres();
        return new ResponseEntity<>(nombres, HttpStatus.OK);
    }


    @GetMapping("/departamentosPaginados")
    public ResponseEntity<Page<DepartamentoDTO>> todosDepartamentosPaginados(
    @RequestParam(defaultValue = "1") int pagina,
    @RequestParam(defaultValue = "10") int tamanio) {
        List<DepartamentoDTO> aux = departamentoService.traerTodos();
        PageRequest pageRequest = PageRequest.of(pagina - 1, tamanio);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), aux.size());
        List<DepartamentoDTO> paginatedList = new ArrayList<>(aux.subList(start, end));


        Page<DepartamentoDTO> pages = new PageImpl<>(paginatedList, pageRequest, aux.size());

        return ResponseEntity.ok(pages);
    }
}
