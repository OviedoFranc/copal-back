package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.Rol;
import lombok.Getter;
import lombok.Setter;


import java.sql.Date;

@Getter@Setter

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String contrasenia;
    private String mail;
    private Date fechaCreacion;
    private Long telefono;
    private Boolean admin;
    private Rol rol;
    private Boolean activo;
}

