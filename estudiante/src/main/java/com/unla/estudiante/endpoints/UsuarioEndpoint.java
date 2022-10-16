package com.unla.estudiante.endpoints;

import com.unla.estudiante.servicios.UsuarioServicio;
import com.unla.estudiante.soapestudiantes.RespuestaModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudModificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UsuarioEndpoint {

    private static final String NOMBRE = "http://www.unla.com/estudiante/soapEstudiantes";

    @Autowired
    private UsuarioServicio servicio;

    @PayloadRoot(namespace = NOMBRE,localPart = "SolicitudModificacion")
    @ResponsePayload
    public RespuestaModificacion modificacion(@RequestPayload SolicitudModificacion solicitudModificacion){
        return servicio.modificacion(solicitudModificacion);
    }

}
