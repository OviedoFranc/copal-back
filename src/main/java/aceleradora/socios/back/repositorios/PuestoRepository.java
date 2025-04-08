package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.departamento.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {
    Optional<Puesto> findByNombre(String nombre);
}
