package com.unla.administrador.controladores;


import com.unla.administrador.convertidores.MateriaConvertidor;
import com.unla.administrador.convertidores.UsuarioConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateriaEstudiante;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateriaEstudianteLista;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMateria;
import com.unla.administrador.servicios.interfaces.IMateriaServicio;
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
@RequestMapping("/api/materias")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Materia Controlador")
public class MateriaControlador {

    @Autowired
    private IMateriaServicio servicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creacion de Materia")
    public ResponseEntity<RespuestaRegistroMateria> crear(
            @Valid @RequestBody SolicitudRegistroMateria altaMateria) {
        return new ResponseEntity<>(
                MateriaConvertidor.convertirRespuestaMateria(
                        servicio.agregar(altaMateria)
                ), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modificacion Materia")
    public ResponseEntity<RespuestaRegistroMateria> modificacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id,
            @Valid @RequestBody SolicitudRegistroMateria modificacionMateria) {
        return new ResponseEntity<>(
                MateriaConvertidor.convertirRespuestaMateria(
                        servicio.modificar(Long.parseLong(id), modificacionMateria)
                ), HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminacion Materia")
    public ResponseEntity<String> eliminacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(servicio.eliminar(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista Materias")
    public ResponseEntity<List<RespuestaRegistroMateria>> listar() {
        List<RespuestaRegistroMateria> materias = new ArrayList<>();
        servicio.listar().forEach(
                materia -> {
                    materias.add(MateriaConvertidor.convertirRespuestaMateria(materia));
                }
        );
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar Materia")
    public ResponseEntity<RespuestaRegistroMateria> buscar(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(MateriaConvertidor.convertirRespuestaMateria(
                servicio.buscarId(Long.parseLong(id))
        ), HttpStatus.OK);

    }

    @GetMapping(value = "/pdf",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista Materias")
    public ResponseEntity<List<RespuestaRegistroMateria>> listarPdf(@RequestParam String turno) {
        List<RespuestaRegistroMateria> materias = new ArrayList<>();
        servicio.listarPdf(turno).forEach(
                materia -> {
                    materias.add(MateriaConvertidor.convertirRespuestaMateria(materia));
                }
        );
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/estudiantes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Alumnos por Materia")
    public ResponseEntity<List<RespuestaUsuarioMateriaEstudianteLista>> listarAlumnos(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        List<RespuestaUsuarioMateriaEstudianteLista> usuarioMaterias = new ArrayList<>();
        servicio.listarEstudiantes(Long.parseLong(id)).forEach(
                usuarioMateria -> {
                    usuarioMaterias.add(
                            UsuarioConvertidor.convertirRespuestaUsuarioMateriaEstudianteLista(usuarioMateria));
                }
        );
        return new ResponseEntity<>(usuarioMaterias, HttpStatus.OK);

    }

}
