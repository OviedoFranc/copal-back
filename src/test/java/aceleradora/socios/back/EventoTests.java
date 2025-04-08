package aceleradora.socios.back;

import aceleradora.socios.back.clases.evento.EstadoEvento;
import aceleradora.socios.back.clases.evento.Modalidad;
import aceleradora.socios.back.dto.DepartamentoPostDTO;
import aceleradora.socios.back.dto.EventoDTO;
import aceleradora.socios.back.dto.EventoPostDTO;
import aceleradora.socios.back.dto.UbicacionDTO;
import aceleradora.socios.back.repositorios.EventoRepository;
import aceleradora.socios.back.services.EventoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@SpringBootTest
public class EventoTests {
    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    EventoService eventoService;

    @Test
    public void altaEventoTest(){

        EventoPostDTO evento = new EventoPostDTO();

        evento.setNombre("Evento Prueba 1");
        evento.setDepartamento("Normativa Alimentaria");
        evento.setEstado(new EstadoEvento("Activo"));
        evento.setModalidad(Modalidad.VIRTUAL);
        evento.setDescripcion("Descripción de evento 1. Lorem ipsum dolor sit amet. Et illum vitae id illo neque quo dolore reprehenderit ut rerum consequatur sit consequatur autem et asperiores doloremque. Aut mollitia nihil cum enim iusto ut perferendis dolores.");
        evento.setFechaInicio(LocalDate.now());
        evento.setFechaFin(LocalDate.now());
        evento.setHoraInicio(LocalTime.now());
        evento.setHoraFin(LocalTime.now());
        evento.setLinkReunion("Enlance a reunion");

        UbicacionDTO ubicacionEvento1 = new UbicacionDTO();
        ubicacionEvento1.setDireccion("Direccion 1");
        ubicacionEvento1.setLocalidad("Localidad 1");
        ubicacionEvento1.setPiso("Piso 1");
        ubicacionEvento1.setProvincia("Provincia 1");
        evento.setLugar(ubicacionEvento1);


        List<EventoDTO> listaDeEventos = eventoService.traerTodos();

        eventoService.altaEvento(evento);

        List<EventoDTO> listaDeEventos2 = eventoService.traerTodos();

        Assertions.assertEquals(listaDeEventos.size()+1, listaDeEventos2.size());
        Assertions.assertEquals(evento.getNombre(), listaDeEventos2.get(listaDeEventos2.size()-1).getNombre());
        Assertions.assertEquals(evento.getDescripcion(), listaDeEventos2.get(listaDeEventos2.size()-1).getDescripcion());
        Assertions.assertEquals(evento.getDepartamento(), listaDeEventos2.get(listaDeEventos2.size()-1).getDepartamento().getNombre());
        Assertions.assertEquals(evento.getEstado().getDescripcion(), listaDeEventos2.get(listaDeEventos2.size()-1).getEstado().getDescripcion());
        Assertions.assertEquals(evento.getLugar().getDireccion(), listaDeEventos2.get(listaDeEventos2.size()-1).getUbicacion().getDireccion());
        Assertions.assertEquals(evento.getLugar().getPiso(), listaDeEventos2.get(listaDeEventos2.size()-1).getUbicacion().getPiso());
        Assertions.assertEquals(evento.getLugar().getLocalidad(), listaDeEventos2.get(listaDeEventos2.size()-1).getUbicacion().getLocalidad());
        Assertions.assertEquals(evento.getLugar().getProvincia(), listaDeEventos2.get(listaDeEventos2.size()-1).getUbicacion().getProvincia());
        Assertions.assertEquals(evento.getLinkReunion(), listaDeEventos2.get(listaDeEventos2.size()-1).getLinkReunion());
    }
    @Test
    public void modificarEstadoEventoTest(){

        EventoPostDTO evento = new EventoPostDTO();

        evento.setNombre("Evento Prueba 2");
        evento.setDepartamento("Normativa Alimentaria");
        evento.setEstado(new EstadoEvento("Activo"));
        evento.setModalidad(Modalidad.PRESENCIAL);
        evento.setDescripcion("Descripción de evento 2. Lorem ipsum dolor sit amet. Et illum vitae id illo neque quo dolore reprehenderit ut rerum consequatur sit consequatur autem et asperiores doloremque. Aut mollitia nihil cum enim iusto ut perferendis dolores.");
        evento.setFechaInicio(LocalDate.now());
        evento.setFechaFin(LocalDate.now());
        evento.setHoraInicio(LocalTime.now());
        evento.setHoraFin(LocalTime.now());
        evento.setLinkReunion("Enlace a otra reunión");

        UbicacionDTO ubicacionEvento2 = new UbicacionDTO();
        ubicacionEvento2.setDireccion("Direccion 2");
        ubicacionEvento2.setLocalidad("Localidad 2");
        ubicacionEvento2.setPiso("Piso 2");
        ubicacionEvento2.setProvincia("Provincia 2");
        evento.setLugar(ubicacionEvento2);


        eventoService.altaEvento(evento);

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();

        eventoService.modificarEstadoEvento((long) listaDeEventos.size(), "Suspendido");

        List<EventoDTO> listaDeEventos2 = eventoService.traerTodos();

        Assertions.assertNotEquals(listaDeEventos.get(listaDeEventos.size()-1).getEstado().getDescripcion(), listaDeEventos2.get(listaDeEventos2.size()-1).getEstado().getDescripcion());
        Assertions.assertEquals("Suspendido", listaDeEventos2.get(listaDeEventos2.size()-1).getEstado().getDescripcion());


    }

