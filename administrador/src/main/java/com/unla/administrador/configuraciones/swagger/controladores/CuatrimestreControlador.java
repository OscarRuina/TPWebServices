package com.unla.administrador.configuraciones.swagger.controladores;

import com.unla.administrador.convertidores.CuatrimestreConvertidor;
import com.unla.administrador.convertidores.MateriaCuatrimestreConvertidor;
import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRelacionMateriaCuatrimestre;
import com.unla.administrador.servicios.interfaces.ICuatrimestreServicio;
import com.unla.administrador.servicios.interfaces.IMateriaCuatrimestreServicio;
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
@RequestMapping("/api/cuatrimestres")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Cuatrimestre Controlador")
public class CuatrimestreControlador {

    @Autowired
    private ICuatrimestreServicio servicio;

    @Autowired
    private IMateriaCuatrimestreServicio materiaCuatrimestreServicio;

    @Autowired
    private IMateriaServicio materiaServicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creacion de Cuatrimestre")
    public ResponseEntity<RespuestaCuatrimestre> crear(@Valid @RequestBody SolicitudModificacionCuatrimestre cuatrimestre) {
        //RespuestaCuatrimestre respuestaCuatrimestre = servicio.agregar(cuatrimestre);
        Cuatrimestre cuatrimestreAgregado = servicio.agregar(cuatrimestre);
        List<MateriaCuatrimestre> materiaCuatrimestreList = new ArrayList<>();
        for (SolicitudRelacionMateriaCuatrimestre materia:
                cuatrimestre.getMaterias()) {
            Materia materiaAAgregar = materiaServicio.buscarId(Long.parseLong(materia.getIdMateria()));
            MateriaCuatrimestre mc = materiaCuatrimestreServicio.crearRelacion(materiaAAgregar, cuatrimestreAgregado, materia.getTurno());
            materiaCuatrimestreList.add(mc);
        }
        cuatrimestreAgregado.setMaterias(materiaCuatrimestreList);
        /*List<RespuestaMateriaCuatrimestre> materiasCuatrimestre = new ArrayList<>();
        for (MateriaCuatrimestre mcuatrimestre:
             materiaCuatrimestreList) {
            RespuestaMateriaCuatrimestre respuestaMateriaCuatrimestre =MateriaCuatrimestreConvertidor.convertirRespuestaMateriaCuatrimestre(mcuatrimestre);
            materiasCuatrimestre.add(respuestaMateriaCuatrimestre);
        }
        respuestaCuatrimestre.setMaterias(materiasCuatrimestre);*/
        RespuestaCuatrimestre respuestaCuatrimestre = CuatrimestreConvertidor.convertirRespuestaCuatrimestre(cuatrimestreAgregado);

        return new ResponseEntity<>(respuestaCuatrimestre, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces =
                    MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modificacion Materia")
    public ResponseEntity<RespuestaCuatrimestre> modificacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody SolicitudModificacionCuatrimestre cuatrimestre) {
        return new ResponseEntity<>(
                servicio.modificar(Long.parseLong(id), cuatrimestre), HttpStatus.OK
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
    public ResponseEntity<List<RespuestaCuatrimestre>> listar(){
        List<RespuestaCuatrimestre> cuatrimestres = servicio.listar();

        return new ResponseEntity<>(cuatrimestres,HttpStatus.OK);
    }

}
