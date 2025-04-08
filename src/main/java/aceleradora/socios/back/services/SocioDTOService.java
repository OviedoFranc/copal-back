package aceleradora.socios.back.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import aceleradora.socios.back.dto.ResumenSocioDTO;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.clases.socio.Etiqueta;
import aceleradora.socios.back.clases.socio.Socio;
import aceleradora.socios.back.dto.SocioDTO;
import aceleradora.socios.back.clases.Usuario;
import aceleradora.socios.back.repositorios.CategoriaRepository;
import aceleradora.socios.back.repositorios.EtiquetaRepository;
import aceleradora.socios.back.repositorios.SocioRepository;
import aceleradora.socios.back.repositorios.UserRepository;

@Service
public class SocioDTOService {

	ModelMapper modelMapper;
	@Autowired
	EtiquetaService etiquetaService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SocioRepository socioRepository;
	
	@Autowired
	CategoriaRepository repoCategoria;
	
	@Autowired
	EtiquetaRepository repoEtiqueta;

    public SocioDTOService (ModelMapper modelMapper) {
        super();
    	this.modelMapper = modelMapper;
    }


	public SocioDTO guardarSocio(SocioDTO socioDto) {

		Socio socio = modelMapper.map(socioDto,Socio.class);
		socioRepository.save(socio);
		return modelMapper.map(socio,SocioDTO.class);
	}

	public Optional<SocioDTO> obtenerPorId(Long id) {
		
		//Optional<Usuario> usuario = userRepository.findById(id);
		Optional<Socio> socio = socioRepository.findById(id);
		
		if (socio.isPresent()) {
			SocioDTO nuevoSocioDto = modelMapper.map(socio,SocioDTO.class);
			
			/*nuevoSocioDto.setId(usuario.get().getId());
			nuevoSocioDto.setNombre(usuario.get().getNombre());
			nuevoSocioDto.setDescripcion(usuario.get().getDescripcion());
			nuevoSocioDto.setCuit(socio.get().getCuit());
			nuevoSocioDto.setContrasenia(usuario.get().getContrasenia());
			nuevoSocioDto.setMail(usuario.get().getMail());
			nuevoSocioDto.setFechaUnion(socio.get().getFechaUnion());
			nuevoSocioDto.setTelefono(usuario.get().getTelefono());
			nuevoSocioDto.setCategoria(socio.get().getCategoria().getId().intValue());
			nuevoSocioDto.setAdmin(usuario.get().getAdmin());
			nuevoSocioDto.setEstado(socio.get().getEstado());
			nuevoSocioDto.setImagen(socio.get().getImagen());
			nuevoSocioDto.setWeb(socio.get().getWeb());
			nuevoSocioDto.setFechaCreacion(usuario.get().getFechaCreacion());
			Collection<Etiqueta> etiquetas = socio.get().getEtiquetas();
			List<Integer> listaEtiquetas = new ArrayList<>();
			for (Etiqueta etiqueta: etiquetas) {
				listaEtiquetas.add(etiqueta.getId().intValue());
			}
			nuevoSocioDto.setEtiquetas(listaEtiquetas);*/

			
			return Optional.of(nuevoSocioDto);
		} else {
			return Optional.empty();
		}
	}

	public SocioDTO editarSocio(Long id,SocioDTO socioDto){
		//Optional<Usuario> usuarioOpcional = userRepository.findById(id);
		Optional<Socio> socioOpcional = socioRepository.findById(id);



		if(socioOpcional.isPresent()){
			Socio socio = socioOpcional.get();
			socio = modelMapper.map(socioDto,Socio.class);

			socioRepository.save(socio);


			return modelMapper.map(socio,SocioDTO.class);
		}
		else{
			throw new EntityNotFoundException("No se encontró socio de id : " + id);
		}
	}

	public SocioDTO eliminarSocio(Long id) {
		Optional<Socio> socioOpcional = socioRepository.findById(id);

		if(socioOpcional.isPresent()){
			Socio socioExistente = socioOpcional.get();

			socioExistente.setEstado(false);
			socioRepository.save(socioExistente);

            return null;
		}
		else{
			return null;
		}
	}

