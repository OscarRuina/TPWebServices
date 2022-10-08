package com.unla.administrador.configuraciones.swagger.controladores;

import com.unla.administrador.convertidores.UsuarioConvertidor;
import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;
import com.unla.administrador.servicios.interfaces.IMateriaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/materias")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Materia Controlador")
public class MateriaControlador {

    @Autowired
    private IMateriaServicio servicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creacion de Materia")
    public ResponseEntity<Materia> crear(@Valid @RequestBody SolicitudModificacionMateria altaMateria) {
        return new ResponseEntity<>(
                servicio.Agregar(altaMateria), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces =
                    MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modificacion Materia")
    public ResponseEntity<Materia> modificacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody SolicitudModificacionMateria modificacionMateria) {
        return new ResponseEntity<>(
                servicio.modificar(Long.parseLong(id), modificacionMateria), HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminacion Materia")
    public ResponseEntity<String> eliminacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(servicio.eliminar(Long.parseLong(id)),HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista Materias")
    public ResponseEntity<List<RespuestaMateria>> listar(){
        List<RespuestaMateria> materias = servicio.listar();

        return new ResponseEntity<>(materias,HttpStatus.OK);
    }
}
