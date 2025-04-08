package aceleradora.socios.back.dto;

import aceleradora.socios.back.clases.evento.TipoParticipante;
import aceleradora.socios.back.clases.socio.Socio;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter@Setter
public class ParticipantePostDTO {

    private String nombre;

    private String apellido;

    private TipoParticipante tipoParticipante;

    //Si es ASOCIADO
    private Optional<Long> socioAsociadoId;

    //SI es INVITADO
    private Optional<Long> socioConvocanteId; //Socio de la empresa que invito a este participante

    private Optional<String> entidadQueRepresenta;

    private String email;

}
