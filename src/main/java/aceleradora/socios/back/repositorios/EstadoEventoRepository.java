package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.evento.EstadoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoEventoRepository extends JpaRepository<EstadoEvento, Long> {

    Optional<EstadoEvento> findByDescripcion(String descripcion);
}
