package aceleradora.socios.back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO {
    
    private Long id;
    
    @JsonProperty("name")
    private String nombre;
    
    public CategoriaDTO() {}
    
	public CategoriaDTO(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}


    
}
