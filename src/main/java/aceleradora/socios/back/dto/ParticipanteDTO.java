package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.evento.TipoParticipante;
import aceleradora.socios.back.clases.socio.Socio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ParticipanteDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private TipoParticipante tipoParticipante;

    //Si es ASOCIADO
    private Socio socioAsociado;

    //SI es INVITADO
    private Long socioConvocanteId; //Socio de la empresa que invito a este participante

    private String entidadQueRepresenta;

    private String email;
}
