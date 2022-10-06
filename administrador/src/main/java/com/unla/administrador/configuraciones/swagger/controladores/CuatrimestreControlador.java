package com.unla.administrador.configuraciones.swagger.controladores;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.servicios.interfaces.ICuatrimestreServicio;
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
@RequestMapping("/api/cuatrimestres")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Cuatrimestre Controlador")
public class CuatrimestreControlador {

    @Autowired
    private ICuatrimestreServicio servicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creacion de Cuatrimestre")
    public ResponseEntity<Cuatrimestre> crear(@Valid @RequestBody Cuatrimestre cuatrimestre) {
        return new ResponseEntity<>(
                servicio.agregar(cuatrimestre), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces =
                    MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modificacion Materia")
    public ResponseEntity<Cuatrimestre> modificacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody Cuatrimestre cuatrimestre) {
        return new ResponseEntity<>(
                servicio.modificar(cuatrimestre), HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminacion Cuatrimestre")
    public ResponseEntity<String> eliminacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(servicio.eliminar(Long.parseLong(id)),HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista Cuatrimestres")
    public ResponseEntity<List<Cuatrimestre>> listar(){
        List<Cuatrimestre> cuatrimestres = new ArrayList<>();
        servicio.listar().forEach(
                cuatrimestre ->  {
                    cuatrimestres.add(cuatrimestre);
                }
        );
        return new ResponseEntity<>(cuatrimestres,HttpStatus.OK);
    }

}
