package com.unla.administrador.controladores;

import com.unla.administrador.convertidores.InscripcionConvertidor;
import com.unla.administrador.convertidores.MateriaConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaInscripcion;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudInscripcion;
import com.unla.administrador.servicios.interfaces.IInscripcionServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Inscripcion Controlador")
public class InscripcionesControlador {

    @Autowired
    private IInscripcionServicio servicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creacion de Inscripcion")
    public ResponseEntity<RespuestaInscripcion> crear(
            @Valid @RequestBody SolicitudInscripcion inscripcion) {
        return new ResponseEntity<>(
                InscripcionConvertidor.convertirRespuestaInscripcion(
                        servicio.registro(inscripcion)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar Inscripcion")
    public ResponseEntity<RespuestaInscripcion> buscar(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(InscripcionConvertidor.convertirRespuestaInscripcion(
                servicio.buscar(Long.parseLong(id))
        ), HttpStatus.OK);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista Inscripciones")
    public ResponseEntity<List<RespuestaInscripcion>> listar() {
        List<RespuestaInscripcion> inscripciones = new ArrayList<>();
        servicio.listar().forEach(
                inscripcion -> {
                    inscripciones.add(
                            InscripcionConvertidor.convertirRespuestaInscripcion(inscripcion));
                }
        );
        return new ResponseEntity<>(inscripciones, HttpStatus.OK);
    }

}
