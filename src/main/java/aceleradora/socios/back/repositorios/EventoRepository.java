package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.evento.EstadoEvento;
import aceleradora.socios.back.clases.evento.Evento;
import aceleradora.socios.back.clases.evento.Modalidad;
import aceleradora.socios.back.clases.socio.Socio;
import aceleradora.socios.back.dto.EventoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findByNombre(String nombre);
    Optional<Evento> findByCodigoUUID(String codigo);
    List<Evento> findByEstadoIn(List<EstadoEvento> estados);
    List<Evento> findByModalidadIn(List<Modalidad> modalidad);
    List<Evento> findByDepartamentoIn(List<Departamento> departamento);
}
