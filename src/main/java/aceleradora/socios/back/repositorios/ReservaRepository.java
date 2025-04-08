package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.departamento.Puesto;
import aceleradora.socios.back.clases.espacio.EstadoReserva;
import aceleradora.socios.back.clases.espacio.Lugar;
import aceleradora.socios.back.clases.espacio.Reserva;
import aceleradora.socios.back.clases.evento.EstadoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
   List<Reserva> findByNombre(String nombre);
   List<Reserva> findByLugar(Lugar Lugar);
   List<Reserva> findByEstadoReservaIn(List<EstadoReserva> estados);

}
