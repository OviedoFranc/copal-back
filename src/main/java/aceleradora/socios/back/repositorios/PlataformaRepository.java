package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.evento.Evento;
import aceleradora.socios.back.clases.evento.PlataformaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlataformaRepository extends JpaRepository<PlataformaEvento, Long> {
}
