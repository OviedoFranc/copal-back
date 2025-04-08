package aceleradora.socios.back.clases.departamento;

import aceleradora.socios.back.clases.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "autoridad")
public class Autoridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "puesto_id")
    private Puesto puesto;

    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    public Autoridad() {}
    public Autoridad(Long id, Puesto puesto) {
        this.id = id;
        this.puesto = puesto;

    }
}
