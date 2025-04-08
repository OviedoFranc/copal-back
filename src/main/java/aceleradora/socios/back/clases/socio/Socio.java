package aceleradora.socios.back.clases.socio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import aceleradora.socios.back.clases.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Entity
@Table(name = "socios")
public class Socio{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ID_categoria")
	private Categoria categoria;
	
	@OneToOne(mappedBy = "socio")
	private Membresia membresia;
	
	@ManyToMany
    @JoinTable(
            name = "socio_etiqueta",
            joinColumns = @JoinColumn(name = "ID_socio"),
            inverseJoinColumns = @JoinColumn(name = "ID_etiqueta"))
	private Collection<Etiqueta> etiquetas;

	private String nombre;
	private Boolean estado;
	private String imagen;
	private String web;
	private Long cuit;
	private Date fechaUnion;


	private String mail;
	private Long telefono;
	private String presidente;

	@OneToOne(mappedBy = "socio", cascade = CascadeType.ALL)
	private Usuario usuario;
	
	public Socio() {}

	public Socio(Long id, Categoria categoria, Membresia membresia, Collection<Etiqueta> etiquetas, Boolean estado,
			String imagen, String web, Long cuit, Date fechaUnion,String nombre) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.categoria = categoria;
		this.membresia = membresia;
		this.etiquetas = etiquetas;
		this.estado = estado;
		this.imagen = imagen;
		this.web = web;
		this.cuit = cuit;
		this.fechaUnion = fechaUnion;
	}

	public Socio(String nombre, String presidente, Long cuit, Long telefono, String mail,
			Categoria categoria, String web, Date fechaUnion, Collection<Etiqueta> etiquetas) {
		super();
		this.nombre = nombre;
		this.presidente = presidente;
		this.estado = true;
		this.cuit = cuit;
		this.telefono = telefono;
		this.mail = mail;
		this.categoria = categoria;
		this.web = web;
		this.fechaUnion = fechaUnion;
		this.etiquetas = etiquetas;
	}

	public boolean isActivo() {
		return estado;
	}
	

}
