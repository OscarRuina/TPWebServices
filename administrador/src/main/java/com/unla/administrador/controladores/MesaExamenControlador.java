package com.unla.administrador.controladores;

import com.unla.administrador.convertidores.MesaExamenConvertidor;
import com.unla.administrador.convertidores.UsuarioConvertidor;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMesaExamenNotasFinales;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMesaExamen;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateriaEstudianteLista;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMesaExamenEstudianteLista;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMesaExamen;
import com.unla.administrador.servicios.interfaces.IMesaExamenServicio;
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
@RequestMapping("/api/mesas-examen")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Mesa Examen Controlador")
public class MesaExamenControlador {

    @Autowired
    private IMesaExamenServicio mesaExamenServicio;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registro de Mesa de Examen")
    public ResponseEntity<RespuestaRegistroMesaExamen> registro(@Valid @RequestBody
    SolicitudRegistroMesaExamen registroMesaExamen) {
        return new ResponseEntity<>(
                MesaExamenConvertidor.convertirRespuestaMesaExamen(
                        mesaExamenServicio.registrar(registroMesaExamen)
                )
                , HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busqueda  Mesa de Examen")
    public ResponseEntity<RespuestaMesaExamenNotasFinales> buscar(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        return new ResponseEntity<>(
                MesaExamenConvertidor.convertirRespuestaMesaExamenNotasFinales(
                        mesaExamenServicio.buscarId(Long.parseLong(id))
                ), HttpStatus.OK);

    }

    @GetMapping(value = "/materia/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Mesa de Examen por Materia")
    public ResponseEntity<List<RespuestaRegistroMesaExamen>> listar(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id){
        List<RespuestaRegistroMesaExamen> registroMesaExamen = new ArrayList<>();
        mesaExamenServicio.listar(Long.parseLong(id)).forEach(
                mesaExamen -> {
                    registroMesaExamen.add(MesaExamenConvertidor.convertirRespuestaMesaExamen(mesaExamen));
                }
        );
        return new ResponseEntity<>(registroMesaExamen,HttpStatus.OK);
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Mesas de Examen Activas")
    public ResponseEntity<List<RespuestaRegistroMesaExamen>> listarActivas(){
        List<RespuestaRegistroMesaExamen> registroMesaExamen = new ArrayList<>();
        mesaExamenServicio.listarActivas().forEach(
                mesaExamen -> {
                    registroMesaExamen.add(MesaExamenConvertidor.convertirRespuestaMesaExamen(mesaExamen));
                }
        );
        return new ResponseEntity<>(registroMesaExamen,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/estudiantes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Alumnos por Mesa de Examen")
    public ResponseEntity<List<RespuestaUsuarioMesaExamenEstudianteLista>> listarAlumnosMesaExamen(
            @PathVariable("id") @Pattern(regexp = "[0-9]+") String id) {
        List<RespuestaUsuarioMesaExamenEstudianteLista> notasfinales = new ArrayList<>();
        mesaExamenServicio.listarAlumnosInscriptos(Long.parseLong(id)).forEach(
                notaFinal -> {
                    notasfinales.add(
                            MesaExamenConvertidor.convertirRespuestaUsuarioMesaExamenEstudianteLista(notaFinal));
                }
        );
        return new ResponseEntity<>(notasfinales, HttpStatus.OK);

    }

}
