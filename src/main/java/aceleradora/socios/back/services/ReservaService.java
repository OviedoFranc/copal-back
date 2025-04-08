package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.espacio.EstadoReserva;
import aceleradora.socios.back.clases.espacio.Lugar;
import aceleradora.socios.back.clases.espacio.Recurso;
import aceleradora.socios.back.clases.espacio.Reserva;
import aceleradora.socios.back.dto.ReservaDTO;
import aceleradora.socios.back.repositorios.*;
import aceleradora.socios.back.repositorios.EstadoReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final LugarRepository lugarRepository;
    private final ModelMapper modelMapper;

    private final RecursoRepository recursoRepository;
    private final EstadoReservaRepository estadoReservaRepository;
    private final DepartamentoRepository departamentoRepository;


    public ReservaService(ReservaRepository reservaRepository, LugarRepository lugarRepository, ModelMapper modelMapper, RecursoRepository recursoRepository, EstadoReservaRepository estadoReservaRepository,DepartamentoRepository departamentoRepository) {
        this.reservaRepository = reservaRepository;
        this.lugarRepository = lugarRepository;
        this.modelMapper = modelMapper;
        this.recursoRepository = recursoRepository;
        this.estadoReservaRepository = estadoReservaRepository;
        this.departamentoRepository = departamentoRepository;

        }

        public ReservaDTO obtenerPorId (Long id){
            Optional<Reserva> reserva = reservaRepository.findById(id);
            if (reserva.isPresent()) {
                return modelMapper.map(reserva, ReservaDTO.class);
            } else throw new EntityNotFoundException("no hay reserva con id: " + id);
        }


        public List<ReservaDTO> obtenerPorNombre (String nombre){
            Optional<Lugar> lugarPedido = lugarRepository.findByNombre(nombre);
            if (lugarPedido.isPresent()) {

                List<Reserva> reservas = reservaRepository.findByLugar(lugarPedido.get());
                List<ReservaDTO> reservaDTOS = new ArrayList<>();
                if (reservas.isEmpty()) {
                    throw new EntityNotFoundException("no hay reservas para un Lugar de nombre" + nombre);
                } else {
                    reservas.forEach(reservaDTO -> {
                        reservaDTOS.add(modelMapper.map(reservaDTO, ReservaDTO.class));
                    });
                    return reservaDTOS;
                }
            } else throw new EntityNotFoundException("no hay un Lugar de nombre" + nombre);
        }

        public ReservaDTO guardarReserva (ReservaDTO reservaDTO){
            Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
            reserva.setEstadoReserva(estadoReservaRepository.findByNombre("pendiente").get());
            reserva.setLugar(lugarRepository.findByNombre(reservaDTO.getLugar().getNombre()).get());
            reserva.setDepartamento(departamentoRepository.findByNombre(reservaDTO.getDepartamento().getNombre()).get());
            List<Recurso> recursos = new ArrayList<>();
            reserva.getRecursos().forEach(recurso -> {
                recursos.add(recursoRepository.findByNombre(recurso.getNombre()).get(0));
            });
            reserva.setRecursos(recursos);
            Reserva reservaAux = reservaRepository.save(reserva);
            return modelMapper.map(reservaAux, ReservaDTO.class);
        }