    @Test
    public void editarEventoTest(){

        EventoPostDTO evento = new EventoPostDTO();

        evento.setNombre("Evento Prueba 3");
        evento.setDepartamento("Normativa Alimentaria");
        evento.setEstado(new EstadoEvento("Activo"));
        evento.setModalidad(Modalidad.VIRTUAL);
        evento.setDescripcion("Descripción de evento 3. Lorem ipsum dolor sit amet. Et illum vitae id illo neque quo dolore reprehenderit ut rerum consequatur sit consequatur autem et asperiores doloremque. Aut mollitia nihil cum enim iusto ut perferendis dolores.");
        evento.setFechaInicio(LocalDate.now());
        evento.setFechaFin(LocalDate.now());
        evento.setHoraInicio(LocalTime.now());
        evento.setHoraFin(LocalTime.now());
        evento.setLinkReunion("Enlace a reunión");

        UbicacionDTO ubicacionEvento3 = new UbicacionDTO();
        ubicacionEvento3.setDireccion("Dirección 3");
        ubicacionEvento3.setLocalidad("Localidad 3");
        ubicacionEvento3.setPiso("Piso 3");
        ubicacionEvento3.setProvincia("Provincia 3");
        evento.setLugar(ubicacionEvento3);

        eventoService.altaEvento(evento);

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();


        EventoDTO eventoEditado = new EventoDTO();

        eventoEditado.setNombre("Evento Prueba 3 actualizado");
        eventoEditado.setDescripcion("Descripcion evento 3 actualizada");
        eventoEditado.setModalidad(Modalidad.HIBRIDA);
        eventoEditado.setLinkReunion("Enlace a reunion actualizada");

        DepartamentoPostDTO dpto = new DepartamentoPostDTO();
        dpto.setNombre("Asuntos Laborales");
        eventoEditado.setDepartamento(dpto);

        eventoEditado.setEstado(new EstadoEvento("Activo"));
        eventoEditado.setFechaInicio(LocalDate.now());
        eventoEditado.setFechaFin(LocalDate.now());
        eventoEditado.setHoraInicio(LocalTime.now());
        eventoEditado.setHoraFin(LocalTime.now());

        UbicacionDTO ubicacionEventoModificado3 = new UbicacionDTO();
        ubicacionEventoModificado3.setDireccion("Dirección 3 Actualizado");
        ubicacionEventoModificado3.setLocalidad("Localidad 3 Actualizado");
        ubicacionEventoModificado3.setPiso("Piso 3 Actualizado");
        ubicacionEventoModificado3.setProvincia("Provincia 3 Actualizado");
        eventoEditado.setUbicacion(ubicacionEventoModificado3);

        eventoService.editarEvento((long) listaDeEventos.size(), eventoEditado);

        List<EventoDTO> listaDeEventos2 = eventoService.traerTodos();

        Assertions.assertNotEquals(listaDeEventos.get(listaDeEventos.size()-1).getNombre(), listaDeEventos2.get(listaDeEventos2.size()-1).getNombre());
        Assertions.assertNotEquals(listaDeEventos.get(listaDeEventos.size()-1).getDescripcion(), listaDeEventos2.get(listaDeEventos2.size()-1).getDescripcion());
        Assertions.assertNotEquals(listaDeEventos.get(listaDeEventos.size()-1).getModalidad(), listaDeEventos2.get(listaDeEventos2.size()-1).getModalidad());
        Assertions.assertNotEquals(listaDeEventos.get(listaDeEventos.size()-1).getLinkReunion(), listaDeEventos2.get(listaDeEventos2.size()-1).getLinkReunion());
        Assertions.assertEquals(eventoEditado.getNombre(), listaDeEventos2.get(listaDeEventos2.size()-1).getNombre());
        Assertions.assertEquals(eventoEditado.getDescripcion(), listaDeEventos2.get(listaDeEventos2.size() - 1).getDescripcion());
        Assertions.assertEquals(eventoEditado.getModalidad(), listaDeEventos2.get(listaDeEventos2.size() - 1).getModalidad());
        Assertions.assertEquals(eventoEditado.getLinkReunion(), listaDeEventos2.get(listaDeEventos2.size() - 1).getLinkReunion());

    }


