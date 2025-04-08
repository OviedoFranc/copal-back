package aceleradora.socios.back.controller;

import aceleradora.socios.back.clases.evento.Participante;
import aceleradora.socios.back.dto.*;
import aceleradora.socios.back.services.EventoService;
import aceleradora.socios.back.services.ParticipanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participante")
public class ParticipanteController {

    @Autowired
    ParticipanteService participanteService;

    @PostMapping("/agregar/{eventoId}")
    public ResponseEntity<ParticipanteDTO> agregar(@PathVariable Long eventoId, @RequestBody ParticipantePostDTO participanteDTO) {
        ParticipanteDTO participante = participanteService.agregarParticipante(eventoId, participanteDTO);
        return new ResponseEntity<>(participante, HttpStatus.OK);
    }

    @PostMapping("fomulario/{codigo}")
    public ResponseEntity<ParticipantePostDTO> agregarParticipanteForm(
            @PathVariable String codigo,
            @RequestBody ParticipantePostDTO participanteDTO
    ) {
        ParticipantePostDTO participante = participanteService.agregarParticipanteForm(codigo, participanteDTO);
        return new ResponseEntity<>(participante, HttpStatus.OK);
    }

    @PostMapping("/agregarLista/{eventoId}")
    public ResponseEntity<EventoDTO> agregarParticipantes(
            @PathVariable Long eventoId,
            @RequestBody List<ParticipanteDTO> participanteDTOS
    ) {
        EventoDTO eventoActualizado = participanteService.agregarParticipantes(eventoId,participanteDTOS);
        return new ResponseEntity<>(eventoActualizado, HttpStatus.OK);
    }

    @PutMapping("/editar/{participanteID}")
    public ResponseEntity<Participante> editarParticipante(
            @PathVariable Long participanteID,
            @RequestBody ParticipantePostDTO participanteDTO
    ) {
        Participante p1 = participanteService.editar(participanteID, participanteDTO);
        return new ResponseEntity<>(p1, HttpStatus.OK);
    }

    @PutMapping("/editarParticipantes/{participanteID}")
    public ResponseEntity<EventoDTO> editarParticipantes(
           @PathVariable Long participanteID,
           @RequestBody List<ParticipanteDTO> participanteDTOS
    ) {
        EventoDTO eventoActualizado = participanteService.editarParticipantes(participanteID, participanteDTOS);
       return new ResponseEntity<>(eventoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/borrar/{participanteId}")
    public ResponseEntity<Void> borrarParticipante(
            @PathVariable Long participanteId
    ){
        participanteService.borrarParticipante(participanteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/obtenerParticipantes/{eventoId}")
    public ResponseEntity<List<ParticipanteDTO>> obtenerParticipantes(@PathVariable Long eventoId) {
        List<ParticipanteDTO> participantes = participanteService.obtenerParticipantes(eventoId);
        return new ResponseEntity<>(participantes, HttpStatus.OK);
    }

    @GetMapping("/codigoParticipante/{participanteId}")
    public ResponseEntity<String> obtenerCodPartidipante(@PathVariable Long participanteId){
        String codigo = participanteService.obtenerCodigoParticipante(participanteId);
        return new ResponseEntity<>(codigo, HttpStatus.OK);
    }


}
