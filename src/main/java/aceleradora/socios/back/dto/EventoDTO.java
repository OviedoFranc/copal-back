package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.evento.EstadoEvento;
import aceleradora.socios.back.clases.evento.Modalidad;
import aceleradora.socios.back.clases.evento.Participante;
import aceleradora.socios.back.clases.ubicacion.Ubicacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter@Setter
public class EventoDTO {

    private Long id;
    private String nombre;

    private DepartamentoPostDTO departamento;

    private LocalDate fechaInicio;
    private LocalTime horaInicio;

    private LocalDate fechaFin;
    private LocalTime horaFin;

    private String descripcion;
    private Modalidad modalidad;

    private String plataforma;
    private String linkReunion;

    private EstadoEvento estado;

    private List<ParticipanteDTO> participantes;

    private UbicacionDTO ubicacion;

    private String codigoUUID;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoDTO evento = (EventoDTO) o;
        return id == evento.id &&
                Objects.equals(nombre, evento.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
