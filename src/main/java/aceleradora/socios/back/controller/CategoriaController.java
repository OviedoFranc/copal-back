package aceleradora.socios.back.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.dto.CategoriaDTO;
import aceleradora.socios.back.repositorios.CategoriaRepository;

@RestController
public class CategoriaController {
	
	@Autowired
	CategoriaRepository repoCategoria;
	
	@GetMapping("/categorias")
	public List<CategoriaDTO> obtenerCategorias() {
		List<Categoria> categorias = repoCategoria.findAll();
        List<CategoriaDTO> categoriaDTOs = categorias.stream()
            .map(categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre()))
            .collect(Collectors.toList());
        return categoriaDTOs;
	}
}
