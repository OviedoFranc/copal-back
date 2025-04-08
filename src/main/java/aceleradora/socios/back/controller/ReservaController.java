package aceleradora.socios.back.controller;

import aceleradora.socios.back.clases.Usuario;

import aceleradora.socios.back.clases.espacio.Lugar;
import aceleradora.socios.back.clases.espacio.Reserva;
import aceleradora.socios.back.dto.*;

import aceleradora.socios.back.clases.espacio.EstadoReserva;
import aceleradora.socios.back.clases.espacio.Recurso;
import aceleradora.socios.back.dto.DepartamentoPostDTO;
import aceleradora.socios.back.dto.ReservaDTO;
import aceleradora.socios.back.dto.SocioDTO;
import aceleradora.socios.back.dto.UsuarioDTO;

import aceleradora.socios.back.services.ReservaService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reserva")
public class ReservaController {
    @Autowired
    ReservaService reservaService;


    @GetMapping("/obtenerReserva/{reservaId}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable("reservaId") Long id) {
        ReservaDTO reservaDTO = reservaService.obtenerPorId(id);
        return new ResponseEntity<>(reservaDTO, HttpStatus.OK);
    }

    @GetMapping("/obtenerReservaPorNombre/{nombreLugar}")
    public ResponseEntity<List<ReservaDTO>> obtenerReservaPorNombre(@PathVariable("nombreLugar") String nombre) {
        List<ReservaDTO> reservaDTOs = reservaService.obtenerPorNombre(nombre);
        return new ResponseEntity<>(reservaDTOs, HttpStatus.OK);
    }

    @PostMapping("/altaReserva")
    public ResponseEntity<ReservaDTO> altaReserva(@Valid @RequestBody ReservaDTO reservaDTO){

        ReservaDTO nuevaReserva = reservaService.guardarReserva(reservaDTO);
        return new ResponseEntity<>(nuevaReserva,HttpStatus.OK);
    }

    @DeleteMapping("/eliminarReserva/{reservaId}")
    public ResponseEntity<ReservaDTO> eliminarReserva(@PathVariable("reservaId") Long id){
        ReservaDTO reservaEliminada = reservaService.eliminarReserva(id);
        return new ResponseEntity<>(reservaEliminada,HttpStatus.OK);
    }

    @PutMapping("/editarReserva/{reservaId}")
    public ResponseEntity<ReservaDTO> editarReserva(@PathVariable("reservaId") Long id,@RequestBody ReservaDTO reservaDTO){
        ReservaDTO reservaBaja = reservaService.editarReserva(id,reservaDTO);
        return new ResponseEntity<>(reservaBaja,HttpStatus.OK);
    }
    @Cacheable(value = "reservarListadasCache")
    @GetMapping("/listar")
    public ResponseEntity<List<ReservaDTO>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasFiltradas(@RequestParam(name = "estado", required = false) List<String> estado){
        List<ReservaDTO> reservasFiltradas = reservaService.busquedaFiltrada(estado);
        return new ResponseEntity<>(reservasFiltradas, HttpStatus.OK);
    }

    @GetMapping("/listarEstados")
    public ResponseEntity<List<EstadoReserva>> listarEstados() {
        return ResponseEntity.ok(reservaService.listarEstados());
    }

    @GetMapping("/listarRecursos")
    public ResponseEntity<List<Recurso>> listarRecursos() {
        return ResponseEntity.ok(reservaService.listarRecursos());
    }

    @GetMapping("/listarLugares")
    public ResponseEntity<List<Lugar>> listarLugares(){
        return ResponseEntity.ok(reservaService.listarLugares());
    }


    @GetMapping("/obtenerReservaPaginado")
    public ResponseEntity<Page<ReservaDTO>> obtenerReservaPaginada(
            @RequestParam(name = "estado", required = false) List<String> estado,
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "10") int tamanio){
        List<ReservaDTO> reservasFiltradas = reservaService.busquedaFiltrada(estado);

        PageRequest pageRequest = PageRequest.of(pagina - 1, tamanio);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), reservasFiltradas.size());
        List<ReservaDTO> paginatedList = new ArrayList<>(reservasFiltradas.subList(start, end));

        
        Page<ReservaDTO> pages = new PageImpl<>(paginatedList, pageRequest, reservasFiltradas.size());

        return ResponseEntity.ok(pages);
    }
}
