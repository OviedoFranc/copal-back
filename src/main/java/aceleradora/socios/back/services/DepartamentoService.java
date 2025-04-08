package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.departamento.Autoridad;
import aceleradora.socios.back.dto.AutoridadDTO;
import aceleradora.socios.back.dto.DepartamentoDTO;
import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.dto.DepartamentoPostDTO;
import aceleradora.socios.back.repositorios.AutoridadRepository;
import aceleradora.socios.back.repositorios.DepartamentoRepository;
import aceleradora.socios.back.repositorios.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    private final AutoridadRepository autoridadRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository, AutoridadRepository autoridadRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.departamentoRepository = departamentoRepository;
        this.autoridadRepository = autoridadRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    public DepartamentoDTO altaDepartamento(DepartamentoPostDTO departamentoDTO){
        Optional<Departamento> departamentoOptional = departamentoRepository.findByNombre(departamentoDTO.getNombre());

        if (departamentoOptional.isPresent()) {
            throw new EntityExistsException("Ya existe el departamento");
        } else {
            Departamento departamento = modelMapper.map(departamentoDTO, Departamento.class);
            departamento.setEstaActivo(true);
            departamentoRepository.save(departamento);
            return modelMapper.map(departamento, DepartamentoDTO.class);
        }
    }


    public DepartamentoDTO bajaDepartamento(Long id){

        Optional<Departamento> existingSocioOpt = departamentoRepository.findById(id);
        if (existingSocioOpt.isPresent()) {
            Departamento existingDpto = existingSocioOpt.get();
            existingDpto.setEstaActivo(false);
            Departamento updatedDpto = departamentoRepository.save(existingDpto);
            return modelMapper.map(updatedDpto, DepartamentoDTO.class);
        } else {
            throw new EntityNotFoundException("No se encontro un departamento con id: " + id+ " para borrar");

        }
    }

    public DepartamentoDTO editarDepartamento(Long id, DepartamentoPostDTO departamentoDTO){
        Optional<Departamento> departamentoOptional = departamentoRepository.findById(id);

        if (departamentoOptional.isPresent()) {
            Departamento departamento = departamentoOptional.get();
            modelMapper.map(departamentoDTO,departamento);
            Departamento dptoactualizado = departamentoRepository.save(departamento);
            return modelMapper.map(dptoactualizado, DepartamentoDTO.class);
        } else {
            throw new EntityNotFoundException("No se encontró un departamento con id: " + id+ " para editar");
        }

    }



    public List<DepartamentoDTO> traerTodos() {
        List<DepartamentoDTO> departamentoDTOS = new ArrayList<>(); // Initialize the list

        List<Departamento> departamentoEntities = departamentoRepository.findAll();

        // Use a forEach loop or a stream with collect to map the entities to DTOs
        departamentoEntities.forEach(departamento -> {
            DepartamentoDTO departamentoDTO = modelMapper.map(departamento, DepartamentoDTO.class);
            departamentoDTO.setAutoridades(convertAutoridadesToDTO(departamento.getAutoridades()));
            departamentoDTOS.add(departamentoDTO);
        });

        return departamentoDTOS;
    }

    public DepartamentoDTO obtenerDepartamento(Long id) {
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        if(departamento.isPresent()){
            DepartamentoDTO departamentoDTO = modelMapper.map(departamento.get(), DepartamentoDTO.class);
            departamentoDTO.setAutoridades(convertAutoridadesToDTO(departamento.get().getAutoridades()));
            return departamentoDTO;
        }
        else{throw new EntityNotFoundException("No se encontró el departamento con ID: "+id);
        }
    }



    public List<AutoridadDTO> convertAutoridadesToDTO(List<Autoridad> autoridades) {
        return autoridades.stream()
                .map(this::convertToDTO)  // Utiliza tu método de conversión Autoridad a AutoridadDTO
                .collect(Collectors.toList());
    }
    public AutoridadDTO convertToDTO(Autoridad autoridad) {
        AutoridadDTO dto = new AutoridadDTO();
        dto.setPuesto(autoridad.getPuesto().getNombre());
        dto.setUsuarioId(autoridad.getUsuario().getId());
        dto.setNombre(autoridad.getUsuario().getNombre());
        return dto;
    }


    public List<String> obtenerNombres() {  //CAMBIO: SI LA LISTA ESTA VACIA AHORA TIRA EXCPECION ADVIRTIENDOLO, PERO NO DEVUELVE LA LISTA VACIA, QUIZA DEBERIA SIMPLEMENTE DEVOLVER LISTA VACIA
        List<Departamento> departamentos = departamentoRepository.findAll();
        if (departamentos.isEmpty()) {
            throw new EntityNotFoundException("No hay socios cargados");
        }else{
            return departamentos.stream().map(Departamento::getNombre).collect(Collectors.toList());
        }
    }
}



