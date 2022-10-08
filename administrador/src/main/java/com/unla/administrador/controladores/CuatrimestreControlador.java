package com.unla.administrador.controladores;

import com.unla.administrador.convertidores.CuatrimestreConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaCuatrimestreMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudAsignarMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroCuatrimestre;
import com.unla.administrador.servicios.interfaces.ICuatrimestreServicio;
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
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/cuatrimestres")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Cuatrimestre Controlador")
public class CuatrimestreControlador {

    @Autowired
    private ICuatrimestreServicio servicio;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creacion de Cuatrimestre")
    public ResponseEntity<RespuestaRegistroCuatrimestre> crear(
            @Valid @RequestBody SolicitudRegistroCuatrimestre cuatrimestre) {
        return new ResponseEntity<>(
                CuatrimestreConvertidor.convertirRespuestaCuatrimestre(
                        servicio.agregar(cuatrimestre)
                ), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modificacion Materia")
    public ResponseEntity<RespuestaRegistroCuatrimestre> modificacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody SolicitudRegistroCuatrimestre cuatrimestre) {
        return new ResponseEntity<>(
                CuatrimestreConvertidor.convertirRespuestaCuatrimestre(
                        servicio.modificar(Long.parseLong(id), cuatrimestre)
                ), HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminacion Cuatrimestre")
    public ResponseEntity<String> eliminacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(servicio.eliminar(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista Cuatrimestres")
    public ResponseEntity<List<RespuestaRegistroCuatrimestre>> listar() {
        List<RespuestaRegistroCuatrimestre> cuatrimestres = new ArrayList<>();
        servicio.listar().forEach(
                cuatrimestre -> {
                    cuatrimestres.add(
                            CuatrimestreConvertidor.convertirRespuestaCuatrimestre(cuatrimestre));
                }
        );
        return new ResponseEntity<>(cuatrimestres, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar Cuatrimestre")
    public ResponseEntity<RespuestaRegistroCuatrimestre> buscar(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id
    ) {
        return new ResponseEntity<>(
                CuatrimestreConvertidor.convertirRespuestaCuatrimestre(
                        servicio.buscarId(Long.parseLong(id))
                ), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/materias", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agregar Materia a Cuatrimestre")
    public ResponseEntity<String> agregarMateria(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody SolicitudAsignarMateria asignarMateria) {
        return new ResponseEntity<>(servicio.asignarMateria(Long.parseLong(id), asignarMateria), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/materias", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listado de Materias del Cuatrimestre")
    public ResponseEntity<List<RespuestaCuatrimestreMateria>> cuatrimestreMaterias(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id
    ) {
        List<RespuestaCuatrimestreMateria> cuatrimestreMaterias = new ArrayList<>();
        servicio.listarMaterias(Long.parseLong(id)).forEach(
                materiaCuatrimestre -> {
                    cuatrimestreMaterias.add(
                            CuatrimestreConvertidor.convertirRespuestaCuatrimestreMateria(
                                    materiaCuatrimestre));
                }
        );
        return new ResponseEntity<>(
                cuatrimestreMaterias, HttpStatus.OK
        );
    }

}
