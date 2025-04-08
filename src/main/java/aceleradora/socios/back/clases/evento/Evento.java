package aceleradora.socios.back.clases.evento;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.ubicacion.Ubicacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private LocalDate fechaInicio;
    private LocalTime horaInicio;

    private LocalDate fechaFin;
    private LocalTime horaFin;

    @Column(columnDefinition="TEXT")
    private String descripcion;

    @ManyToOne
    private EstadoEvento estado;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id")
    private List<Participante> participantes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "evento_ID")
    private List<Actividad> actividades;

    @ManyToOne
    private Departamento departamento;

    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Ubicacion ubicacion;

    private String plataforma;
    private String linkReunion;

    private String codigoUUID;


    public Evento() {
    }

    public Evento(String nombre, LocalDate fechaInicio, LocalTime horaInicio , LocalDate fechaFin, LocalTime horaFin ,
                  String descripcion, Ubicacion ubicacion, Modalidad modalidad, List<Participante> participantes,
                  EstadoEvento estado) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.modalidad = modalidad;
        this.participantes = participantes;
        this.estado = estado;
    }

}
