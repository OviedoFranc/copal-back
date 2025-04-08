package aceleradora.socios.back.repositorios;


import aceleradora.socios.back.clases.espacio.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByNombre(String nombre);
}
