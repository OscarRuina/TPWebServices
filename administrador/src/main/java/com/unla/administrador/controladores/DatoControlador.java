package com.unla.administrador.controladores;

import com.unla.administrador.modelos.dtos.respuesta.RespuestaDatos;
import com.unla.administrador.utilidades.enums.Carrera;
import com.unla.administrador.utilidades.enums.Cuatrimestre;
import com.unla.administrador.utilidades.enums.Dia;
import com.unla.administrador.utilidades.enums.Materia;
import com.unla.administrador.utilidades.enums.Turno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api")
@CrossOrigin(origins = {"*"}, maxAge = 3600)
@Tag(name = "Dato Controlador")
public class DatoControlador {

    @GetMapping(value = "/dias",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Dias")
    public ResponseEntity<List<RespuestaDatos>> dias(){
        List<RespuestaDatos> datos = new ArrayList<>();
        datos.add(new RespuestaDatos(Dia.LUNES.name()));
        datos.add(new RespuestaDatos(Dia.MARTES.name()));
        datos.add(new RespuestaDatos(Dia.MIERCOLES.name()));
        datos.add(new RespuestaDatos(Dia.JUEVES.name()));
        datos.add(new RespuestaDatos(Dia.VIERNES.name()));
        datos.add(new RespuestaDatos(Dia.SABADO.name()));
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @GetMapping(value = "/turnos",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Turnos")
    public ResponseEntity<List<RespuestaDatos>> turnos(){
        List<RespuestaDatos> datos = new ArrayList<>();
        datos.add(new RespuestaDatos(Turno.DIA.name()));
        datos.add(new RespuestaDatos(Turno.TARDE.name()));
        datos.add(new RespuestaDatos(Turno.NOCHE.name()));
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @GetMapping(value = "/carreras",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Carreras")
    public ResponseEntity<List<RespuestaDatos>> carreras(){
        List<RespuestaDatos> datos = new ArrayList<>();
        datos.add(new RespuestaDatos(Carrera.AUDIOVISION.name()));
        datos.add(new RespuestaDatos(Carrera.CIENCIA.name()));
        datos.add(new RespuestaDatos(Carrera.DISEÑO.name()));
        datos.add(new RespuestaDatos(Carrera.ECONOMIA.name()));
        datos.add(new RespuestaDatos(Carrera.EDUCACION.name()));
        datos.add(new RespuestaDatos(Carrera.ENFERMERIA.name()));
        datos.add(new RespuestaDatos(Carrera.GESTION.name()));
        datos.add(new RespuestaDatos(Carrera.JUSTICIA.name()));
        datos.add(new RespuestaDatos(Carrera.MUSICA.name()));
        datos.add(new RespuestaDatos(Carrera.NUTRICION.name()));
        datos.add(new RespuestaDatos(Carrera.PLANIFICACION.name()));
        datos.add(new RespuestaDatos(Carrera.SEGURIDAD.name()));
        datos.add(new RespuestaDatos(Carrera.SISTEMAS.name()));
        datos.add(new RespuestaDatos(Carrera.TRADUCTORADO.name()));
        datos.add(new RespuestaDatos(Carrera.TURISMO.name()));
        datos.add(new RespuestaDatos(Carrera.FERROVIARIAS.name()));
        datos.add(new RespuestaDatos(Carrera.INTERNACIONALES.name()));
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @GetMapping(value = "/cuatrimestres",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Cuatrimestres")
    public ResponseEntity<List<RespuestaDatos>> cuatrimestres(){
        List<RespuestaDatos> datos = new ArrayList<>();
        datos.add(new RespuestaDatos(Cuatrimestre.PRIMERO.name()));
        datos.add(new RespuestaDatos(Cuatrimestre.SEGUNDO.name()));
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

    @GetMapping(value = "/nombres-materias",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista de Nombres de Materias")
    public ResponseEntity<List<RespuestaDatos>> materias(){
        List<RespuestaDatos> datos = new ArrayList<>();
        datos.add(new RespuestaDatos(Materia.MATEMATICA.name()));
        datos.add(new RespuestaDatos(Materia.PROGRAMACION.name()));
        datos.add(new RespuestaDatos(Materia.ALGORITMOS.name()));
        datos.add(new RespuestaDatos(Materia.ARQUITECTURA.name()));
        datos.add(new RespuestaDatos(Materia.BASE_DE_DATOS.name()));
        datos.add(new RespuestaDatos(Materia.OBJETOS.name()));
        datos.add(new RespuestaDatos(Materia.INGENIERIA.name()));
        datos.add(new RespuestaDatos(Materia.SISTEMAS_OPERATIVOS.name()));
        datos.add(new RespuestaDatos(Materia.PROYECTO.name()));
        datos.add(new RespuestaDatos(Materia.REDES.name()));
        datos.add(new RespuestaDatos(Materia.ORGANIZACIONES.name()));
        datos.add(new RespuestaDatos(Materia.FUNDAMENTOS.name()));
        datos.add(new RespuestaDatos(Materia.SISTEMAS_DISTRIBUIDOS.name()));
        datos.add(new RespuestaDatos(Materia.PRUEBAS_DE_SOFTWARE.name()));
        return new ResponseEntity<>(datos, HttpStatus.OK);
    }

}
