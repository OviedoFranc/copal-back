package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.clases.socio.Etiqueta;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResumenSocioDTO {
  private Integer id;
  private String nombre;
  private String cuit;
  private String presidente;
  private Categoria tipoSocio;
  private List<Etiqueta> etiquetas;
  private Boolean activo;
  private String mail;
  private String telefono;
  private Integer AniosDeAntiguedad;
}