package com.unla.docente.endpoints;

import com.unla.docente.servicios.SoapDocenteServicio;
import com.unla.docente.soapdocentes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapDocenteEndpoint {

    private static final String NOMBRE_URL = "http://www.unla.com/docente/soapDocentes";

    @Autowired
    private SoapDocenteServicio servicio;

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudMateriasAsignadas")
    @ResponsePayload
    public RespuestaMateriasAsignadas traerMateriasAsignadas(@RequestPayload SolicitudMateriasAsignadas solicitudMateriasAsignadas){
        return servicio.traerMateriasAsignadas(solicitudMateriasAsignadas);
    }

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudAlumnosPorMateria")
    @ResponsePayload
    public RespuestaAlumnosPorMateria traerAlumnosPorMateria(@RequestPayload SolicitudAlumnosPorMateria solicitudAlumnosPorMateria){
        return servicio.traerAlumnosPorMateria(solicitudAlumnosPorMateria);
    }

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudAlumnosCursada")
    @ResponsePayload
    public RespuestaAlumnosCursada cargaNotasCursada(@RequestPayload SolicitudAlumnosCursada solicitudAlumnosCursada){
        return servicio.cargaNotasCursada(solicitudAlumnosCursada);
    }

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudAlumnosFinal")
    @ResponsePayload
    public RespuestaAlumnosFinal cargaNotasFinal(@RequestPayload SolicitudAlumnosFinal solicitudAlumnosFinal){
        return servicio.cargaNotasFinal(solicitudAlumnosFinal);
    }


}
