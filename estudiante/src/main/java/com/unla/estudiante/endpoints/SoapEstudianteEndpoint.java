package com.unla.estudiante.endpoints;

import com.unla.estudiante.servicios.SoapEstudianteServicio;
import com.unla.estudiante.soapestudiantes.RespuestaBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudModificacion;
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

}
