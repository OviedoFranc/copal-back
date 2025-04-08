package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.espacio.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Long> {
     Optional<Lugar> findByNombre(String nombre);
}