    @Test
    public void eliminarEventoTest() {

        EventoPostDTO evento = new EventoPostDTO();

        evento.setNombre("Evento Prueba 4");
        evento.setDepartamento("Normativa Alimentaria");
        evento.setEstado(new EstadoEvento("Activo"));
        evento.setModalidad(Modalidad.VIRTUAL);
        evento.setDescripcion("Descripción de evento 4. Lorem ipsum dolor sit amet. Et illum vitae id illo neque quo dolore reprehenderit ut rerum consequatur sit consequatur autem et asperiores doloremque. Aut mollitia nihil cum enim iusto ut perferendis dolores.");
        evento.setFechaInicio(LocalDate.now());
        evento.setFechaFin(LocalDate.now());
        evento.setHoraInicio(LocalTime.now());
        evento.setHoraFin(LocalTime.now());
        evento.setLinkReunion("Enlace a reunión");

        UbicacionDTO ubicacionEvento4 = new UbicacionDTO();
        ubicacionEvento4.setDireccion("Dirección 4");
        ubicacionEvento4.setLocalidad("Localidad 4");
        ubicacionEvento4.setPiso("Piso 4");
        ubicacionEvento4.setProvincia("Provincia 4");
        evento.setLugar(ubicacionEvento4);

        eventoService.altaEvento(evento);

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();

        eventoService.eliminarEvento((long) listaDeEventos.size());

        List<EventoDTO> listaDeEventos2 = eventoService.traerTodos();

        Assertions.assertNotNull(listaDeEventos2);
        Assertions.assertNotEquals(listaDeEventos.size(), listaDeEventos2.size());
        Assertions.assertEquals(listaDeEventos.size() -1, listaDeEventos2.size());
    }

    @Test
    public void obtenerEventoTest() {

        EventoPostDTO evento = new EventoPostDTO();

        evento.setNombre("Evento Prueba 5");
        evento.setDepartamento("Normativa Alimentaria");
        evento.setEstado(new EstadoEvento("Activo"));
        evento.setModalidad(Modalidad.VIRTUAL);
        evento.setDescripcion("Descripción de evento 5. Lorem ipsum dolor sit amet. Et illum vitae id illo neque quo dolore reprehenderit ut rerum consequatur sit consequatur autem et asperiores doloremque. Aut mollitia nihil cum enim iusto ut perferendis dolores.");
        evento.setFechaInicio(LocalDate.now());
        evento.setFechaFin(LocalDate.now());
        evento.setHoraInicio(LocalTime.now());
        evento.setHoraFin(LocalTime.now());
        evento.setLinkReunion("Enlace a reunión");

        UbicacionDTO ubicacionEvento5 = new UbicacionDTO();
        ubicacionEvento5.setDireccion("Dirección 5");
        ubicacionEvento5.setLocalidad("Localidad 5");
        ubicacionEvento5.setPiso("Piso 5");
        ubicacionEvento5.setProvincia("Provincia 5");
        evento.setLugar(ubicacionEvento5);

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();

        eventoService.altaEvento(evento);

        List<EventoDTO> listaDeEventos2 = eventoService.traerTodos();


        Assertions.assertEquals(listaDeEventos.get(listaDeEventos.size() - 1).getId() + 1, listaDeEventos2.get(listaDeEventos2.size() - 1).getId());
        Assertions.assertEquals(evento.getNombre(), listaDeEventos2.get(listaDeEventos2.size()-1).getNombre());
        Assertions.assertEquals(evento.getLinkReunion(), listaDeEventos2.get(listaDeEventos2.size()-1).getLinkReunion());
        Assertions.assertEquals(evento.getEstado().getDescripcion(), listaDeEventos2.get(listaDeEventos2.size()-1).getEstado().getDescripcion());
        Assertions.assertEquals(evento.getDescripcion(), listaDeEventos2.get(listaDeEventos2.size()-1).getDescripcion());
    }
    @Test
    public void modalidadesDeEventoTest() {

        List <String> modalidades = eventoService.obtenerModalidadesEvento();

        Assertions.assertNotNull(modalidades);
        Assertions.assertEquals("PRESENCIAL", modalidades.get(0));
        Assertions.assertEquals("VIRTUAL", modalidades.get(1));
        Assertions.assertEquals("HIBRIDA", modalidades.get(2));
    }



}
