package aceleradora.socios.back.services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


    @Getter
    @Setter
    public class ListadoDeLocalidades {

        public int cantidad;
        public int total;
        public int inicio;
        public Parametro parametros;
        public List<Localidad> localidades;

        private class Parametro {
            public  List<String> campos;
            public  int max;
            public  List<String> municipio;
        }
    }

