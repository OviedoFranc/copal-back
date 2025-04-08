package aceleradora.socios.back.clases.socio;

import java.time.LocalDate;

import aceleradora.socios.back.clases.socio.Socio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Membresia {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nombre;
	private Boolean estado;
	private LocalDate fechaPago;
	private double valor;
	private int duracion;
	@OneToOne
	@JoinColumn(name = "socio_id")
	private Socio socio;
	
	public Membresia() {}

}
