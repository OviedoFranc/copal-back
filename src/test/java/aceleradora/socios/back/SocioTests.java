package aceleradora.socios.back;

import aceleradora.socios.back.clases.socio.Categoria;
import aceleradora.socios.back.clases.socio.Etiqueta;
import aceleradora.socios.back.clases.socio.Socio;
import aceleradora.socios.back.dto.CategoriaDTO;
import aceleradora.socios.back.dto.EtiquetaDTO;
import aceleradora.socios.back.dto.SocioDTO;
import aceleradora.socios.back.repositorios.CategoriaRepository;
import aceleradora.socios.back.repositorios.SocioRepository;
import aceleradora.socios.back.services.SocioDTOService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SocioTests {

    @Autowired
    SocioDTOService socioDTOService;

    @Autowired
    SocioRepository socioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Test
    public void guardarSocioTest() {

        SocioDTO socio = new SocioDTO();
        socio.setNombre("socio prueba 1");
        socio.setPresidente("presidente socio prueba 1");
        socio.setTelefono((long) 113248934);
        socio.setEstado(true);
        socio.setMail("socioprueba1@gmail.com");
        socio.setCuit((long) 877543456);
        Optional<Categoria> categoria = categoriaRepository.findById(1L);
        socio.setCategoria(categoria.get());
        socio.setImagen("https://yt3.ggpht.com/-2LiinVgP1OQ/AAAAAAAAAAI/AAAAAAAAAAA/aTf4A6tMPbg/s900-c-k-no-mo-rj-c0xffffff/photo.jpg");
        socio.setWeb("Calle Arcor 123");
        Date date1 = Date.valueOf("2023-11-02");
        socio.setFechaUnion(date1);

        socioDTOService.guardarSocio(socio);

        List<SocioDTO> listaDeSocios2 = socioDTOService.obtenerListaDeSocios();

        // Before
        Assertions.assertEquals(socio.getNombre(), listaDeSocios2.get(listaDeSocios2.size() - 1).getNombre());
        Assertions.assertEquals(socio.getPresidente(), listaDeSocios2.get(listaDeSocios2.size() - 1).getPresidente());
        Assertions.assertEquals(socio.getTelefono(), listaDeSocios2.get(listaDeSocios2.size() - 1).getTelefono());
        Assertions.assertEquals(socio.getEstado(), listaDeSocios2.get(listaDeSocios2.size() - 1).getEstado());
        Assertions.assertEquals(socio.getMail(), listaDeSocios2.get(listaDeSocios2.size() - 1).getMail());
        Assertions.assertEquals(socio.getCuit(), listaDeSocios2.get(listaDeSocios2.size() - 1).getCuit());
        Assertions.assertEquals(socio.getCategoria().getNombre(), listaDeSocios2.get(listaDeSocios2.size() - 1).getCategoria().getNombre());
        Assertions.assertEquals(socio.getImagen(), listaDeSocios2.get(listaDeSocios2.size() - 1).getImagen());
        Assertions.assertEquals(socio.getWeb(), listaDeSocios2.get(listaDeSocios2.size() - 1).getWeb());
        Assertions.assertEquals(socio.getFechaUnion(), listaDeSocios2.get(listaDeSocios2.size() - 1).getFechaUnion());

    }

    @Test
    public void obtenerSocioPorNombreTest() {

        SocioDTO socio = new SocioDTO();
        socio.setNombre("socio prueba 2");
        socio.setPresidente("presidente socio prueba 2");
        socio.setTelefono((long) 113248934);
        socio.setEstado(true);
        socio.setMail("socioprueba2@gmail.com");
        socio.setCuit((long) 877543456);
        Optional<Categoria> categoria = categoriaRepository.findById(1L);
        socio.setCategoria(categoria.get());
        socio.setImagen("https://yt3.ggpht.com/-2LiinVgP1OQ/AAAAAAAAAAI/AAAAAAAAAAA/aTf4A6tMPbg/s900-c-k-no-mo-rj-c0xffffff/photo.jpg");
        socio.setWeb("Calle Cocacola 123");
        Date date1 = Date.valueOf("2023-11-02");
        socio.setFechaUnion(date1);

        socioDTOService.guardarSocio(socio);

        List<SocioDTO> listaDeSocios = socioDTOService.obtenerListaDeSocios();

        SocioDTO socioObtenido = socioDTOService.obtenerSocio("socio prueba 2");

        // Before
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getNombre(), socioObtenido.getNombre());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getPresidente(), socioObtenido.getPresidente());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getTelefono(), socioObtenido.getTelefono());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getEstado(), socioObtenido.getEstado());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getMail(), socioObtenido.getMail());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getCuit(), socioObtenido.getCuit());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getCategoria().getNombre(), socioObtenido.getCategoria().getNombre());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getImagen(), socioObtenido.getImagen());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getWeb(), socioObtenido.getWeb());
        Assertions.assertEquals(listaDeSocios.get(listaDeSocios.size() - 1).getFechaUnion(), socioObtenido.getFechaUnion());


    }


}
