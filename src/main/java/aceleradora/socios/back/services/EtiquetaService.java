package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.socio.Etiqueta;
import aceleradora.socios.back.dto.DepartamentoDTO;
import aceleradora.socios.back.dto.DepartamentoPostDTO;
import aceleradora.socios.back.dto.EtiquetaDTO;
import aceleradora.socios.back.repositorios.AutoridadRepository;
import aceleradora.socios.back.repositorios.DepartamentoRepository;
import aceleradora.socios.back.repositorios.EtiquetaRepository;
import aceleradora.socios.back.repositorios.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaService {

    private final ModelMapper modelMapper;

    @Autowired
    EtiquetaRepository etiquetaRepository;
    public EtiquetaService(ModelMapper modelMapper, EtiquetaRepository etiquetaRepository) {
        this.modelMapper = modelMapper;
        this.etiquetaRepository = etiquetaRepository;

    }

    public Optional<Etiqueta> obtenerEtiquetaPorNombre(String nombre) {
        Optional<Etiqueta> categoria = etiquetaRepository.findByNombre(nombre);
        if(categoria.isPresent()){
            return etiquetaRepository.findByNombre(nombre);}

        else{throw new EntityNotFoundException("No se encontraron categorias");}
    }

    public List<Etiqueta> obtenerEtiquetasPorNombres(List<String> nombresCategorias) {
        return etiquetaRepository.findByNombreIn(nombresCategorias);
    }

    public EtiquetaDTO guardarEtiqueta(EtiquetaDTO etiquetaDTO){
        Optional<Etiqueta> etiquetaOptional = etiquetaRepository.findByNombre(etiquetaDTO.getNombre());

        if (etiquetaOptional.isPresent()) {
            throw new EntityExistsException("Ya existe la etiqueta");
        } else {
            Etiqueta etiqueta = modelMapper.map(etiquetaDTO, Etiqueta.class);
            etiquetaRepository.save(etiqueta);
            return modelMapper.map(etiqueta, EtiquetaDTO.class);
        }
    }

}
