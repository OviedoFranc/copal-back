package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.Usuario;
import aceleradora.socios.back.clases.departamento.Autoridad;
import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.dto.AutoridadDTO;
import aceleradora.socios.back.dto.AutoridadPostDTO;
import aceleradora.socios.back.dto.DepartamentoDTO;
import aceleradora.socios.back.repositorios.AutoridadRepository;
import aceleradora.socios.back.repositorios.DepartamentoRepository;
import aceleradora.socios.back.repositorios.PuestoRepository;
import aceleradora.socios.back.repositorios.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutoridadService {

    private final DepartamentoRepository departamentoRepository;

    private final AutoridadRepository autoridadRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PuestoRepository puestoRepository;

    public AutoridadService(DepartamentoRepository departamentoRepository, AutoridadRepository autoridadRepository, ModelMapper modelMapper, UserRepository userRepository, PuestoRepository puestoRepository) {
        this.departamentoRepository = departamentoRepository;
        this.autoridadRepository = autoridadRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.puestoRepository = puestoRepository;
    }
    public DepartamentoDTO agregarAutoridad(Long departamentoId, List<AutoridadPostDTO> autoridadDTOs) {


        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento con ID " + departamentoId));


        autoridadDTOs.forEach(autoridadDTO -> {
            Long id = autoridadDTO.getUsuarioId();
            Usuario usuario = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID " + id));

            Autoridad autoridad = new Autoridad();
            autoridad.setPuesto(puestoRepository.findByNombre(autoridadDTO.getPuesto().getNombre()).get());
            autoridad.setUsuario(usuario);

            departamento.getAutoridades().add(autoridad);
            autoridadRepository.save(autoridad);
            departamentoRepository.save(departamento);});

        return modelMapper.map(departamento, DepartamentoDTO.class);
    }

//    public DepartamentoDTO agregarAutoridades(Long departamentoId, List<AutoridadDTO> autoridades) {
//
//
//        Departamento departamento = departamentoRepository.findById(departamentoId)
//                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento con ID " + departamentoId));
//
//        List<Autoridad> nuevasAutoridades = new ArrayList<>();
//
//        for (AutoridadDTO autoridadDTO : autoridades) {
//            Usuario usuario = userRepository.findById(autoridadDTO.getUsuarioId())
//                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID " + autoridadDTO.getUsuarioId()));
//
//            Autoridad autoridad = new Autoridad();
//            autoridad.setPuesto(autoridadDTO.getPuesto());
//            autoridad.setUsuario(usuario);
//
//            nuevasAutoridades.add(autoridad);
//        }
//
//        departamento.getAutoridades().addAll(nuevasAutoridades);
//        autoridadRepository.saveAll(nuevasAutoridades);
//        departamentoRepository.save(departamento);
//
//        return modelMapper.map(departamento, DepartamentoDTO.class);
//
//    }

    public DepartamentoDTO editarAutoridades(Long departamentoId, List<AutoridadPostDTO> nuevasAutoridades) {
        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento con ID " + departamentoId));

        // Obtener la lista de autoridades existentes en el departamento
        List<Autoridad> autoridadesActuales = departamento.getAutoridades();

        // Crear un mapa de autoridades existentes para facilitar la búsqueda
        Map<Long, Autoridad> autoridadesExistentesMap = autoridadesActuales.stream()
                .collect(Collectors.toMap(autoridad -> autoridad.getUsuario().getId(), autoridad -> autoridad));

        // Actualizar las autoridades existentes con las nuevas autoridades o agregar nuevas autoridades
        for (AutoridadPostDTO nuevaAutoridadDTO : nuevasAutoridades) {
            Long usuarioId = nuevaAutoridadDTO.getUsuarioId();
            Autoridad autoridadExistente = autoridadesExistentesMap.get(usuarioId);

            if (autoridadExistente != null) {
                // Actualizar la autoridad existente con los datos de la nueva autoridad
                autoridadExistente.setPuesto(puestoRepository.findByNombre(nuevaAutoridadDTO.getPuesto().getNombre()).get());
            } else {
                // Crear una nueva autoridad si no existe
                Usuario usuario = userRepository.findById(usuarioId)
                        .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con ID " + usuarioId));

                Autoridad nuevaAutoridad = new Autoridad();
                nuevaAutoridad.setPuesto(puestoRepository.findByNombre(nuevaAutoridadDTO.getPuesto().getNombre()).get());
                nuevaAutoridad.setUsuario(usuario);

                autoridadesActuales.add(nuevaAutoridad);
            }
        }

        // Eliminar autoridades que no se encuentren en la lista de nuevas autoridades
        autoridadesActuales.removeIf(autoridad -> !nuevasAutoridades.stream()
                .anyMatch(nuevaAutoridadDTO -> nuevaAutoridadDTO.getUsuarioId().equals(autoridad.getUsuario().getId())));

        departamentoRepository.save(departamento);

        return modelMapper.map(departamento, DepartamentoDTO.class);
    }


    public AutoridadDTO borrarAutoridad(long id){
        Optional<Autoridad> autoridad = autoridadRepository.findById(id);
        if(autoridad.isPresent()){
            autoridadRepository.delete(autoridad.get());

            return modelMapper.map(autoridad,AutoridadDTO.class);
        }else throw new EntityNotFoundException("no se econtro un puesto de autoridad con id: " + id+ " para editar");}


    public List<AutoridadDTO> obtenerAutoridades(Long departamentoId) {
        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el departamento con ID " + departamentoId));

        List<Autoridad> autoridades = departamento.getAutoridades();
        List<AutoridadDTO> autoridadesDTO = convertAutoridadesToDTO(autoridades);
        return autoridadesDTO;
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


}