	public List<SocioDTO> obtenerListaDeSocios() {
		List<SocioDTO> sociosDto = new ArrayList<>();
		List<Socio> socios = socioRepository.findAll();
		
		for (Socio socio : socios) {
			if(socioRepository.findById(socio.getId()).isPresent()){
			SocioDTO nuevoSocioDto = modelMapper.map(socio,SocioDTO.class);

			//Optional<Usuario> usuario = userRepository.findById(socio.getId());
			
			/*if (usuario.isPresent()) {
				nuevoSocioDto.setId(socio.getId());
				nuevoSocioDto.setNombre(usuario.get().getNombre());
				nuevoSocioDto.setDescripcion(usuario.get().getDescripcion());
				nuevoSocioDto.setCuit(socio.getCuit());
				nuevoSocioDto.setContrasenia(usuario.get().getContrasenia());
				nuevoSocioDto.setMail(usuario.get().getMail());
				nuevoSocioDto.setFechaUnion(socio.getFechaUnion());
				nuevoSocioDto.setTelefono(usuario.get().getTelefono());
				nuevoSocioDto.setCategoria(socio.getCategoria().getId().intValue());
				nuevoSocioDto.setAdmin(usuario.get().getAdmin());
				nuevoSocioDto.setEstado(socio.getEstado());
				nuevoSocioDto.setImagen(socio.getImagen());
				nuevoSocioDto.setWeb(socio.getWeb());
				nuevoSocioDto.setFechaCreacion(usuario.get().getFechaCreacion());
				Collection<Etiqueta> etiquetas = socio.getEtiquetas();
				List<Integer> listaEtiquetas = new ArrayList<>();
				for (Etiqueta etiqueta: etiquetas) {
					listaEtiquetas.add(etiqueta.getId().intValue());
				}
				nuevoSocioDto.setEtiquetas(listaEtiquetas);*/
				
				sociosDto.add(nuevoSocioDto);
				
			}	
		}
		
		return sociosDto;
	}

	public SocioDTO obtenerSocio(String nombre) {
		Optional<Socio> socioOptional = socioRepository.findByNombre(nombre);

		if (socioOptional.isPresent()) {
			Socio socio = socioOptional.get();
			SocioDTO dto = modelMapper.map(socio, SocioDTO.class);
			return dto;
		} else {
			throw new EntityNotFoundException("Socio no encontrado con NOMBRE: " + nombre);
		}
	}

