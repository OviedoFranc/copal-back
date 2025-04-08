package aceleradora.socios.back.clases.ubicacion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String direccion;

    private String piso;

    private String localidad;

    private String provincia;

    public Ubicacion() {
    }

    public Ubicacion(String direccion, String piso, String localidad, String provincia){
        this.direccion = direccion;
        this.piso=piso;
        this.localidad = localidad;
        this.provincia = provincia;
    }
}
