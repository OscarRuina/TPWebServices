package com.unla.administrador.controladores;

import com.unla.administrador.convertidores.UsuarioConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroUsuario;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateriaEstudiante;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudAsignarDocente;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;
import com.unla.administrador.servicios.interfaces.IUsuarioServicio;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<RespuestaRegistroUsuario> registro(
            @Valid @RequestBody SolicitudRegistroUsuario registroUsuario) {
        return new ResponseEntity<>(
                UsuarioConvertidor.convertirRespuestaRegistroUsuario(
                        servicio.registrar(registroUsuario)
                ), HttpStatus.CREATED);
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
                        servicio.modificar(Long.parseLong(id), modificacionUsuario)
                ), HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminacion Usuario")
    public ResponseEntity<String> eliminacion(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(servicio.eliminar(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista Usuarios por Rol")
    public ResponseEntity<List<RespuestaRegistroUsuario>> listar(@RequestParam String rol) {
        List<RespuestaRegistroUsuario> registroUsuarios = new ArrayList<>();
        servicio.listar(rol).forEach(
                usuario -> {
                    registroUsuarios.add(
                            UsuarioConvertidor.convertirRespuestaRegistroUsuario(usuario));
                }
        );
        return new ResponseEntity<>(registroUsuarios, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busqueda  Usuario")
    public ResponseEntity<RespuestaRegistroUsuario> buscar(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(
                UsuarioConvertidor.convertirRespuestaRegistroUsuario(
                        servicio.buscarId(Long.parseLong(id))
                ), HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}/materias", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Asigno Docente a Materia")
    public ResponseEntity<String> asignar(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id, @Valid @RequestBody
    SolicitudAsignarDocente asignarDocente) {
        return new ResponseEntity<>(
                servicio.asignarMateriaDocente(Long.parseLong(id), asignarDocente), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/materias", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listado de Materias del Docente")
    public ResponseEntity<List<RespuestaUsuarioMateria>> usuarioMaterias(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id
    ) {
        List<RespuestaUsuarioMateria> usuarioMaterias = new ArrayList<>();
        servicio.listarMaterias(Long.parseLong(id)).forEach(
                usuarioMateria -> {
                    usuarioMaterias.add(
                            UsuarioConvertidor.convertirRespuestaUsuarioMateria(usuarioMateria));
                }
        );
        return new ResponseEntity<>(usuarioMaterias, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/materiasEstudiante", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listado de Materias del Estudiante")
    public ResponseEntity<List<RespuestaUsuarioMateriaEstudiante>> usuarioMateriasEstudiante(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id
    ) {
        List<RespuestaUsuarioMateriaEstudiante> usuarioMaterias = new ArrayList<>();
        servicio.listarMateriasEstudiante(Long.parseLong(id)).forEach(
                usuarioMateria -> {
                    usuarioMaterias.add(
                            UsuarioConvertidor.convertirRespuestaUsuarioMateriaEstudiante(usuarioMateria));
                }
        );
        return new ResponseEntity<>(usuarioMaterias, HttpStatus.OK);
    }

}
