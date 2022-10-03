package com.unla.administrador.controladores;

import com.unla.administrador.convertidores.UsuarioConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaLogin;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudCambioContraseña;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Usuario Controlador")
public class UsuarioControlador {

    @Autowired
    private IUsuarioServicio servicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registro de Usuario")
    public ResponseEntity<RespuestaRegistroUsuario> registro(@Valid @RequestBody SolicitudRegistroUsuario registroUsuario) {
        return new ResponseEntity<>(
                UsuarioConvertidor.convertirRespuestaRegistroUsuario(
                        servicio.registrar(registroUsuario)
                ), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces =
                    MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modificacion Usuario")
    public ResponseEntity<RespuestaRegistroUsuario> modificacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody SolicitudModificacionUsuario modificacionUsuario) {
        return new ResponseEntity<>(
                UsuarioConvertidor.convertirRespuestaRegistroUsuario(
                        servicio.modificar(Long.parseLong(id),modificacionUsuario)
                ), HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminacion Usuario")
    public ResponseEntity<String> eliminacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(servicio.eliminar(Long.parseLong(id)),HttpStatus.OK);
    }

}
