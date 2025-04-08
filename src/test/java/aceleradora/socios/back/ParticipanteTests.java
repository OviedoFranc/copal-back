package aceleradora.socios.back;

import aceleradora.socios.back.clases.evento.Participante;
import aceleradora.socios.back.clases.evento.TipoParticipante;
import aceleradora.socios.back.dto.EventoDTO;
import aceleradora.socios.back.dto.ParticipanteDTO;
import aceleradora.socios.back.dto.ParticipantePostDTO;
import aceleradora.socios.back.dto.SocioDTO;
import aceleradora.socios.back.repositorios.ParticipanteRepository;
import aceleradora.socios.back.services.EventoService;
import aceleradora.socios.back.services.ParticipanteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ParticipanteTests {
    @Autowired
    ParticipanteRepository participanteRepository;

    @Autowired
    ParticipanteService participanteService;

    @Autowired
    EventoService eventoService;

    @Test
    public void agregarParticipanteAsociadoEventoTest(){

         ParticipantePostDTO participante = new ParticipantePostDTO();

         participante.setNombre("Nombre Participante 1");
         participante.setApellido("Apellido Participante 1");
         participante.setEmail("participante1@gmail.com");
         participante.setTipoParticipante(TipoParticipante.ASOCIADO);
         participante.setSocioAsociadoId(Optional.of((long) 4));

         List<EventoDTO> listaDeEventos = eventoService.traerTodos();
         Long ultimoEventoId = (long) listaDeEventos.size();

         List<ParticipanteDTO> listaDeParticipantes = participanteService.obtenerParticipantes(ultimoEventoId);

         participanteService.agregarParticipante(ultimoEventoId,participante);

         List<ParticipanteDTO> listaDeParticipantes2 = participanteService.obtenerParticipantes(ultimoEventoId);

         Assertions.assertEquals(participante.getNombre(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getNombre());
         Assertions.assertEquals(participante.getApellido(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getApellido());
         Assertions.assertEquals(participante.getTipoParticipante(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getTipoParticipante());
         Assertions.assertNotNull(listaDeParticipantes2);
         Assertions.assertEquals(listaDeParticipantes.size() +1, listaDeParticipantes2.size());

    }

    @Test
    public void agregarParticipanteInvitadoEventoTest(){

        ParticipantePostDTO participante = new ParticipantePostDTO();

        participante.setNombre("Nombre Participante 2");
        participante.setApellido("Apellido Participante 2");
        participante.setEmail("participante2@gmail.com");
        participante.setTipoParticipante(TipoParticipante.INVITADO);
        participante.setEntidadQueRepresenta(Optional.of("Entidad que representa el Participante 2"));
        participante.setSocioConvocanteId(Optional.of((long) 5));

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();
        Long ultimoEventoId = (long) listaDeEventos.size();

        List<ParticipanteDTO> listaDeParticipantes = participanteService.obtenerParticipantes(ultimoEventoId);

        participanteService.agregarParticipante(ultimoEventoId, participante);

        List<ParticipanteDTO> listaDeParticipantes2 = participanteService.obtenerParticipantes(ultimoEventoId);

        Assertions.assertEquals(participante.getNombre(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getNombre());
        Assertions.assertEquals(participante.getApellido(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getApellido());
        Assertions.assertEquals(participante.getTipoParticipante(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getTipoParticipante());
        Assertions.assertEquals(participante.getEntidadQueRepresenta().get(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getEntidadQueRepresenta());
        Assertions.assertNotNull(listaDeParticipantes2);
        Assertions.assertEquals(listaDeParticipantes.size() +1, listaDeParticipantes2.size());

    }

    @Test
    public void borrarParticipanteInvitadoTest(){

        ParticipantePostDTO participante = new ParticipantePostDTO();

        participante.setNombre("Nombre Participante 3");
        participante.setApellido("Apellido Participante 3");
        participante.setEmail("participante3@gmail.com");
        participante.setTipoParticipante(TipoParticipante.INVITADO);
        participante.setEntidadQueRepresenta(Optional.of("Entidad que representa el Participante 3"));
        participante.setSocioConvocanteId(Optional.of((long) 3));

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();
        Long ultimoEventoId = (long) listaDeEventos.size();

        participanteService.agregarParticipante(ultimoEventoId, participante);

        List<ParticipanteDTO> listaDeParticipantes = participanteService.obtenerParticipantes(ultimoEventoId);
        Long ultimoParticipanteId = listaDeParticipantes.get(listaDeParticipantes.size()-1).getId();

        participanteService.borrarParticipante(ultimoParticipanteId);

        List<ParticipanteDTO> listaDeParticipantes2 = participanteService.obtenerParticipantes(ultimoEventoId);

        // Before delete
        Assertions.assertEquals(participante.getNombre(),listaDeParticipantes.get(listaDeParticipantes.size()-1).getNombre());
        Assertions.assertEquals(participante.getApellido(), listaDeParticipantes.get(listaDeParticipantes.size()-1).getApellido());
        Assertions.assertEquals(participante.getTipoParticipante(), listaDeParticipantes.get(listaDeParticipantes.size()-1).getTipoParticipante());
        Assertions.assertEquals(participante.getEntidadQueRepresenta().get(), listaDeParticipantes.get(listaDeParticipantes.size()-1).getEntidadQueRepresenta());

        // After delete
        Assertions.assertNotNull(listaDeParticipantes2);
        Assertions.assertEquals(listaDeParticipantes.size()-1, listaDeParticipantes2.size());

    }

    @Test
    public void borrarParticipanteAsociadoTest(){

        ParticipantePostDTO participante = new ParticipantePostDTO();

        participante.setNombre("Nombre Participante 4");
        participante.setApellido("Apellido Participante 4");
        participante.setTipoParticipante(TipoParticipante.ASOCIADO);
        participante.setSocioAsociadoId(Optional.of((long) 2));

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();
        Long ultimoEventoId = (long) listaDeEventos.size();

        participanteService.agregarParticipante(ultimoEventoId, participante);

        List<ParticipanteDTO> listaDeParticipantes = participanteService.obtenerParticipantes(ultimoEventoId);
        Long ultimoParticipanteId = listaDeParticipantes.get(listaDeParticipantes.size()-1).getId();

        participanteService.borrarParticipante(ultimoParticipanteId);

        List<ParticipanteDTO> listaDeParticipantes2 = participanteService.obtenerParticipantes(ultimoEventoId);

        // Before delete
        Assertions.assertEquals(participante.getNombre(),listaDeParticipantes.get(listaDeParticipantes.size()-1).getNombre());
        Assertions.assertEquals(participante.getApellido(), listaDeParticipantes.get(listaDeParticipantes.size()-1).getApellido());
        Assertions.assertEquals(participante.getTipoParticipante(), listaDeParticipantes.get(listaDeParticipantes.size()-1).getTipoParticipante());

        // After delete
        Assertions.assertNotNull(listaDeParticipantes2);
        Assertions.assertEquals(listaDeParticipantes.size()-1, listaDeParticipantes2.size());

    }

    @Test
    public void agregarParticipantesTest() {

        List<ParticipanteDTO> participantes = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            ParticipanteDTO participante = new ParticipanteDTO();
            participante.setNombre("Nombre Participante 5_" + i);
            participante.setApellido("Apellido Participante 5_" + i);

            if (i % 2 == 0) {
                participante.setTipoParticipante(TipoParticipante.ASOCIADO);
                //participante.setSocioAsociadoId((long) 5);
                participante.setEmail(null);
            } else {
                participante.setTipoParticipante(TipoParticipante.INVITADO);
                participante.setEntidadQueRepresenta("Entidad que representa el Participante 5_" + i);
                participante.setSocioConvocanteId((long) 3);
                participante.setEmail("participante5_" + i + "@macmail.com");
            }

            participantes.add(participante);
        }

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();
        Long ultimoEventoId = (long) listaDeEventos.size();

        List<ParticipanteDTO> listaDeParticipantes = participanteService.obtenerParticipantes(ultimoEventoId);

        participanteService.agregarParticipantes(ultimoEventoId, participantes);

        List<ParticipanteDTO> listaDeParticipantes2 = participanteService.obtenerParticipantes(ultimoEventoId);

        int posicionParticipante = 0;
        int participanteAgregados = participantes.size();


        while (listaDeParticipantes2.size() != 0 && posicionParticipante< listaDeParticipantes2.size() - participanteAgregados) {
            Assertions.assertEquals(listaDeParticipantes.get(posicionParticipante).getNombre(), listaDeParticipantes2.get(posicionParticipante).getNombre());
            Assertions.assertEquals(listaDeParticipantes.get(posicionParticipante).getApellido(), listaDeParticipantes2.get(posicionParticipante).getApellido());
            Assertions.assertEquals(listaDeParticipantes.get(posicionParticipante).getTipoParticipante(), listaDeParticipantes2.get(posicionParticipante).getTipoParticipante());

            if (listaDeParticipantes2.get(posicionParticipante).getTipoParticipante().equals(TipoParticipante.INVITADO)) {
                Assertions.assertEquals(listaDeParticipantes.get(posicionParticipante).getEmail(), listaDeParticipantes2.get(posicionParticipante).getEmail());
                Assertions.assertEquals(listaDeParticipantes.get(posicionParticipante).getEntidadQueRepresenta(), listaDeParticipantes2.get(posicionParticipante).getEntidadQueRepresenta());
            } else {
                Assertions.assertEquals(listaDeParticipantes.get(posicionParticipante).getEmail(), listaDeParticipantes2.get(posicionParticipante).getEmail());
            }

            posicionParticipante++;
        }

        for(int k = 0; k < participanteAgregados; k++) {
            Assertions.assertEquals(participantes.get(k).getNombre(), listaDeParticipantes2.get(posicionParticipante).getNombre());
            Assertions.assertEquals(participantes.get(k).getApellido(), listaDeParticipantes2.get(posicionParticipante).getApellido());
            Assertions.assertEquals(participantes.get(k).getTipoParticipante(), listaDeParticipantes2.get(posicionParticipante).getTipoParticipante());

            if (listaDeParticipantes2.get(posicionParticipante).getTipoParticipante().equals(TipoParticipante.INVITADO)) {
                Assertions.assertEquals(participantes.get(k).getEntidadQueRepresenta(), listaDeParticipantes2.get(posicionParticipante).getEntidadQueRepresenta());
                Assertions.assertEquals(participantes.get(k).getEmail(), listaDeParticipantes2.get(posicionParticipante).getEmail());
            }
            else{
                    Assertions.assertEquals(participantes.get(k).getEmail(), listaDeParticipantes2.get(posicionParticipante).getEmail());
                }

            posicionParticipante++;
        }
    }

    @Test
    public void editarParticipanteTest(){
        ParticipantePostDTO participante = new ParticipantePostDTO();

        participante.setNombre("Nombre Participante 6");
        participante.setApellido("Apellido Participante 6");
        participante.setEmail("participante2@gmail.com");
        participante.setTipoParticipante(TipoParticipante.INVITADO);
        participante.setEntidadQueRepresenta(Optional.of("Entidad que representa el Participante 6"));
        participante.setSocioConvocanteId(Optional.of((long) 3));

        List<EventoDTO> listaDeEventos = eventoService.traerTodos();
        Long ultimoEventoId = (long) listaDeEventos.size();

        participanteService.agregarParticipante(ultimoEventoId, participante);

        List<ParticipanteDTO> listaDeParticipantes = participanteService.obtenerParticipantes(ultimoEventoId);
        Long ultimoParticipanteId = listaDeParticipantes.get(listaDeParticipantes.size()-1).getId();


        ParticipantePostDTO participanteEditado = new ParticipantePostDTO();

        participanteEditado.setNombre("Nombre Participante 6 Actualizado");
        participanteEditado.setApellido("Apellido Participante 6 Actualizado");
        participanteEditado.setEmail("participante2actualizado@gmail.com");
        participanteEditado.setTipoParticipante(TipoParticipante.INVITADO);
        participanteEditado.setEntidadQueRepresenta(Optional.of("Entidad que representa el Participante 6 Actualizado"));
        participanteEditado.setSocioConvocanteId(Optional.of((long) 3));

        participanteService.editar(ultimoParticipanteId, participanteEditado);

        List<ParticipanteDTO> listaDeParticipantes2 = participanteService.obtenerParticipantes(ultimoEventoId);

        // After update
        Assertions.assertEquals(participanteEditado.getNombre(),listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getNombre());
        Assertions.assertEquals(participanteEditado.getApellido(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getApellido());
        Assertions.assertEquals(participanteEditado.getTipoParticipante(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getTipoParticipante());
        if (listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getTipoParticipante().equals(TipoParticipante.INVITADO)) {
            Assertions.assertEquals(participanteEditado.getEntidadQueRepresenta().get(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getEntidadQueRepresenta());
            Assertions.assertEquals(participanteEditado.getEmail(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getEmail());
            Assertions.assertEquals(participante.getSocioConvocanteId().get(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getSocioConvocanteId());
        }
        else{
            Assertions.assertEquals(participanteEditado.getEmail(), listaDeParticipantes2.get(listaDeParticipantes2.size()-1).getEmail());
        }
    }

}


