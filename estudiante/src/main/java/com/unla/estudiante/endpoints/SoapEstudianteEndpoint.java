package com.unla.estudiante.endpoints;

import com.unla.estudiante.servicios.SoapEstudianteServicio;
import com.unla.estudiante.soapestudiantes.Analitico;
import com.unla.estudiante.soapestudiantes.Materia;
import com.unla.estudiante.soapestudiantes.Materias;
import com.unla.estudiante.soapestudiantes.MesaExamen;
import com.unla.estudiante.soapestudiantes.MesasExamen;
import com.unla.estudiante.soapestudiantes.RespuestaBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaBajaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudBajaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudIdEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudListaMaterias;
import com.unla.estudiante.soapestudiantes.SolicitudMesaExamen;
import com.unla.estudiante.soapestudiantes.SolicitudMesasExamen;
import com.unla.estudiante.soapestudiantes.SolicitudModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudNombre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapEstudianteEndpoint {

    private static final String NOMBRE_URL = "http://www.unla.com/estudiante/soapEstudiantes";

    @Autowired
    private SoapEstudianteServicio servicio;

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudModificacion")
    @ResponsePayload
    public RespuestaModificacion modificacion(@RequestPayload SolicitudModificacion solicitudModificacion){
        return servicio.modificacion(solicitudModificacion);
    }

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudInscripcionMateriaEstudiante")
    @ResponsePayload
    public RespuestaInscripcionMateriaEstudiante inscripcionMateria(@RequestPayload
            SolicitudInscripcionMateriaEstudiante solicitudInscripcionMateriaEstudiante){
        return servicio.inscripcionMateria(solicitudInscripcionMateriaEstudiante);
    }

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudBajaInscripcionMateriaEstudiante")
    @ResponsePayload
    public RespuestaBajaInscripcionMateriaEstudiante bajaMateria(@RequestPayload
            SolicitudBajaInscripcionMateriaEstudiante solicitudBajaInscripcionMateriaEstudiante){
        return servicio.bajaMateria(solicitudBajaInscripcionMateriaEstudiante);
    }

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudInscripcionMesaExamenEstudiante")
    @ResponsePayload
    public RespuestaInscripcionMesaExamenEstudiante inscripcionMesaExamen(@RequestPayload
    SolicitudInscripcionMesaExamenEstudiante inscripcionMesaExamenEstudiante){
        return servicio.inscripcionMesaExamen(inscripcionMesaExamenEstudiante);
    }

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudBajaInscripcionMesaExamenEstudiante")
    @ResponsePayload
    public RespuestaBajaInscripcionMesaExamenEstudiante bajaMesaExamen(@RequestPayload
    SolicitudBajaInscripcionMesaExamenEstudiante bajaInscripcionMesaExamenEstudiante){
        return servicio.bajaMesaExamen(bajaInscripcionMesaExamenEstudiante);
    }
    @PayloadRoot(namespace = NOMBRE_URL, localPart = "SolicitudNombre")
    @ResponsePayload
    public Materia getMateria(@RequestPayload  SolicitudNombre nombre){
        return servicio.getMateria(nombre);
    }

    @PayloadRoot(namespace = NOMBRE_URL, localPart = "SolicitudListaMaterias")
    @ResponsePayload
    public Materias getMaterias(@RequestPayload SolicitudListaMaterias listaMaterias){
        return servicio.getMaterias(listaMaterias);
    }

    @PayloadRoot(namespace = NOMBRE_URL, localPart = "SolicitudMesaExamen")
    @ResponsePayload
    public MesaExamen getMesaExamen(@RequestPayload SolicitudMesaExamen mesa){
        return servicio.getMesaExamen(mesa);
    }

    @PayloadRoot(namespace = NOMBRE_URL, localPart = "SolicitudMesasExamen")
    @ResponsePayload
    public MesasExamen getMesasExamen(@RequestPayload SolicitudMesasExamen mesas){
        return servicio.getMesasExamen(mesas);
    }

    @PayloadRoot(namespace = NOMBRE_URL, localPart = "SolicitudIdEstudiante")
    @ResponsePayload
    public Analitico getAnalitico(@RequestPayload SolicitudIdEstudiante idEstudiante){
        return servicio.getAnalitico(idEstudiante);
    }

}
