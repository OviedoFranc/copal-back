package aceleradora.socios.back.controller;

import aceleradora.socios.back.services.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    @Autowired
    private ImagenService imagenesService;

    @PostMapping("/subirImgDpto/{id}")
    public ResponseEntity<String> subirImagenDepartamento(@PathVariable Long id, @RequestParam("imagen") MultipartFile archivo) {
        try {
            String ruta = imagenesService.guardarImagenDpto(archivo, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(ruta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo subir la imagen");
        }
    }

    @GetMapping("/obtenerImgDpto/{id}")
    public ResponseEntity<?> obtenerImagenDepartamento(@PathVariable Long id) {
        try {
            String nombreArchivo = "departamento_" + id;
            Resource imagen = imagenesService.obtenerImagen(nombreArchivo);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imagen no encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo obtener la imagen");
        }
    }

    @DeleteMapping("/eliminarImgDpto/{id}")
    public ResponseEntity<?> eliminarImagenDepartamento(@PathVariable Long id) {
        String nombreArchivo = "departamento_" + id;

        boolean eliminada = imagenesService.eliminarImagenSiExiste(nombreArchivo);

        if (eliminada) {
            return ResponseEntity.ok("Imagen eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La imagen no existe");
        }
    }

    @PostMapping("/subirImgSocio/{id}")
    public ResponseEntity<String> subirImagenSocio(@PathVariable Long id, @RequestParam("imagen") MultipartFile archivo) {
        try {
            String ruta = imagenesService.guardarImagenSocio(archivo, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(ruta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo subir la imagen");
        }
    }



    @GetMapping("/obtenerImgSocio/{id}")
    public ResponseEntity<?> obtenerImagenSocio(@PathVariable Long id) {
        try {
            String nombreArchivo = "socio_" + id;
            Resource imagen = imagenesService.obtenerImagen(nombreArchivo);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imagen no encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo obtener la imagen");
        }
    }


    @DeleteMapping("/eliminarImgSocio/{id}")
    public ResponseEntity<?> eliminarImagenSocio(@PathVariable Long id) {
        String nombreArchivo = "socio_" + id;

        boolean eliminada = imagenesService.eliminarImagenSiExiste(nombreArchivo);

        if (eliminada) {
            return ResponseEntity.ok("Imagen eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La imagen no existe");
        }
    }




}
