package aceleradora.socios.back.controller;

import aceleradora.socios.back.clases.evento.EstadoEvento;
import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.dto.EventoDTO;
import aceleradora.socios.back.dto.EventoPostDTO;
import aceleradora.socios.back.dto.ParticipanteDTO;
import aceleradora.socios.back.dto.SocioDTO;
import aceleradora.socios.back.services.EventoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    EventoService eventoService;

    @PostMapping("alta")
    public ResponseEntity<EventoDTO> guardarEvento(@Valid @RequestBody EventoPostDTO eventoDTO) {
        EventoDTO nuevoEvento = eventoService.altaEvento(eventoDTO);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    @PutMapping("modificarEstado/{id}")
    public ResponseEntity<Void> modificarEstadoEvento(@PathVariable Long id, @RequestParam String estado) {
        EventoDTO eventoEliminado = eventoService.modificarEstadoEvento(id, estado);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("cancelar/{id}")
    public ResponseEntity<Void> cancelarEvento(@PathVariable Long id) {
        eventoService.modificarEstadoEvento(id, "Cancelado");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @PutMapping("editar/{id}")
    public ResponseEntity<EventoDTO> editarEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO){
        EventoDTO eventoActualizado = eventoService.editarEvento(id ,eventoDTO);
        return Optional.ofNullable(eventoActualizado)
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<EventoDTO>> todosEventos() {
        List<EventoDTO> aux = eventoService.traerTodos();
        return new ResponseEntity<>(aux, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> obtenerEvento(@PathVariable Long id) {
        EventoDTO eventoObtenido = eventoService.obtenerEvento(id);
        return Optional.ofNullable(eventoObtenido)
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("estadosEvento")
    public ResponseEntity<List<String>> obtenerEstadosEvento() {
        List<String> estadosEvento = eventoService.obtenerEstadosEvento();
        return new ResponseEntity<>(estadosEvento, HttpStatus.OK);
    }

    @GetMapping("modalidadesEvento")
    public ResponseEntity<List<String>> obtenerModalidadesEvento() {
        List<String> modalidades = eventoService.obtenerModalidadesEvento();
        return new ResponseEntity<>(modalidades, HttpStatus.OK);
    }


    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("filtro")
    public ResponseEntity<List<EventoDTO>> obtenerEventosFiltrados(
    @RequestParam(name = "estado", required = false) List<String> estado,
    @RequestParam(name = "modalidad", required = false) List<String> modalidad,
    @RequestParam(name = "departamento", required = false) List<String> departamento) {

        List<EventoDTO> eventosfiltrados1 = new ArrayList<>();
        List<EventoDTO> eventosfiltrados2 = new ArrayList<>();
        List<EventoDTO> eventosfiltrados3 = new ArrayList<>();
        if (estado != null) {
          eventosfiltrados1 = eventoService.busquedaFiltrada(estado);
          if(modalidad == null && departamento == null){return new ResponseEntity<>(eventosfiltrados1, HttpStatus.OK);}
        }
        if(modalidad != null){
         eventosfiltrados2 = eventoService.busquedaFiltrada(modalidad);
            if(estado == null && departamento == null){return new ResponseEntity<>(eventosfiltrados2, HttpStatus.OK);}
        }
        if(departamento != null){
        eventosfiltrados3 = eventoService.busquedaFiltrada(departamento);
            if(modalidad == null && estado == null){return new ResponseEntity<>(eventosfiltrados3, HttpStatus.OK);}
        }
        List<EventoDTO> eventos = new ArrayList<>();
        eventos.addAll(eventosfiltrados1);
        eventos.addAll(eventosfiltrados2);
        eventos.addAll(eventosfiltrados3);

        List<EventoDTO> eventosSinRepetir = eventoService.obtenerUnicoDuplicado(eventos);
        return new ResponseEntity<>(eventosSinRepetir, HttpStatus.OK);
    }

    @GetMapping("obtenerEventoPorCodigo/{codigo}")
    public ResponseEntity<EventoDTO> obtenerEventoPorCodigo(@PathVariable String codigo) {
        EventoDTO eventoObtenido = eventoService.obtenerEventoPorCodigo(codigo);
        return Optional.ofNullable(eventoObtenido)
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("obtenerCodigoPorEvento/{id}")
    public ResponseEntity<String> obtenerCodigoPorEvento(@PathVariable Long id) {
        String code = eventoService.obtenerCodigoPorEvento(id);
        return Optional.ofNullable(code)
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("obtenerEventoPaginado")
    public ResponseEntity<Page<EventoDTO>> obtenerEventoPaginado(
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "10") int tamanio){

        Page<EventoDTO> pages = eventoService.obtenerResumenEventosPaginados(pagina, tamanio);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("plataformas")
    public ResponseEntity<List<String>> obtenerPlataformas() {
        List<String> plataformas = eventoService.obtenerPlataformas();
        return new ResponseEntity<>(plataformas, HttpStatus.OK);
    }


    @GetMapping("/obtenerEventosFiltradosYPaginado")
    public ResponseEntity<Page<EventoDTO>> obtenerReservaPaginada(
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(name = "estado", required = false) List<String> estado,
            @RequestParam(name = "modalidad", required = false) List<String> modalidad,
            @RequestParam(name = "departamento", required = false) List<String> departamento) {

        List<EventoDTO> eventosfiltrados1 = new ArrayList<>();
        List<EventoDTO> eventosfiltrados2 = new ArrayList<>();
        List<EventoDTO> eventosfiltrados3 = new ArrayList<>();
        if (estado != null) {
            eventosfiltrados1 = eventoService.busquedaFiltrada(estado);
            if(modalidad == null && departamento == null){PageRequest pageRequest = PageRequest.of(pagina - 1, tamanio);
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), eventosfiltrados1.size());
                List<EventoDTO> paginatedList = new ArrayList<>(eventosfiltrados1.subList(start, end));


                Page<EventoDTO> pages = new PageImpl<>(paginatedList, pageRequest, eventosfiltrados1.size());

                return ResponseEntity.ok(pages);}
        }
        if(modalidad != null){
            eventosfiltrados2 = eventoService.busquedaFiltrada(modalidad);
            if(estado == null && departamento == null){PageRequest pageRequest = PageRequest.of(pagina - 1, tamanio);
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), eventosfiltrados2.size());
                List<EventoDTO> paginatedList = new ArrayList<>(eventosfiltrados2.subList(start, end));


                Page<EventoDTO> pages = new PageImpl<>(paginatedList, pageRequest, eventosfiltrados2.size());

                return ResponseEntity.ok(pages);}}

        if(departamento != null){
            eventosfiltrados3 = eventoService.busquedaFiltrada(departamento);
           if(modalidad == null && estado == null){PageRequest pageRequest = PageRequest.of(pagina - 1, tamanio);
               int start = (int) pageRequest.getOffset();
               int end = Math.min((start + pageRequest.getPageSize()), eventosfiltrados3.size());
               List<EventoDTO> paginatedList = new ArrayList<>(eventosfiltrados3.subList(start, end));


               Page<EventoDTO> pages = new PageImpl<>(paginatedList, pageRequest, eventosfiltrados3.size());

               return ResponseEntity.ok(pages);}
        }
        List<EventoDTO> eventos = new ArrayList<>();
        eventos.addAll(eventosfiltrados1);
        eventos.addAll(eventosfiltrados2);
        eventos.addAll(eventosfiltrados3);

        List<EventoDTO> eventosSinRepetir = eventoService.obtenerUnicoDuplicado(eventos);

        PageRequest pageRequest = PageRequest.of(pagina - 1, tamanio);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), eventosSinRepetir.size());
        List<EventoDTO> paginatedList = new ArrayList<>(eventosSinRepetir.subList(start, end));


        Page<EventoDTO> pages = new PageImpl<>(paginatedList, pageRequest, eventosSinRepetir.size());

        return ResponseEntity.ok(pages);
    }
}


