package aceleradora.socios.back;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.espacio.EstadoReserva;
import aceleradora.socios.back.clases.espacio.Lugar;
import aceleradora.socios.back.clases.espacio.Recurso;
import aceleradora.socios.back.clases.espacio.Reserva;
import aceleradora.socios.back.dto.ReservaDTO;
import aceleradora.socios.back.repositorios.*;
import aceleradora.socios.back.services.ReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ReservaTest {

    @Autowired
    ReservaService reservaService;

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    DepartamentoRepository departamentoRepository;

    @Autowired
    EstadoReservaRepository estadoReservaRepository;

    @Autowired
    LugarRepository lugarRepository;

    @Autowired
    RecursoRepository recursoRepository;

    private Reserva reserva;
    @BeforeEach
    public void setup(){
        reserva = new Reserva();
        reserva.setNombre("Reserva test");
        reserva.setDescripcion("Reserva de prueba");

        Long id = 2L;
        Optional<Departamento> optionalDepartamento = departamentoRepository.findById(id);
        if (optionalDepartamento.isPresent()) {
            Departamento departamento = optionalDepartamento.get();
            reserva.setDepartamento(departamento);
        } else {
            throw new EntityNotFoundException("No se encontró el departamento con el ID especificado");
        }
        Optional<EstadoReserva> optionalEstadoReserva = estadoReservaRepository.findById(id);
        if (optionalEstadoReserva.isPresent()) {
            EstadoReserva estadoReserva = optionalEstadoReserva.get();
            reserva.setEstadoReserva(estadoReserva);
        } else {
            throw new EntityNotFoundException("No se encontró el estadoReserva con el ID especificado");
        }
        Optional<Lugar> optionalLugar = lugarRepository.findById(id);
        if (optionalLugar.isPresent()) {
            Lugar lugar = optionalLugar.get();
            reserva.setLugar(lugar);
        } else {
            throw new EntityNotFoundException("No se encontró el lugar con el ID especificado");
        }
        reserva.setHoraInicio(LocalTime.now());
        reserva.setHoraFin(LocalTime.now().plusHours(8L));

        Recurso recursos = recursoRepository.getReferenceById(3L);
        List<Recurso> listaRecursos = new ArrayList<>();
        listaRecursos.add(recursos);
        reserva.setRecursos(listaRecursos);
    }


    @Test
    public void crearReservaTest(){

        List<ReservaDTO> listaReserva = reservaService.listar();
        reservaRepository.save(reserva);
        List<ReservaDTO> listaReserva2 = reservaService.listar();

        Assertions.assertEquals(listaReserva.size()+1, listaReserva2.size());
        Assertions.assertEquals(reserva.getNombre(), listaReserva2.get(listaReserva2.size()-1).getNombre());
        Assertions.assertEquals(reserva.getDepartamento().getNombre(), listaReserva2.get(listaReserva2.size()-1).getDepartamento().getNombre());
        Assertions.assertEquals(reserva.getLugar().getNombre(), listaReserva2.get(listaReserva2.size()-1).getLugar().getNombre());
        Assertions.assertEquals("Netbook", listaReserva2.get(listaReserva2.size()-1).getRecursos().get(0).getNombre());
        Assertions.assertEquals(reserva.getHoraInicio().getHour(), listaReserva2.get(listaReserva2.size()-1).getHoraInicio().getHour());
        Assertions.assertEquals(reserva.getHoraFin().getHour(), listaReserva2.get(listaReserva2.size()-1).getHoraFin().getHour());
        Assertions.assertEquals(reserva.getEstadoReserva().getNombre(), listaReserva2.get(listaReserva2.size()-1).getEstadoReserva().getNombre());

    }

    @Test
    public void eliminarReservaTest(){

        reservaRepository.save(reserva);

        List<ReservaDTO> listaReserva = reservaService.listar();
        reservaService.eliminarReserva((long) listaReserva.size());
        List<ReservaDTO> listaReserva2 = reservaService.listar();

        // Before Delete
        Assertions.assertNotNull(listaReserva);
        Assertions.assertEquals(reserva.getNombre(), listaReserva.get(listaReserva.size()-1).getNombre());
        Assertions.assertEquals(reserva.getDescripcion(), listaReserva.get(listaReserva.size()-1).getDescripcion());
        Assertions.assertEquals(reserva.getDepartamento().getNombre(), listaReserva.get(listaReserva.size()-1).getDepartamento().getNombre());
        Assertions.assertEquals(reserva.getLugar().getNombre(), listaReserva.get(listaReserva.size()-1).getLugar().getNombre());
        Assertions.assertEquals("Netbook", listaReserva.get(listaReserva.size()-1).getRecursos().get(0).getNombre());
        Assertions.assertEquals(reserva.getHoraInicio().getHour(), listaReserva.get(listaReserva.size()-1).getHoraInicio().getHour());
        Assertions.assertEquals(reserva.getHoraFin().getHour(), listaReserva.get(listaReserva.size()-1).getHoraFin().getHour());
        Assertions.assertEquals(reserva.getEstadoReserva().getNombre(), listaReserva.get(listaReserva.size()-1).getEstadoReserva().getNombre());

        // After Delete
        Assertions.assertNotNull(listaReserva2);
        Assertions.assertEquals(listaReserva.size()-1, listaReserva2.size());

    }

    @Test
    public void modificarReservaTest(){

        reservaRepository.save(reserva);

        List<ReservaDTO> listaReserva = reservaService.listar();
        Long reservaId = (long)listaReserva.size();

        // Edit Reserva
        ReservaDTO reservaModificada = new ReservaDTO();
        reservaModificada.setNombre("Reserva test 3 actualizada");
        reservaModificada.setDescripcion("Reserva de prueba 3 actualizada");

        Long idMod = 1L;
        Optional<Departamento> optionalDepartamentoMod = departamentoRepository.findById(idMod);
        if (optionalDepartamentoMod.isPresent()) {
            Departamento departamento = optionalDepartamentoMod.get();
            reservaModificada.setDepartamento(departamento);
        } else {
            throw new EntityNotFoundException("No se encontró el departamento con el ID especificado");
        }
        Optional<EstadoReserva> optionalEstadoReservaMod = estadoReservaRepository.findById(idMod);
        if (optionalEstadoReservaMod.isPresent()) {
            EstadoReserva estadoReserva = optionalEstadoReservaMod.get();
            reservaModificada.setEstadoReserva(estadoReserva);
        } else {
            throw new EntityNotFoundException("No se encontró el estadoReserva con el ID especificado");
        }
        Optional<Lugar> optionalLugarMod = lugarRepository.findById(idMod);
        if (optionalLugarMod.isPresent()) {
            Lugar lugar = optionalLugarMod.get();
            reservaModificada.setLugar(lugar);
        } else {
            throw new EntityNotFoundException("No se encontró el lugar con el ID especificado");
        }

        reservaModificada.setHoraInicio(LocalTime.now());
        reservaModificada.setHoraFin(LocalTime.now().plusHours(7L));

        Recurso recursoModificado = recursoRepository.getReferenceById(2L);
        List<Recurso> listaRecursosMod = new ArrayList<>();
        listaRecursosMod.add(recursoModificado);
        reservaModificada.setRecursos(listaRecursosMod);

        reservaService.editarReserva(reservaId, reservaModificada);

        List<ReservaDTO> listaReserva2 = reservaService.listar();

        // Before Update
        Assertions.assertNotNull(listaReserva);
        Assertions.assertEquals(reserva.getNombre(), listaReserva.get(listaReserva.size()-1).getNombre());
        Assertions.assertEquals(reserva.getDescripcion(), listaReserva.get(listaReserva.size()-1).getDescripcion());
        Assertions.assertEquals(reserva.getDepartamento().getNombre(), listaReserva.get(listaReserva.size()-1).getDepartamento().getNombre());
        Assertions.assertEquals(reserva.getLugar().getNombre(), listaReserva.get(listaReserva.size()-1).getLugar().getNombre());
        Assertions.assertEquals("Netbook", listaReserva.get(listaReserva.size()-1).getRecursos().get(0).getNombre());
        Assertions.assertEquals(reserva.getHoraInicio().getHour(), listaReserva.get(listaReserva.size()-1).getHoraInicio().getHour());
        Assertions.assertEquals(reserva.getHoraFin().getHour(), listaReserva.get(listaReserva.size()-1).getHoraFin().getHour());
        Assertions.assertEquals(reserva.getEstadoReserva().getNombre(), listaReserva.get(listaReserva.size()-1).getEstadoReserva().getNombre());


        // After Update
        Assertions.assertNotNull(listaReserva2);
        Assertions.assertEquals(reservaModificada.getNombre(), listaReserva2.get(listaReserva2.size()-1).getNombre());
        Assertions.assertEquals(reservaModificada.getDepartamento().getNombre(), listaReserva2.get(listaReserva2.size()-1).getDepartamento().getNombre());
        Assertions.assertEquals(reservaModificada.getLugar().getNombre(), listaReserva2.get(listaReserva2.size()-1).getLugar().getNombre());
        Assertions.assertEquals(reservaModificada.getEstadoReserva().getNombre(), listaReserva2.get(listaReserva2.size()-1).getEstadoReserva().getNombre());
        Assertions.assertEquals(reservaModificada.getDescripcion(), listaReserva2.get(listaReserva2.size()-1).getDescripcion());
        Assertions.assertEquals("Pizarra", listaReserva2.get(listaReserva2.size()-1).getRecursos().get(0).getNombre());
    }


}