/*
        public ReservaDTO bajaReserva (Long id){
            Optional<Reserva> reserva = reservaRepository.findById(id);
            if (reserva.isPresent()) {
//            reserva.get().setEstadoReserva();
                Reserva reservaGuardada = reservaRepository.save(reserva.get());
                return modelMapper.map(reservaGuardada, ReservaDTO.class);
            } else throw new EntityNotFoundException("Reserva no encontrada por id" + id);
        }
*/
        public ReservaDTO eliminarReserva (Long id){
            if (!reservaRepository.existsById(id)) {
                throw new EntityNotFoundException("No se encontró la reserva con ID: " + id);
            }
            Optional<Reserva> reserva = reservaRepository.findById(id);
            Reserva reservaGuardada = reserva.get();
            reservaRepository.deleteById(id);
            return modelMapper.map(reservaGuardada, ReservaDTO.class);
        }

        public ReservaDTO editarReserva (Long id, ReservaDTO reservaDTO){
            Optional<Reserva> reserva = reservaRepository.findById(id);
            if (reserva.isPresent()) {
                Reserva reservaReal = reserva.get();
                reservaReal = modelMapper.map(reservaDTO, Reserva.class);
                Reserva reservaGuardada = reservaRepository.save(reservaReal);
                return modelMapper.map(reservaGuardada, ReservaDTO.class);
            } else throw new EntityNotFoundException("Reserva no encontrada por id" + id);
        }

        public List<ReservaDTO> listar () {
            List<Reserva> reservas = reservaRepository.findAll();
            List<ReservaDTO> reservaDTOS = new ArrayList<>();
            if (reservas.isEmpty()) {
                throw new EntityNotFoundException("no hay reservas");
            } else {
                reservas.forEach(reservaDTO -> {
                    reservaDTOS.add(modelMapper.map(reservaDTO, ReservaDTO.class));
                });
                return reservaDTOS;
            }
        }

        public List<ReservaDTO> busquedaFiltrada (List<String>parametro) {
            if(parametro == null || parametro.isEmpty()){
                List<ReservaDTO> reservasFiltradasDTO = new ArrayList<>();
                reservaRepository.findAll().forEach(reserva -> {
                    ReservaDTO reservaDTO = modelMapper.map(reserva, ReservaDTO.class);
                    reservasFiltradasDTO.add(reservaDTO);
                });
                return reservasFiltradasDTO;
            }
            List<EstadoReserva> estadoReservas = new ArrayList<>();
            parametro.forEach(string -> {
                Optional<EstadoReserva> estadoReservaOptional = estadoReservaRepository.findByNombre(string);
                if (estadoReservaOptional.isPresent()) {
                    estadoReservas.add(estadoReservaOptional.get());
                }
            });
            if (!estadoReservas.isEmpty()) {
                List<Reserva> reservasFiltrados = reservaRepository.findByEstadoReservaIn(estadoReservas);
                if (reservasFiltrados.isEmpty()) {
                    throw new EntityNotFoundException("no se encontraron reservas con estado " + parametro);
                } else {
                    List<ReservaDTO> reservasFiltradasDTO = new ArrayList<>();
                    reservasFiltrados.forEach(reserva -> {
                        ReservaDTO reservaDTO = modelMapper.map(reserva, ReservaDTO.class);
                        reservasFiltradasDTO.add(reservaDTO);
                    });
                    return reservasFiltradasDTO;
                }
            } else throw new EntityNotFoundException("no se encontraron reservas con estado/s :" + parametro);
        }


//    @Transactional
//    public Reserva agregarResponsable(Long reservaId, Long usuarioId) {
//        Reserva reserva = reservaRepository.findById(reservaId).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
//        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        reserva.getResponsables().add(usuario);
//        return reservaRepository.save(reserva);
//    }
//
//    @Transactional
//    public Reserva eliminarResponsable(Long reservaId, Long usuarioId) {
//        Reserva reserva = reservaRepository.findById(reservaId).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
//        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//        reserva.getResponsables().remove(usuario);
//        return reservaRepository.save(reserva);
//    }
//
//    public List<Usuario> listarResponsables(Long reservaId) {
//        Reserva reserva = reservaRepository.findById(reservaId).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
//        return reserva.getResponsables();
//    }

        public List<EstadoReserva> listarEstados () {
            List<EstadoReserva> estados = estadoReservaRepository.findAll();
            return estados;
        }

        public List<Recurso> listarRecursos () {
            List<Recurso> recursos = recursoRepository.findAll();
            return recursos;
        }

        public List<Lugar> listarLugares () {
            List<Lugar> lugares = lugarRepository.findAll();
            return lugares;
        }

    }