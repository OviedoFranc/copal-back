package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.evento.Participante;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class DepartamentoDTO {

    private String nombre;
    private Long id;
    private String objetivo;
    private List<AutoridadDTO> autoridades;
    private Boolean estaActivo;

}
