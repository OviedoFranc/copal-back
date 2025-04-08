package aceleradora.socios.back.clases.socio;
import java.util.ArrayList;
import java.util.Collection;

import aceleradora.socios.back.clases.socio.Socio;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@Entity
public class Etiqueta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    public Etiqueta(){}

    public Etiqueta(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
    //TODO: REVISAR ESTE COMENTARIO
//    @JsonProperty("name")
//    private String nombre;
//
//
//    @JsonProperty("description")
//    private String descripcion;
//
//    @ManyToMany(mappedBy = "etiquetas")
//    private Collection<Socio> socios;
//
//    public Etiqueta() {
//        super();
//    }
//
//    public Etiqueta(Long id, String nombre, String descripcion, Collection<Socio> usuarios) {
//        this.id = id;
//        this.nombre = nombre;
//        this.descripcion = descripcion;
//        this.socios = new ArrayList<Socio>();
//    }