package aceleradora.socios.back.repositorios;

import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.clases.socio.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aceleradora.socios.back.clases.socio.Socio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long>{
    List<Socio> findByEtiquetasIn(List<Etiqueta> etiquetas);
    List<Socio> findByEtiquetasInAndMembresia_FechaPagoBefore(List<Etiqueta> etiquetas, LocalDate fechaInicioMembresia);
    List<Socio> findByMembresia_FechaPagoBefore(LocalDate fecha);
    List<Socio> findByCategoriaAndEtiquetasInAndMembresia_FechaPagoBefore(Categoria categoria , List<Etiqueta> etiquetas, LocalDate fechaInicioMembresia);
    List<Socio> findByCategoria(Categoria categoria);
    List<Socio> findByCategoriaAndMembresia_FechaPagoBefore(Categoria categoria, LocalDate fechaInicioMembresia);
    List<Socio> findByCategoriaAndEtiquetasIn(Categoria categoria, List<Etiqueta> etiquetas);
    List<Socio> findByNombreContaining(String nombre);
    List<Socio> findByCategoriaAndNombreContaining(Categoria categoria, String nombre);
    List<Socio> findByEstado(Boolean estado);
    List<Socio> findByCategoriaAndEstado(Categoria categoria, Boolean estado);
    List<Socio> findByEtiquetasInAndEstado(List<Etiqueta> etiquetas, Boolean estado);
    Optional<Socio> findByNombre(String nombre);
}
