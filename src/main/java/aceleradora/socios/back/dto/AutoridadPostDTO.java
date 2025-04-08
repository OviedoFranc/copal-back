package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.departamento.Puesto;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AutoridadPostDTO {

    private Puesto puesto;
    private Long usuarioId;
}
