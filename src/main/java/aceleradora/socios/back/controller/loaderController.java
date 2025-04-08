package aceleradora.socios.back.controller;

import aceleradora.socios.back.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loaderController {
  @Autowired
  AppService appService;
  @GetMapping("/cargaDeDatos")
  @ResponseStatus(HttpStatus.OK)
  public void cargarDatos() {
    appService.cargaDatos();
  }
}
