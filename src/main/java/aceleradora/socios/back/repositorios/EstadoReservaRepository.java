package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.espacio.EstadoReserva;
import aceleradora.socios.back.clases.evento.EstadoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Long> {
 Optional<EstadoReserva> findByNombre(String nombre);
}


