package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.socio.Socio;
import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.repositorios.DepartamentoRepository;
import aceleradora.socios.back.repositorios.SocioRepository;
import jakarta.persistence.EntityNotFoundException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImagenService {

    @Autowired
    DepartamentoRepository departamentoRepository;

    @Autowired
    SocioRepository socioRepository;

    @Value("${PATH_IMAGENES}")
    private String path_imagenes;

    public String guardarImagenDpto(MultipartFile archivo, Long id) throws IOException {

        Optional<Departamento> departamentoOptional = departamentoRepository.findById(id);

        if (departamentoOptional.isPresent()) {
            String nombreArchivo = "departamento_" + id;
            Path ubicacionCopia = Paths.get(path_imagenes + File.separator + nombreArchivo + ".JPEG");

            Departamento dpto = departamentoOptional.get();
            dpto.setImagen(ubicacionCopia.toString());
            departamentoRepository.save(dpto);

            optimizarImagen(archivo, ubicacionCopia.toString());
            return ubicacionCopia.toString();

        } else {
            throw new EntityNotFoundException("No se encontró un departamento con id: " + id);
        }


    }

    public String guardarImagenSocio(MultipartFile archivo, Long id) throws IOException {

        Optional<Socio> socioOpt = socioRepository.findById(id);

        if (socioOpt.isPresent()) {
            String nombreArchivo = "socio_" + id;
            Path ubicacionCopia = Paths.get(path_imagenes + File.separator + nombreArchivo + ".JPEG");

            Socio socio = socioOpt.get();
            socio.setImagen(ubicacionCopia.toString());
            socioRepository.save(socio);

            optimizarImagen(archivo, ubicacionCopia.toString());
            return ubicacionCopia.toString();

        } else {
            throw new EntityNotFoundException("No se encontró un socio con id: " + id);
        }


    }

    private void optimizarImagen(MultipartFile archivo, String rutaDestino) throws IOException {
        Thumbnails.of(archivo.getInputStream())
                .size(640, 360)
                .outputFormat("JPEG")
                .outputQuality(0.75)
                .toFile(rutaDestino);
    }

    public Resource obtenerImagen(String nombreArchivo) {
        try {
            nombreArchivo = nombreArchivo.endsWith(".JPEG") ? nombreArchivo : nombreArchivo + ".JPEG";
            Path rutaArchivo = Paths.get(path_imagenes).resolve(nombreArchivo).normalize();
            Resource recurso = new UrlResource(rutaArchivo.toUri());
            if (recurso.exists()) {
                return recurso;
            } else {
                throw new RuntimeException("Archivo no encontrado: " + nombreArchivo);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error al buscar el archivo: " + nombreArchivo, ex);
        }
    }

    public void eliminarImagen(String nombreArchivo) {
        try {
            Path rutaParaEliminar = Paths.get(path_imagenes + File.separator + StringUtils.cleanPath(nombreArchivo));
            Files.deleteIfExists(rutaParaEliminar);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen", e);
        }
    }
    public boolean eliminarImagenSiExiste(String nombreArchivo) {
        try {
            Path rutaParaEliminar = Paths.get(path_imagenes + File.separator + nombreArchivo + ".JPEG");

            if (Files.exists(rutaParaEliminar)) {
                Files.delete(rutaParaEliminar);
                return true; // La imagen se eliminó con éxito
            } else {
                return false; // La imagen no existía
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen", e);
        }
    }







}
