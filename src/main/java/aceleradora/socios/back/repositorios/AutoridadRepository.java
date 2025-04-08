package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.departamento.Autoridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoridadRepository extends JpaRepository<Autoridad, Long> {
}
