package aceleradora.socios.back;

import aceleradora.socios.back.dto.UsuarioDTO;
import aceleradora.socios.back.repositorios.UserRepository;
import aceleradora.socios.back.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
public class UserTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void altaUsuarioTest(){

        UsuarioDTO u1 = new UsuarioDTO();
        u1.setNombre("usuario1");
        u1.setMail("usuario1@gmail.com");
        u1.setDescripcion("Descripcion1");
        u1.setContrasenia("_");
        u1.setTelefono((long) 112389843);
        u1.setActivo(true);


        List<UsuarioDTO> listaUsuarios = userService.traerTodos();

        userService.darDeAltaUsuario(u1);

        List<UsuarioDTO> listaUsuarios1 = userService.traerTodos();

        Assertions.assertEquals(listaUsuarios.size()+1, listaUsuarios1.size());
        Assertions.assertEquals(u1.getNombre(), listaUsuarios1.get(listaUsuarios1.size()-1).getNombre());
        Assertions.assertEquals(u1.getMail(), listaUsuarios1.get(listaUsuarios1.size()-1).getMail());
        Assertions.assertEquals(u1.getTelefono(), listaUsuarios1.get(listaUsuarios1.size()-1).getTelefono());

    }

    @Test
    public void bajaUsuarioTest(){

        UsuarioDTO u2 = new UsuarioDTO();
        u2.setNombre("usuario2");
        u2.setMail("usuario2@gmail.com");
        u2.setDescripcion("Descripcion 2");
        u2.setContrasenia("_");
        u2.setTelefono((long) 112389843);
        u2.setActivo(true);


        userService.darDeAltaUsuario(u2);

        List<UsuarioDTO> listaUsuarios = userService.traerTodos();

        userService.darDeBajaUsuario((long) listaUsuarios.size());

        List<UsuarioDTO> listaUsuarios2 = userService.traerTodos();

        Assertions.assertEquals(false, listaUsuarios2.get(listaUsuarios2.size()-1).getActivo());


    }

    @Test
    public void modificarUsuarioTest(){

        UsuarioDTO u3 = new UsuarioDTO();
        u3.setNombre("usuario3");
        u3.setMail("usuario3@gmail.com");
        u3.setDescripcion("Descripcion 3");
        u3.setContrasenia("_");
        u3.setTelefono((long) 112389843);
        u3.setActivo(true);


        userService.darDeAltaUsuario(u3);

        List<UsuarioDTO> listaUsuarios = userService.traerTodos();

        UsuarioDTO um3 = new UsuarioDTO();
        um3.setNombre("usuario3 actualizado");
        um3.setMail("usuario3actualizado@gmail.com");
        um3.setDescripcion("Descripcion 3 actualizado");
        um3.setContrasenia("_");
        um3.setTelefono((long) 112365892);

        userService.modificarUsuario(listaUsuarios.size() -1,  um3);

        List<UsuarioDTO> listaUsuarios3 = userService.traerTodos();

        Assertions.assertNotEquals(u3.getNombre(), listaUsuarios3.get(listaUsuarios3.size()-1).getNombre());
        Assertions.assertEquals(um3.getNombre(), listaUsuarios3.get(listaUsuarios3.size()-1).getNombre());
        Assertions.assertEquals(um3.getMail(), listaUsuarios3.get(listaUsuarios3.size()-1).getMail());
        Assertions.assertEquals(um3.getTelefono(), listaUsuarios3.get(listaUsuarios3.size()-1).getTelefono());
        Assertions.assertEquals(um3.getDescripcion(), listaUsuarios3.get(listaUsuarios3.size()-1).getDescripcion());

    }


}
