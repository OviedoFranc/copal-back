package aceleradora.socios.back.clases.socio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@Entity
@Table(name = "etiqueta")
public class Etiqueta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    public Etiqueta(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}