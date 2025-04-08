package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.evento.EstadoEvento;
import aceleradora.socios.back.clases.evento.Modalidad;
import aceleradora.socios.back.clases.evento.Participante;
import aceleradora.socios.back.clases.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventoPostDTO {

    private String nombre;

    private String departamento;

    private LocalDate fechaInicio;
    private LocalTime horaInicio;

    private LocalDate fechaFin;
    private LocalTime horaFin;

    private String descripcion;
    private Modalidad modalidad;

    private String plataforma;
    private String linkReunion;

    //TODO: solo el nombre
    private EstadoEvento estado;

    private UbicacionDTO lugar;



}
