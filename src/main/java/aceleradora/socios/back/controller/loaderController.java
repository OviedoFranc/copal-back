package aceleradora.socios.back.controller;

import aceleradora.socios.back.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cargaDatos")
public class loaderController {
  @Autowired
  AppService appService;
  @GetMapping()
  public ResponseEntity<String> cargaInicialDatos(){
    appService.cargaDatos();
    return ResponseEntity.status(HttpStatus.OK).body("Carga De Datos Exitosa");
  }
}
