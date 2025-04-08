package aceleradora.socios.back.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aceleradora.socios.back.clases.socio.Etiqueta;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long>{
    Optional<Etiqueta> findByNombre(String nombre);
    List<Etiqueta> findByNombreIn(List<String> nombres);
}
