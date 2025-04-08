package aceleradora.socios.back.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aceleradora.socios.back.clases.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
}
