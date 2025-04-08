package aceleradora.socios.back.controller;


import aceleradora.socios.back.services.georef.ServicioGeoref;
import aceleradora.socios.back.services.georef.entities.ListadoDeLocalidades;
import aceleradora.socios.back.services.georef.entities.ListadoDeMunicipios;
import aceleradora.socios.back.services.georef.entities.ListadoDeProvincias;
import aceleradora.socios.back.services.georef.entities.Localidad;
import aceleradora.socios.back.services.georef.entities.Municipio;
import aceleradora.socios.back.services.georef.entities.Provincia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RequestMapping("/api/provincia")
@RestController
public class UbicacionController {

    @Autowired
    private ServicioGeoref servicioGeoref;

    public UbicacionController(ServicioGeoref servicioGeoref) {
        this.servicioGeoref = servicioGeoref.instancia();
    }

    @GetMapping("/provinciasNombre")
    public ResponseEntity<List<String>> listadoProvincias() throws IOException {


        List<Provincia> provincias = servicioGeoref.listadoDeProvincias().provincias;
        List<String> nombresProvincias = provincias.stream()
                .map(Provincia::getNombre)
                .sorted()
                .collect(Collectors.toList());
        return new ResponseEntity<>(nombresProvincias, HttpStatus.OK);

    }

    @GetMapping("/provinciasCompletas")
    public ResponseEntity<ListadoDeProvincias> obtenerProvincias() throws IOException {
        ListadoDeProvincias provincias = servicioGeoref.listadoDeProvincias();
        return new ResponseEntity<>(provincias, HttpStatus.OK);
    }

    @GetMapping("/{id}/municipiosCompleto")
    public ResponseEntity<ListadoDeMunicipios> obtenerMunicipiosDeProvincia(@PathVariable Integer id) throws IOException {
        ListadoDeProvincias provincias = servicioGeoref.listadoDeProvincias();

        Optional<Provincia> provinciaEncontrada = provincias.provinciaDeId(id);

        if (provinciaEncontrada.isPresent()) {
            Provincia provinciaSeleccionada = provinciaEncontrada.get();
            ListadoDeMunicipios municipios = servicioGeoref.listadoDeMunicipiosDeProvincia(provinciaSeleccionada);
            return new ResponseEntity<>(municipios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/nombresMunicipio")
    public ResponseEntity<List<String>> obtenerNombresDeMunicipios(@RequestParam("nombre") String nombre) throws IOException {
        ListadoDeProvincias provincias = servicioGeoref.listadoDeProvincias();

        List<String> nombresDeMunicipios = new ArrayList<>();

        for (Provincia provincia : provincias.getProvincias()) {
            if (provincia.getNombre().equalsIgnoreCase(nombre)) {
                ListadoDeMunicipios municipios = servicioGeoref.listadoDeMunicipiosDeProvincia(provincia);
                for (Municipio municipio : municipios.getMunicipios()) {
                    nombresDeMunicipios.add(municipio.getNombre());
                }
                break;
            }
        }

        if (!nombresDeMunicipios.isEmpty()) {
            return new ResponseEntity<>(nombresDeMunicipios, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //TODO: REVISAR
   /* @GetMapping("/nombresLocalidades")
    public ResponseEntity<List<String>> obtenerNombresDeLocaldidades(@RequestParam("nombreProvincia") String nombreProvincia,@RequestParam("nombreMunicipio") String nombreMunicipio) throws IOException {
        ListadoDeProvincias provincias = servicioGeoref.listadoDeProvincias();

        List<String> nombresDeLocalidades = new ArrayList<>();


        for (Provincia provincia : provincias.getProvincias()) {
            if (provincia.getNombre().equalsIgnoreCase(nombreProvincia)) {
                ListadoDeMunicipios municipios = servicioGeoref.listadoDeMunicipiosDeProvincia(provincia);
                for(Municipio municipio : municipios.getMunicipios()){
                    if(municipio.getNombre().equalsIgnoreCase(nombreMunicipio)){
                        ListadoDeLocalidades localidades = servicioGeoref.listadoDeLocalidadesDeMunicipios(municipio);
                        for(Localidad localidad : localidades.getLocalidades()){
                            nombresDeLocalidades.add(localidad.getNombre());
                        }
                        break;
                    }

                }
                break; // Terminar la búsqueda después de encontrar la provincia
            }
        }

        if (!nombresDeLocalidades.isEmpty()) {
            return new ResponseEntity<>(nombresDeLocalidades, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @GetMapping("/nombresLocalidades")
    public ResponseEntity<List<String>> obtenerNombresDeLocaldidadesDeUnaProvincia(@RequestParam("nombreProvincia") String nombreProvincia) throws IOException {
        ListadoDeProvincias provincias = servicioGeoref.listadoDeProvincias();

        List<String> nombresDeLocalidades = new ArrayList<>();


        for (Provincia provincia : provincias.getProvincias()) {
            if (provincia.getNombre().equalsIgnoreCase(nombreProvincia)) {
                        ListadoDeLocalidades localidades = servicioGeoref.listadoDeLocalidadesDeMunicipios1(provincia);
                        for(Localidad localidad : localidades.getLocalidades()){
                            nombresDeLocalidades.add(localidad.getNombre());
                        }
                        break;
                    }

                }
        if (!nombresDeLocalidades.isEmpty()) {
            return new ResponseEntity<>(nombresDeLocalidades, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }









}





