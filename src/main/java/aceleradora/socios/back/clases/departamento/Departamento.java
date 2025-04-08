package aceleradora.socios.back.clases.departamento;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter@Setter
@Entity
@Table(name = "departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank
    private String nombre;

    @Column(columnDefinition="TEXT")
    private String objetivo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "departamento_id")
    private List<Autoridad> autoridades;

    private String imagen;

    private Boolean estaActivo;
    public Departamento() {}
    public Departamento(Long id, String nombre, String descripcion, List<Autoridad> autoridades, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.objetivo = descripcion;
        this.autoridades = autoridades;
        this.estaActivo = estado;
    }
}
