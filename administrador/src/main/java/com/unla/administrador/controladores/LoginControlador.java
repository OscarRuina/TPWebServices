package com.unla.administrador.controladores;

import com.unla.administrador.convertidores.UsuarioConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaLogin;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudCambioContraseña;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.servicios.interfaces.IUsuarioServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Login Controlador")
public class LoginControlador {

    @Autowired
    private IUsuarioServicio servicio;

    @PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login de Usuario")
    public ResponseEntity<RespuestaLogin> login(@Valid @RequestBody SolicitudLogin solicitudLogin) {
        return new ResponseEntity<>(
                UsuarioConvertidor.convertirRespuestaLogin(
                        servicio.login(solicitudLogin)
                ), HttpStatus.OK);
    }

    @PutMapping(value = "/auth/primer-login/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Pedido de Cambio de Contraseña Usuario")
    public ResponseEntity<String> primerLogin(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody SolicitudCambioContraseña cambioContraseña) {
        return new ResponseEntity<>(
                servicio.primerLogin(Long.parseLong(id),cambioContraseña), HttpStatus.OK
        );
    }

    @GetMapping(value = "/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Salida del Sistema Usuario")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>(
                servicio.logout(), HttpStatus.OK
        );
    }

}
