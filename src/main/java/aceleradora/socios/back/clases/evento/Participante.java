package aceleradora.socios.back.clases.evento;

import aceleradora.socios.back.clases.socio.Socio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    private String estado;//TODO: definir esto

    @Enumerated(EnumType.STRING)
    private TipoParticipante tipoParticipante;

//    private Integer satisfaccion;

//    private String codigoUnico;

    @ManyToOne
    private Socio socioAsociado;

    @ManyToOne
    private Socio socioConvocante;

    private String entidadQueRepresenta;

    private String email;




    public Participante() {
    }

    public Participante(String nombre, String apellido, String estado,String email) {

        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

}
