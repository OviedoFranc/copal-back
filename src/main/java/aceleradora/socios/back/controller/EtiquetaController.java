package aceleradora.socios.back.controller;

import java.util.List;

import aceleradora.socios.back.dto.DepartamentoDTO;
import aceleradora.socios.back.dto.EtiquetaDTO;
import aceleradora.socios.back.dto.EventoDTO;
import aceleradora.socios.back.services.EtiquetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aceleradora.socios.back.clases.socio.Etiqueta;
import aceleradora.socios.back.repositorios.EtiquetaRepository;

@RestController
public class EtiquetaController {
	
	@Autowired
	EtiquetaRepository repoEtiqueta;

	@Autowired
	EtiquetaService etiquetaService;
	
	@GetMapping("/etiquetas")
	public List<Etiqueta> obtenerEtiquetas() {
		return repoEtiqueta.findAll();
	}

	@PostMapping("/agregarEtiqueta")
	public ResponseEntity<EtiquetaDTO> guardarEtiquetas(@Valid @RequestBody EtiquetaDTO etiqueta) {
		EtiquetaDTO etiquetaDTO = etiquetaService.guardarEtiqueta(etiqueta);
		return new ResponseEntity<>(etiquetaDTO, HttpStatus.CREATED);
	}

}
