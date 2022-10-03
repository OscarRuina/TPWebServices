package com.unla.administrador.controladores;

import com.unla.administrador.convertidores.UsuarioConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaLogin;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;
import com.unla.administrador.servicios.interfaces.IUsuarioServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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

}
