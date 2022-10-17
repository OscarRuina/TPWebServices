package com.unla.estudiante.endpoints;

import com.unla.estudiante.servicios.SoapEstudianteModificacionServicio;
import com.unla.estudiante.soapestudiantes.RespuestaModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudModificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapEstudianteModificacionEndpoint {

    private static final String NOMBRE_URL = "http://www.unla.com/estudiante/soapEstudiantes";

    @Autowired
    private SoapEstudianteModificacionServicio servicio;

    @PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudModificacion")
    @ResponsePayload
    public RespuestaModificacion modificacion(@RequestPayload SolicitudModificacion solicitudModificacion){
        return servicio.modificacion(solicitudModificacion);
    }

}
