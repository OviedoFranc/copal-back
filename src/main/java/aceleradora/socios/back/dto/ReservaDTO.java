package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.Usuario;
import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.espacio.EstadoReserva;
import aceleradora.socios.back.clases.espacio.Lugar;
import aceleradora.socios.back.clases.espacio.Recurso;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
public class ReservaDTO {
    private Long id;

    private String nombre;

    private String email;

    private Departamento departamento;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private String descripcion;

    private Lugar lugar;

    private String observaciones;

    private List<Recurso> recursos;

    private EstadoReserva estadoReserva;
}
