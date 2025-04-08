package aceleradora.socios.back.clases.espacio;

import aceleradora.socios.back.clases.Usuario;
import aceleradora.socios.back.clases.departamento.Departamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter@Setter@NoArgsConstructor
@Entity
public class Reserva {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Departamento departamento;

    private String nombre;

    private String email;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private String descripcion;

    @ManyToOne
    private Lugar lugar;

    @ManyToMany (fetch = FetchType.EAGER)
    private List<Recurso> recursos;

    @ManyToOne
    private EstadoReserva estadoReserva;

    @Column(columnDefinition="TEXT")
    private String observaciones;

}
