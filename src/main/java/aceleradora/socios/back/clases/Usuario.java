package aceleradora.socios.back.clases;

import java.sql.Date;
import java.util.Set;

import aceleradora.socios.back.clases.socio.Socio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
	
	private String nombre;
	private String descripcion;
	private String contrasenia;
	private String mail;
	private Date fechaCreacion;
	private Long telefono;
	private Boolean admin;
	
	@OneToOne
    private Socio socio;

	@ManyToMany
	@JoinTable(
			name = "usuario_rol",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "rol_id")
	)
	private Set<Rol> roles;

	private Boolean activo;

	public Usuario() {}
	public Usuario(String nombre) {
		super();
		this.nombre = nombre;
		this.activo= true;
	}
	public Usuario(Long id, String nombre, String descripcion, String contrasenia, String mail, Date fechaCreacion,
			Long telefono, Boolean admin) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.contrasenia = contrasenia;
		this.mail = mail;
		this.fechaCreacion = fechaCreacion;
		this.telefono = telefono;
		this.admin = admin;
		this.activo= true;
	}
	

}
