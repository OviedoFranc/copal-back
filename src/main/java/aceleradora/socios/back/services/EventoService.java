package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.departamento.Departamento;

import aceleradora.socios.back.clases.evento.EstadoEvento;
import aceleradora.socios.back.clases.evento.Evento;
import aceleradora.socios.back.clases.evento.Modalidad;
import aceleradora.socios.back.clases.evento.Participante;

import aceleradora.socios.back.clases.evento.*;

import aceleradora.socios.back.clases.ubicacion.Ubicacion;
import aceleradora.socios.back.dto.*;
import aceleradora.socios.back.repositorios.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;

    private final ParticipanteRepository participanteRepository;
    private final DepartamentoRepository departamentoRepository;

    private final EstadoEventoRepository estadoEventoRepository;

    private final UbicacionRepository ubicacionRepository;
    private final ModelMapper modelMapper;

    private final PlataformaRepository plataformaRepository;

    public EventoService(EventoRepository eventoRepository, ParticipanteRepository participanteRepository, ModelMapper modelMapper, DepartamentoRepository departamentoRepository,
                         EstadoEventoRepository estadoEventoRepository, UbicacionRepository ubicacionRepository, PlataformaRepository plataformaRepository) {
        this.eventoRepository= eventoRepository;
        this.participanteRepository = participanteRepository;
        this.modelMapper = modelMapper;
        this.departamentoRepository = departamentoRepository;
        this.estadoEventoRepository = estadoEventoRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.plataformaRepository = plataformaRepository;
    }

    public EventoDTO altaEvento(EventoPostDTO eventoDTO){
        Optional<Evento> eventoOptional = eventoRepository.findByNombre(eventoDTO.getNombre());

        if (eventoOptional.isPresent()) {
            throw new EntityExistsException("Ya existe un evento con ese nombre");
        } else {
            Evento evento = mapEventoPostToEvento(eventoDTO);

//            ubicacionRepository.save(evento.getLugar());
            eventoRepository.save(evento);

            //Generar codigo unico
            evento = eventoRepository.findByNombre(eventoDTO.getNombre()).get();
            GeneradorCodigoUnico aux = new GeneradorCodigoUnico();
            String codigo = aux.generarCodigoUnico(evento.getId());
            evento.setCodigoUUID(codigo);
            eventoRepository.save(evento);

            return modelMapper.map(evento, EventoDTO.class);
        }
    }


    public EventoDTO modificarEstadoEvento(Long id, String estado) {

        Optional<Evento> aux = eventoRepository.findById(id);
        if (aux.isPresent()) {
            Evento existingEvento = aux.get();

            Optional<EstadoEvento> estadoEvento = estadoEventoRepository.findByDescripcion(estado);
            if (estadoEvento.isEmpty()) {
                throw new IllegalArgumentException("El estado proporcionado no existe: " + estado);
            }

            existingEvento.setEstado(estadoEvento.get());

            Evento updatedEvento = eventoRepository.save(existingEvento);
            return modelMapper.map(updatedEvento, EventoDTO.class);
        } else {
            throw new EntityNotFoundException("No se encontró un departamento con id: " + id);
        }
    }

    public void eliminarEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró el evento con ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    public EventoDTO editarEvento(Long id, EventoDTO eventoDTO){
        Optional<Evento> EventoOptional = eventoRepository.findById(id);

        if (EventoOptional.isPresent()) {
            Evento evento = mapEventoDTOToEvento(eventoDTO);
            evento.setId(id);
            evento.setParticipantes(EventoOptional.get().getParticipantes());

            Evento eventoActualizado = eventoRepository.save(evento);
            return modelMapper.map(eventoActualizado, EventoDTO.class);
        } else {
            throw new EntityNotFoundException("No se encontró un departamento con id: " + id+ " para editar");
        }

    }



    public List<EventoDTO> traerTodos() {
        List<EventoDTO> eventoDTOS = new ArrayList<>(); // Initialize the list

        List<Evento> eventos = eventoRepository.findAll();

        // Use a forEach loop or a stream with collect to map the entities to DTOs
        eventos.forEach(evento -> {
            EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
            eventoDTO.setParticipantes(convertParticipantesToDTO(evento.getParticipantes()));
            eventoDTOS.add(eventoDTO);
        });

        return eventoDTOS;
    }

    public EventoDTO obtenerEvento(Long id) {
        Optional<Evento> eventoOptional = eventoRepository.findById(id);

        if(eventoOptional.isPresent()){
            EventoDTO eventoDTO = modelMapper.map(eventoOptional.get(), EventoDTO.class);
            eventoDTO.setParticipantes(convertParticipantesToDTO(eventoOptional.get().getParticipantes()));
            return eventoDTO;
        }
        else{throw new EntityNotFoundException("No se encontró el departamento con ID: "+id);
        }
    }


    public List<String> obtenerEstadosEvento(){
        List<String> estadosEvento = new ArrayList<>();
        List<EstadoEvento> estados = estadoEventoRepository.findAll();
        estados.forEach(estadoEvento -> {
            estadosEvento.add(estadoEvento.getDescripcion());
        });
        return estadosEvento;

    }

    public List<String> obtenerModalidadesEvento(){
        List<String> modalidades = new ArrayList<>();
        for (Modalidad modalidad : Modalidad.values()) {
            modalidades.add(modalidad.toString());
        }
        return modalidades;
    }

    public EventoDTO obtenerEventoPorCodigo(String cod){
        Optional<Evento> eventoOptional = eventoRepository.findByCodigoUUID(cod);

        if(eventoOptional.isPresent()){
            EventoDTO eventoDTO = modelMapper.map(eventoOptional.get(), EventoDTO.class);
            eventoDTO.setParticipantes(convertParticipantesToDTO(eventoOptional.get().getParticipantes()));
            return eventoDTO;
        }
        else{throw new EntityNotFoundException("No se encontró el departamento con ID: "+cod);
        }
    }

    public String obtenerCodigoPorEvento(Long id){
        Optional<Evento> eventoOptional = eventoRepository.findById(id);

        if(eventoOptional.isPresent()){
            Evento evento = eventoOptional.get();
            return evento.getCodigoUUID();
        }
        else{throw new EntityNotFoundException("No se encontró el departamento con ID: "+id);
        }
    }

    public Page<EventoDTO> obtenerResumenEventosPaginados(int pagina, int tamanio) {
        pagina = pagina-1;

        Pageable pageable = PageRequest.of(pagina, tamanio);

//        Pair<List<Evento>, Long> result = Pair.of(eventoRepository.findAll(), eventoRepository.count());
//
//        List<Evento> eventos = result.getKey();
//        Long totalEventos = result.getValue();

        List<Evento> eventos =eventoRepository.findAll();
        Long totalEventos =  eventoRepository.count();

        if (eventos.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0L);
        }

        List<EventoDTO> resumen = eventos.stream()
                .map(this::mapEventoToEventoDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(resumen, pageable, totalEventos);
    }


    public List<ParticipanteDTO> convertParticipantesToDTO(List<Participante> participantes){

        List<ParticipanteDTO> participanteDTOS = new ArrayList<>(); // Initialize the list
        participantes.forEach(participante -> {
            ParticipanteDTO participanteDTO = modelMapper.map(participante, ParticipanteDTO.class);
            participanteDTOS.add(participanteDTO);
        });

        return participanteDTOS;


    }


    public Evento mapEventoPostToEvento(EventoPostDTO eventoDTO){
        Evento evento = new Evento();

        // Mapeo directo de atributos
        evento.setNombre(eventoDTO.getNombre());

        Optional<Departamento> aux = departamentoRepository.findByNombre(eventoDTO.getDepartamento());
        if(aux.isPresent()){
            evento.setDepartamento(aux.get());
        } else{
            throw new EntityNotFoundException("No se encontró el departamento con nombre: "+eventoDTO.getDepartamento());
        }

        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setModalidad(eventoDTO.getModalidad());
        evento.setPlataforma(eventoDTO.getPlataforma());
        evento.setLinkReunion(eventoDTO.getLinkReunion());

        Optional<EstadoEvento> aux2 = estadoEventoRepository.findByDescripcion(eventoDTO.getEstado().getDescripcion());
        if(aux2.isPresent()){
            evento.setEstado(aux2.get());
        } else{
            throw new EntityNotFoundException("Estado invalido");
        }

        if(eventoDTO.getLugar() != null) {
            evento.setUbicacion(modelMapper.map(eventoDTO.getLugar(), Ubicacion.class));
        } else {
            evento.setUbicacion(null);
        }
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setHoraInicio(eventoDTO.getHoraInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        evento.setHoraFin(eventoDTO.getHoraFin());

        return evento;
    }

    public EventoDTO mapEventoToEventoDTO(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("No se puede mapear desde un objeto nulo");
        }

        EventoDTO eventoDTO = new EventoDTO();

        eventoDTO.setId(evento.getId());
        eventoDTO.setNombre(evento.getNombre());

        eventoDTO.setDepartamento(modelMapper.map(evento.getDepartamento(), DepartamentoPostDTO.class) );

        eventoDTO.setFechaInicio(evento.getFechaInicio());
        eventoDTO.setHoraInicio(evento.getHoraInicio());
        eventoDTO.setFechaFin(evento.getFechaFin());
        eventoDTO.setHoraFin(evento.getHoraFin());

        eventoDTO.setDescripcion(evento.getDescripcion());
        eventoDTO.setModalidad(evento.getModalidad());
        eventoDTO.setPlataforma(evento.getPlataforma());
        eventoDTO.setLinkReunion(evento.getLinkReunion());
        eventoDTO.setCodigoUUID(evento.getCodigoUUID());

        eventoDTO.setParticipantes(convertParticipanteToDTO(evento.getParticipantes()));

        if(evento.getUbicacion() != null) {
            eventoDTO.setUbicacion(modelMapper.map(evento.getUbicacion(), UbicacionDTO.class));
        } else {
            eventoDTO.setUbicacion(null);
        }

        return eventoDTO;
    }

    public Evento mapEventoDTOToEvento(EventoDTO eventoDTO){
        Evento evento = new Evento();

        // Mapeo directo de atributos
        evento.setNombre(eventoDTO.getNombre());

        Optional<Departamento> aux = departamentoRepository.findByNombre(eventoDTO.getDepartamento().getNombre());
        if(aux.isPresent()){
            evento.setDepartamento(aux.get());
        } else{
            throw new EntityNotFoundException("No se encontró el departamento con nombre: "+eventoDTO.getDepartamento().getNombre());
        }

        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setModalidad(eventoDTO.getModalidad());
        evento.setPlataforma(eventoDTO.getPlataforma());
        evento.setLinkReunion(eventoDTO.getLinkReunion());

        Optional<EstadoEvento> aux2 = estadoEventoRepository.findByDescripcion(eventoDTO.getEstado().getDescripcion());
        if(aux2.isPresent()){
            evento.setEstado(aux2.get());
        } else{
            throw new EntityNotFoundException("Estado invalido");
        }

        if(eventoDTO.getUbicacion() != null) {
            evento.setUbicacion(modelMapper.map(eventoDTO.getUbicacion(), Ubicacion.class));
        } else {
            evento.setUbicacion(null);
        }
        evento.setFechaInicio(eventoDTO.getFechaInicio());
        evento.setHoraInicio(eventoDTO.getHoraInicio());
        evento.setFechaFin(eventoDTO.getFechaFin());
        evento.setHoraFin(eventoDTO.getHoraFin());
        evento.setCodigoUUID(eventoDTO.getCodigoUUID());

        return evento;
    }

    public List<ParticipanteDTO> convertParticipanteToDTO(List<Participante> aux) {
        return aux.stream()
                .map(participante -> modelMapper.map(participante, ParticipanteDTO.class))
                .collect(Collectors.toList());
    }