	public Page<SocioDTO> obtenerResumenSociosPaginados(int pagina, int tamanio,
															   Optional<List<String>> categoriaOptional,
															   Optional<Integer> aniosActivosOptional,
															   Optional<Categoria> tipoSocioOptional,
															   Optional<String> nombreOptional,
															   Optional<Boolean> activoOptional) {
		pagina = pagina-1;

		LocalDate fechaActual = LocalDate.now();
		Pageable pageable = PageRequest.of(pagina, tamanio);

		Optional<LocalDate> fechaInicioMembresiaOptional = aniosActivosOptional.map(anios -> fechaActual.minusYears(anios));

		Pair<List<Socio>, Long> result = filtrarYContarSocios(
				pageable,
				categoriaOptional,
				fechaInicioMembresiaOptional,
				tipoSocioOptional,
				nombreOptional,
				activoOptional);

		List<Socio> sociosFiltrados = result.getKey();
		Long totalSocios = result.getValue();

		if (sociosFiltrados.isEmpty()) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0L);
		}

		List<SocioDTO> resumenSocios = sociosFiltrados.stream()
				.map(socio -> convertirResumenSocioDTO(socio, fechaActual))
				.collect(Collectors.toList());

		return new PageImpl<>(resumenSocios, pageable, totalSocios);
	}

	private Pair<List<Socio>, Long> filtrarYContarSocios(Pageable pageable,
														 Optional<List<String>> etiquetaOptional,
														 Optional<LocalDate> fechaInicioMembresia,
														 Optional<Categoria> categoriaOptional,
														 Optional<String> nombreOptional,
														 Optional<Boolean> activoOptional) {

		List<Socio> sociosFiltrados;

		Categoria categoria = categoriaOptional.orElse(null);
		List<Etiqueta> etiquetas = null;
		if(etiquetaOptional.isPresent()) {
			etiquetas = etiquetaService.obtenerEtiquetasPorNombres(etiquetaOptional.get());
		}
		String nombre = nombreOptional.orElse(null);
		Boolean activo = activoOptional.orElse(null);
		LocalDate fechaInicio = fechaInicioMembresia.orElse(null);

		if (etiquetas != null && fechaInicio != null && categoria != null) {
			sociosFiltrados = socioRepository.findByCategoriaAndEtiquetasInAndMembresia_FechaPagoBefore(categoria, etiquetas, fechaInicio);
		} else if (etiquetas != null && categoria != null) {
			sociosFiltrados = socioRepository.findByCategoriaAndEtiquetasIn(categoria, etiquetas);
		} else if (etiquetas != null && fechaInicio != null) {
			sociosFiltrados = socioRepository.findByEtiquetasInAndMembresia_FechaPagoBefore(etiquetas, fechaInicio);
		} else if (categoria != null && fechaInicio != null) {
			sociosFiltrados = socioRepository.findByCategoriaAndMembresia_FechaPagoBefore(categoria, fechaInicio);
		} else if (categoria != null && nombre !=null) {
			sociosFiltrados = socioRepository.findByCategoriaAndNombreContaining(categoria, nombre);
		} else if (categoria != null && activo != null) {
			sociosFiltrados = socioRepository.findByCategoriaAndEstado(categoria, activo);
		} else if (etiquetas != null && activo != null) {
			sociosFiltrados = socioRepository.findByEtiquetasInAndEstado(etiquetas, activo);
		} else if (categoria != null) {
			sociosFiltrados = socioRepository.findByCategoria(categoria);
		} else if (etiquetas != null) {
			sociosFiltrados = socioRepository.findByEtiquetasIn(etiquetas);
		} else if (fechaInicio != null) {
			sociosFiltrados = socioRepository.findByMembresia_FechaPagoBefore(fechaInicio);
		} else if (nombre != null) {
			sociosFiltrados = socioRepository.findByNombreContaining(nombre);
		} else if (activo != null) {
			sociosFiltrados = socioRepository.findByEstado(activo);
		} else {
			sociosFiltrados = socioRepository.findAll();
		}

		Long totalSocios = (long) sociosFiltrados.size();
		sociosFiltrados = sociosFiltrados.stream()
				.skip(pageable.getOffset())
				.limit(pageable.getPageSize())
				.collect(Collectors.toList());

		return Pair.of(sociosFiltrados, totalSocios);
	}

	private SocioDTO convertirResumenSocioDTO(Socio socio, LocalDate fechaActual) {
		SocioDTO resumenSocioDTO = modelMapper.map(socio, SocioDTO.class);
		/*if (socio.isActivo() && socio.getMembresia() != null) {

			Period periodo = Period.between(socio.getMembresia().getFechaPago(), fechaActual);
			resumenSocioDTO.setAniosDeAntiguedad(periodo.getYears());
			// TODO: Considerar otros casos aquí, como cuando el socio tiene un nombre de presidente
		} else {
			// Establezco valores predeterminados en casos limites, ejemplo este que el socio no tiene membresia
			resumenSocioDTO.setAniosDeAntiguedad(0);
		}*/
		return resumenSocioDTO;
	}

	public SocioDTO agregarEtiquetasASocio(Long idSocio, List<String> nombresCategorias) {
		Socio socio = socioRepository.findById(idSocio).orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));

		List<Etiqueta> etiquetas = etiquetaService.obtenerEtiquetasPorNombres(nombresCategorias);
		socio.getEtiquetas().addAll(etiquetas); // TODO: Agregarle la funcion a socio para que no rompa el paradigma de objetos

		Socio socioGuardado = socioRepository.save(socio);

		// Reutiliza el mapeo existente para convertir Socio a SocioDTO
		SocioDTO socioDTO = modelMapper.map(socioGuardado, SocioDTO.class);

		return socioDTO;
	}

	public List<String> obtenerEtiquetasDeSocio(Long idSocio) {
		Socio socio = socioRepository.findById(idSocio).orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));

		List<String> etiquetas = socio.getEtiquetas().stream()
				.map(Etiqueta::getNombre)
				.collect(Collectors.toList());

		return etiquetas;
	}

	public Void eliminarEtiquetaDeSocio(Long idSocio, String nombreEtiqueta) {
		Socio socio = socioRepository.findById(idSocio).orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));
		Optional<Etiqueta> etiqueta = etiquetaService.obtenerEtiquetaPorNombre(nombreEtiqueta);
		socio.getEtiquetas().remove(etiqueta);
		socioRepository.save(socio);
		return null;
	}

	public Void actualizarEtiquetasDeSocio(Long idSocio, List<String> nombreEtiquetas) {
		Socio socio = socioRepository.findById(idSocio).orElseThrow(() -> new EntityNotFoundException("Socio no encontrado"));
		List<Etiqueta> etiqueta = etiquetaService.obtenerEtiquetasPorNombres(nombreEtiquetas);
		socio.setEtiquetas(etiqueta);
		socioRepository.save(socio);
		return null;
	}

	public List<String> obtenerNombres() {
		List<Socio> socios = socioRepository.findAll();
		if (socios.isEmpty()) {
			throw new EntityNotFoundException("No hay socios cargados");
		}else{
			return socios.stream().map(Socio::getNombre).collect(Collectors.toList());
		}
	}

}