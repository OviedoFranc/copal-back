package aceleradora.socios.back.controller;

import aceleradora.socios.back.dto.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aceleradora.socios.back.dto.SocioDTO;
import aceleradora.socios.back.services.SocioDTOService;
import aceleradora.socios.back.services.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/altaUsuario")
    public ResponseEntity<UsuarioDTO> darDeAltaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO nuevoUsuario = userService.darDeAltaUsuario(usuarioDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/bajaUsuario/{id}")
    public ResponseEntity<Void> darDeBajaUsuario(@PathVariable Long id) {
        userService.darDeBajaUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        List<UsuarioDTO> aux = userService.traerTodos();
        return new ResponseEntity<>(aux, HttpStatus.OK);
        }

    @PutMapping("/actualizarUsuario/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuario){
        UsuarioDTO usuarioActualizado = userService.modificarUsuario(id,usuario);
        return Optional.ofNullable(usuarioActualizado).
                map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
