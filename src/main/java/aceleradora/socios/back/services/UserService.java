package aceleradora.socios.back.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import aceleradora.socios.back.clases.Usuario;

import aceleradora.socios.back.dto.UsuarioDTO;
import aceleradora.socios.back.repositorios.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	ModelMapper modelMapper;

	public UserService(UserRepository userRepository, ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}

//Nuevo Sprint 2
	public UsuarioDTO darDeAltaUsuario(UsuarioDTO usuarioDTO) {
		// Realiza la conversión de UsuarioDTO a entidad Usuario y guarda en la base de datos
		Usuario usuario = modelMapper.map( usuarioDTO, Usuario.class);
		Usuario nuevoUsuario = userRepository.save(usuario);
		return usuarioDTO;
	}

	public void darDeBajaUsuario(Long id) {
		Optional<Usuario> usuarioOptional = userRepository.findById(id);
		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			usuario.setActivo(false);
			userRepository.save(usuario);
		} else {
			throw new EntityNotFoundException("No se encontró el usuario con ID " + id);
		}
	}


	public List<UsuarioDTO> traerTodos(){
		List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
		List<Usuario> usuarios = userRepository.findAll();

		usuarios.forEach(usuario->{
			UsuarioDTO usuarioDTO = modelMapper.map(usuario,UsuarioDTO.class);
			usuarioDTOS.add(usuarioDTO);
		});
		return usuarioDTOS;
	}

	public UsuarioDTO modificarUsuario(long id,UsuarioDTO usuarioDTO){
		Optional<Usuario> usuario = userRepository.findById(id);

		if(usuario.isPresent()){
			Usuario usuarioPresent = usuario.get();
			modelMapper.map(usuarioDTO,usuarioPresent);
			Usuario usuarioActualizado = userRepository.save(usuarioPresent);
			return modelMapper.map(usuarioActualizado,UsuarioDTO.class);
		}else throw new EntityNotFoundException("No se encontró un usuario con id: " + id + " para actualizar");
	}

}
