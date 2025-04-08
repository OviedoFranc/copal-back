package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.ubicacion.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
}
