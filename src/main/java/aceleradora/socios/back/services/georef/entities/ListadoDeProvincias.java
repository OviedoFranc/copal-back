package aceleradora.socios.back.services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
@Getter
@Setter
public class ListadoDeProvincias {
  public int cantidad;
  public int total;
  public int inicio;
  public Parametro parametros;
  public List<Provincia> provincias;

  public Optional<Provincia> provinciaDeId(int id){
    return this.provincias.stream()
        .filter(p -> p.getId() == id)
        .findFirst();
  }

  public Optional<Provincia> provinciaDeNombre(String nombre){
    return this.provincias.stream()
            .filter(p -> p.getNombre() == nombre)
            .findFirst();
  }

  private class Parametro {
    public  List<String> campos;
  }
}