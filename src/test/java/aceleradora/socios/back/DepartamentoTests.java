package aceleradora.socios.back;

import aceleradora.socios.back.clases.departamento.Departamento;
import aceleradora.socios.back.dto.DepartamentoDTO;
import aceleradora.socios.back.dto.DepartamentoPostDTO;
import aceleradora.socios.back.services.AutoridadService;
import aceleradora.socios.back.services.DepartamentoService;
import org.assertj.core.api.Assert;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DepartamentoTests {

    @Autowired
    DepartamentoService departamentoService;

    @Autowired
    AutoridadService autoridadService;

    @Test
    public void altaDepartamentoTest(){

        DepartamentoPostDTO departamento = new DepartamentoPostDTO();
        departamento.setNombre("departamento 1 ");
        departamento.setObjetivo("Objetivo departamento 1");

        List<DepartamentoDTO> listaDeDepartamentos = departamentoService.traerTodos();

        departamentoService.altaDepartamento(departamento);

        List<DepartamentoDTO> listaDeDepartamentos2 = departamentoService.traerTodos();

        Assertions.assertEquals(listaDeDepartamentos.size()+1, listaDeDepartamentos2.size());
        Assertions.assertEquals(departamento.getNombre(), listaDeDepartamentos2.get(listaDeDepartamentos2.size()-1).getNombre());

    }

    @Test
    public void bajaDepartamentoTest(){

        DepartamentoPostDTO departamento = new DepartamentoPostDTO();
        departamento.setNombre("departamento 2");
        departamento.setObjetivo("Objetivo departamento 2");

        departamentoService.altaDepartamento(departamento);

        List<DepartamentoDTO> listaDeDepartamentos = departamentoService.traerTodos();
        long ultimoId = listaDeDepartamentos.size();

        departamentoService.bajaDepartamento(ultimoId);

        DepartamentoDTO dpto0btenido = departamentoService.obtenerDepartamento(ultimoId);

        Assertions.assertEquals(false, dpto0btenido.getEstaActivo());

    }

    @Test
    public void editarDepartamentoTest(){
        DepartamentoPostDTO departamento = new DepartamentoPostDTO();
        departamento.setNombre("departamento 3");
        departamento.setObjetivo("Objetivo departamento 3");

        departamentoService.altaDepartamento(departamento);

        List<DepartamentoDTO> listaDeDepartamentos = departamentoService.traerTodos();
        long ultimoId = listaDeDepartamentos.size();

        DepartamentoPostDTO departamentoEditado = new DepartamentoPostDTO();
        departamentoEditado.setNombre("departamento 3 actualizado");
        departamentoEditado.setObjetivo("Objetivo departamento 3 actualizado");

        DepartamentoDTO dptoEditado = departamentoService.editarDepartamento(ultimoId, departamentoEditado);

        Assertions.assertNotEquals(departamento.getNombre(), dptoEditado.getNombre());
        Assertions.assertNotEquals(departamento.getObjetivo(), dptoEditado.getObjetivo());
        Assertions.assertEquals(departamentoEditado.getNombre(),dptoEditado.getNombre());
        Assertions.assertEquals(departamentoEditado.getObjetivo(), dptoEditado.getObjetivo());

    }

    @Test
    public void obtenerDepartamentoTest(){
        DepartamentoPostDTO departamento = new DepartamentoPostDTO();
        departamento.setNombre("departamento 4");
        departamento.setObjetivo("Objetivo departamento 4");

        departamentoService.altaDepartamento(departamento);

        List<DepartamentoDTO> listaDeDepartamentos = departamentoService.traerTodos();
        long ultimoId = listaDeDepartamentos.size();

        DepartamentoDTO dptoObtenido = departamentoService.obtenerDepartamento(ultimoId);

        Assertions.assertEquals(departamento.getNombre(), dptoObtenido.getNombre());
        Assertions.assertEquals(departamento.getObjetivo(), dptoObtenido.getObjetivo());
        Assertions.assertEquals(true, dptoObtenido.getEstaActivo());

    }

    @Test
    public void obtenerNombresDeDepartamentosTest(){

        DepartamentoPostDTO departamento = new DepartamentoPostDTO();
        departamento.setNombre("departamento 5");
        departamento.setObjetivo("Objetivo departamento 5");

        departamentoService.altaDepartamento(departamento);

        List<DepartamentoDTO> listaDeDepartamentos = departamentoService.traerTodos();
        List<String> nombreDptosObtenido = departamentoService.obtenerNombres();

        long ultimoId = listaDeDepartamentos.size();
        while (ultimoId != 0){
            DepartamentoDTO dptoObtenido = departamentoService.obtenerDepartamento(ultimoId);

            Assertions.assertEquals(nombreDptosObtenido.get((int)ultimoId -1), dptoObtenido.getNombre());

            ultimoId = ultimoId - 1;
        }

    }



}
