package aceleradora.socios.back.controller;

import aceleradora.socios.back.clases.socio.Socio;
import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.dto.ResumenSocioDTO;
import aceleradora.socios.back.dto.SocioDTO;
import aceleradora.socios.back.services.SocioDTOService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/socio")
public class SocioController {
    @Autowired
    SocioDTOService socioDTOService;

    @Autowired
    ModelMapper modelMapper;
    //TODO: LOS CROSSORIGIN RAROS
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(path = "/socios")
    public List<SocioDTO> getList(){
        return this.socioDTOService.obtenerListaDeSocios();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/alta")
    public SocioDTO guardarSocio(@RequestBody SocioDTO socio) {
        return this.socioDTOService.guardarSocio(socio);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/obtenerSocio/{socioId}")
    public Optional<SocioDTO> obtenerSocioPorId(@PathVariable("socioId") Long id) {
        return this.socioDTOService.obtenerPorId(id);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/editarSocio/{socioId}")
    public SocioDTO editarSocio(@PathVariable Long socioId, @RequestBody SocioDTO socioDTO){
        return this.socioDTOService.editarSocio(socioId, socioDTO);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/bajaSocio/{socioId}")
    public SocioDTO eliminarSocio(@PathVariable Long socioId){
        return this.socioDTOService.eliminarSocio(socioId);
    }

    @GetMapping("/obtenerSocioPorNombre/{nombre}")
    public ResponseEntity<SocioDTO> obtenerSocio(@PathVariable String nombre) {
        SocioDTO socio = socioDTOService.obtenerSocio(nombre);
        return Optional.ofNullable(socio)
                .map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/busquedaPaginada")
    public ResponseEntity<Page<SocioDTO>> obtenerResumenSocios(
            @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(defaultValue = "10") int tamanio,
            @RequestParam(name = "etiqueta", required = false) Optional<List<String>> etiquetas,
            @RequestParam(name = "aniosActivos", required = false) Optional<Integer> aniosActivos,
            @RequestParam(name = "categoria", required = false) Optional<Categoria> tipoSocio,
            @RequestParam(name = "nombre", required = false) Optional<String> nombre,
            @RequestParam(name = "activo", required = false) Optional<Boolean> activo){

        Page<SocioDTO> pages = socioDTOService.obtenerResumenSociosPaginados(pagina, tamanio, etiquetas, aniosActivos, tipoSocio, nombre,activo);
        return ResponseEntity.ok(pages);
    }

    @GetMapping("/obtenerNombres")
    public ResponseEntity<List<String>> obtenerNombres() {
        List<String> nombres = socioDTOService.obtenerNombres();
        return new ResponseEntity<>(nombres, HttpStatus.OK);
    }

    @GetMapping("/{id}/etiquetas")
    public ResponseEntity<List<String>> obtenerEtiquetasDeSocio(@PathVariable Long id) {
        try {
            List<String> etiquetas = socioDTOService.obtenerEtiquetasDeSocio(id);
            return new ResponseEntity<>(etiquetas, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio no encontrado", e);
        }
    }

    @DeleteMapping("/{id}/etiquetas")
    public ResponseEntity<Void> eliminarEtiquetaDeSocio(@PathVariable Long id, @RequestParam(name = "categoria") String etiquetas) {
        try {
            socioDTOService.eliminarEtiquetaDeSocio(id, etiquetas);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio no encontrado", e);
        }
    }

    @PutMapping("/{id}/etiquetas")
    public ResponseEntity<Void> actualizarCategoriasDeSocio(@PathVariable Long id, @RequestBody List<String> etiquetas) {

        try {
            socioDTOService.actualizarEtiquetasDeSocio(id, etiquetas);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio no encontrado", e);
        }
    }

    @GetMapping("/activo")
    public ResponseEntity<Boolean> estaActivo(@RequestParam Long id) {
        Optional<SocioDTO> socio = socioDTOService.obtenerPorId(id);

        Socio sociomappeado =  modelMapper.map(socio,Socio.class);
        return new ResponseEntity<>(sociomappeado.isActivo(), HttpStatus.OK);
    }
}
