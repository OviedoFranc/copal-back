package aceleradora.socios.back.services;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.clases.espacio.Recurso;
import aceleradora.socios.back.dto.DepartamentoDTO;
import aceleradora.socios.back.repositorios.RecursoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecursoService {

    private final ModelMapper modelMapper;

    private final RecursoRepository recursoRepository;

    public RecursoService(ModelMapper modelMapper, RecursoRepository recursoRepository) {
        this.modelMapper = modelMapper;
        this.recursoRepository = recursoRepository;
    }

    public List<Recurso> findAll() {
        return recursoRepository.findAll();
    }

    public Optional<Recurso> findById(Long id) {
        Optional<Recurso> recursoOptional = recursoRepository.findById(id);
        if (recursoOptional.isPresent()) {
            return recursoOptional;
        } else {
            throw new EntityNotFoundException("No se encontró un recurso con id: " + id);
        }
    }

    public Recurso save(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public void deleteById(Long id) {
        Optional<Recurso> recursoOptional = recursoRepository.findById(id);
        if (recursoOptional.isPresent()) {
            recursoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No se encontró un recurso con id: " + id);
        }
    }


}
