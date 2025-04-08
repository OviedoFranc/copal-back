package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.evento.Evento;
import aceleradora.socios.back.clases.evento.Participante;
import aceleradora.socios.back.clases.evento.TipoParticipante;
import aceleradora.socios.back.clases.socio.Socio;
import aceleradora.socios.back.dto.*;
import aceleradora.socios.back.repositorios.EventoRepository;
import aceleradora.socios.back.repositorios.ParticipanteRepository;
import aceleradora.socios.back.repositorios.SocioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class ParticipanteService {

    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;
    private final ModelMapper modelMapper;
    private final SocioRepository socioRespository;

    public ParticipanteService(EventoRepository eventoRepository, ParticipanteRepository participanteRepository, ModelMapper modelMapper,SocioRepository socioRespository) {
        this.eventoRepository = eventoRepository;
        this.participanteRepository = participanteRepository;
        this.modelMapper = modelMapper;
        this.socioRespository = socioRespository;
    }

    public ParticipanteDTO agregarParticipante(Long id, ParticipantePostDTO participanteDTO) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con codigo " + id));

        Participante participante = toClass(participanteDTO);

        List<Participante> participantes = evento.getParticipantes();
        participantes.add(participante);

        eventoRepository.save(evento);

        return modelMapper.map(participante, ParticipanteDTO.class);
    }

    public ParticipantePostDTO agregarParticipanteForm(String codigo, ParticipantePostDTO participanteDTO) {
        Evento evento = eventoRepository.findByCodigoUUID(codigo)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con codigo " + codigo));

        Participante participante = toClass(participanteDTO);

        List<Participante> participantes = evento.getParticipantes();
        participantes.add(participante);

        eventoRepository.save(evento);

        return modelMapper.map(participante, ParticipantePostDTO.class);
    }

    public EventoDTO agregarParticipantes(Long eventoId, List<ParticipanteDTO> participanteDTOS) {

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con ID " + eventoId));

        participanteDTOS.forEach(participanteDTO -> {
            Participante participante = modelMapper.map(participanteDTO, Participante.class);

            //TODO: definir como se asignas los estados a un participante
            //participante.setEstado(participanteDTO.getEstado());
            //participante.setUsuario();

            evento.getParticipantes().add(participante);
        });
        eventoRepository.save(evento);
        ;
        return modelMapper.map(evento, EventoDTO.class);
    }

    public Participante editar(Long participanteID, ParticipantePostDTO participanteDTO){
        Participante participante = participanteRepository.findById(participanteID)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el participante con ID " + participanteID));

        Participante participanteActualizado = toClass(participanteDTO);
        participanteActualizado.setId(participanteID);

        participanteRepository.save(participanteActualizado);
        return participanteActualizado;
    }

    public EventoDTO editarParticipantes(Long eventoID, List<ParticipanteDTO> participanteDTOS) {
        Evento evento = eventoRepository.findById(eventoID)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con ID: " + eventoID));

        List<Participante> nuevosParticipantes = new ArrayList<>();

        participanteDTOS.forEach(participanteDTO -> {
            Participante participanteaux = modelMapper.map(participanteDTO, Participante.class);
            nuevosParticipantes.add(participanteaux);
        });

        evento.setParticipantes(nuevosParticipantes);
        eventoRepository.save(evento);
        return modelMapper.map(evento, EventoDTO.class);
    }

    public void borrarParticipante(long participanteid) {
        Optional<Participante> participante = participanteRepository.findById(participanteid);

            if (participante.isPresent()) {
                participanteRepository.deleteById(participanteid);
            } else
                throw new EntityNotFoundException("No se encontró un participante con id: " + participanteid + " para eliminar");

    }

    public List<ParticipanteDTO> obtenerParticipantes(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el evento con ID " + id));

        List<Participante> aux = evento.getParticipantes();
        List<ParticipanteDTO> participanteDTOS = convertParticipantesToDTO(aux);
        return participanteDTOS;
    }


    public List<ParticipanteDTO> convertParticipantesToDTO(List<Participante> participantes) {

        List<ParticipanteDTO> participanteDTOS = new ArrayList<>(); // Initialize the list
        participantes.forEach(participante -> {
            ParticipanteDTO participanteDTO = modelMapper.map(participante, ParticipanteDTO.class);
            participanteDTOS.add(participanteDTO);
        });
        return participanteDTOS;
    }

    public String obtenerCodigoParticipante(Long id) {
        GeneradorCodigoUnico generador = new GeneradorCodigoUnico();
        String codigoUnico = generador.generarCodigoUnico(id);

        return codigoUnico;

    }

    public Participante toClass(ParticipantePostDTO participanteDTO) {

        Participante participante = new Participante();
        participante.setNombre(participanteDTO.getNombre());
        participante.setApellido(participanteDTO.getApellido());
        participante.setTipoParticipante(participanteDTO.getTipoParticipante());

        if (participanteDTO.getTipoParticipante() == TipoParticipante.ASOCIADO) {
            // Si es ASOCIADO, requiere
            if (participanteDTO.getSocioAsociadoId() == null) {
                throw new IllegalArgumentException("El socioAsociadoId es obligatorio para un participante ASOCIADO.");
            }
            Optional<Socio> socioAsociado = socioRespository.findById(participanteDTO.getSocioAsociadoId().get());
            socioAsociado.ifPresent(participante::setSocioAsociado);

        } else if (participanteDTO.getTipoParticipante() == TipoParticipante.INVITADO) {
            // Si es INVITADO
            if (participanteDTO.getSocioConvocanteId() == null || participanteDTO.getEntidadQueRepresenta() == null) {
                throw new IllegalArgumentException("El socioConvocante y la entidadQueRepresenta son obligatorios para un participante INVITADO.");
            }
            if(participanteDTO.getSocioConvocanteId().get() != 0){
                Optional<Socio> socioConvocante = socioRespository.findById(participanteDTO.getSocioConvocanteId().get());
                socioConvocante.ifPresent(participante::setSocioConvocante);
                participante.setEntidadQueRepresenta(participanteDTO.getEntidadQueRepresenta().get());
                participante.setEmail(participanteDTO.getEmail());
            } else if (participanteDTO.getSocioConvocanteId().get() == 0){
                participante.setEntidadQueRepresenta(participanteDTO.getEntidadQueRepresenta().get());
                participante.setEmail(participanteDTO.getEmail());
            }

        } else {
            throw new IllegalArgumentException("Tipo de participante no válido: " + participanteDTO.getTipoParticipante());
        }
        return participante;
    }


}