// Optional<EstadoEvento> estadoEvento = estadoEventoRepository.findByDescripcion(parametro.get(0));

    public List<EventoDTO> busquedaFiltrada(List<String> parametro) {

        List<EstadoEvento> estadoEventos = new ArrayList<>();
        parametro.forEach(string->{
            Optional<EstadoEvento> estadoEventoOptional = estadoEventoRepository.findByDescripcion(string);
            if (estadoEventoOptional.isPresent()) {
            estadoEventos.add(estadoEventoOptional.get());}});
        if (!estadoEventos.isEmpty()) {
            List<Evento> eventosFiltrados = eventoRepository.findByEstadoIn(estadoEventos);
            if (eventosFiltrados.isEmpty()) {
                throw new EntityNotFoundException("No se encontraron eventos con estado " + parametro);
            } else {
                List<EventoDTO> eventosFiltradosDTO = new ArrayList<>();
                eventosFiltrados.forEach(evento -> {
                    EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
                    eventoDTO.setParticipantes(convertParticipantesToDTO(evento.getParticipantes()));
                    eventosFiltradosDTO.add(modelMapper.map(eventoDTO,EventoDTO.class));
                });
                return eventosFiltradosDTO;
            }
        }
        List<Departamento> departamentos = new ArrayList<>();
        parametro.forEach(dpto->{
            Optional<Departamento> dptoopcional = departamentoRepository.findByNombre(dpto);
            if(dptoopcional.isPresent()){
            departamentos.add(departamentoRepository.findByNombre(dpto).get());
        }});
        if (!departamentos.isEmpty()) {
            List<Evento> eventosFiltrados = eventoRepository.findByDepartamentoIn(departamentos);
            if (eventosFiltrados.isEmpty()) {
                throw new EntityNotFoundException("No se encontraron eventos del departamento " + parametro);
            } else {
                List<EventoDTO> eventosFiltradosDTO = new ArrayList<>();
                eventosFiltrados.forEach(evento -> {
                    EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
                    eventoDTO.setParticipantes(convertParticipantesToDTO(evento.getParticipantes()));
                    eventosFiltradosDTO.add(modelMapper.map(eventoDTO,EventoDTO.class));
                });
                return eventosFiltradosDTO;
            }
        }


        try {
            List<Modalidad> modalidades = new ArrayList<>();
            parametro.forEach(string->{modalidades.add(Enum.valueOf(Modalidad.class,string));});
           // Modalidad modalidad = Enum.valueOf(Modalidad.class, parametro.get(0));
            List<Evento> eventosFiltrados = eventoRepository.findByModalidadIn(modalidades);
            if (eventosFiltrados.isEmpty()) {
                throw new EntityNotFoundException("No se encontraron eventos de modalidad " + parametro);
            } else {
                List<EventoDTO> eventosFiltradosDTO = new ArrayList<>();
                eventosFiltrados.forEach(evento -> {
                    EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
                    eventoDTO.setParticipantes(convertParticipantesToDTO(evento.getParticipantes()));
                    eventosFiltradosDTO.add(modelMapper.map(eventoDTO,EventoDTO.class));
                });
                return eventosFiltradosDTO;

            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(parametro + "No se identificó como un estado o departamento o modalidad");
        }
    }




    public List<EventoDTO> obtenerUnicoDuplicado(List<EventoDTO> lista) {
        Set<EventoDTO> setUnicos = new HashSet<>();
        Set<EventoDTO> elementosRepetidos = new HashSet<>();
        List<EventoDTO> resultado = new ArrayList<>();

        for (EventoDTO evento : lista) {
            if (!setUnicos.add(evento)) {
                elementosRepetidos.add(evento);
            }
        }

        resultado.addAll(elementosRepetidos);
        return resultado;
    }

    public List<String> obtenerPlataformas(){
        List<String> plataformasNombres = new ArrayList<>();
        List<PlataformaEvento> plataformas = plataformaRepository.findAll();
        plataformas.forEach(plataforma -> {
            plataformasNombres.add(plataforma.getDescripcion());
        });
        return plataformasNombres;

    }


}
