package aceleradora.socios.back.dto;


import aceleradora.socios.back.clases.departamento.Puesto;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AutoridadDTO {

    private String puesto;
    private String nombre;
    private Long usuarioId;
}
