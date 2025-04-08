package aceleradora.socios.back.clases.socio;

import java.util.List;

import aceleradora.socios.back.clases.socio.Socio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
public class Categoria {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @JsonProperty("name")
    private String nombre;
    
    public Categoria() {}
    public Categoria(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}


}

