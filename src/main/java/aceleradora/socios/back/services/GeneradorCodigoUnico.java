package aceleradora.socios.back.services;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class GeneradorCodigoUnico {
    public String generarCodigoUnico(long id) {
        UUID uuid = UUID.nameUUIDFromBytes(Long.toString(id).getBytes());
        return uuid.toString();
    }
}
