package com.unla.docente.endpoints;

import com.unla.docente.servicios.SoapDocenteServicio;
//import com.unla.docente.soapestudiantes.RespuestaModificacion;
//import com.unla.docente.soapestudiantes.SolicitudModificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

@Endpoint
public class SoapDocenteEndpoint {

    private static final String NOMBRE_URL = "http://www.unla.com/estudiante/soapDocentes";

    @Autowired
    private SoapDocenteServicio servicio;

    /*@PayloadRoot(namespace = NOMBRE_URL,localPart = "SolicitudModificacion")
    @ResponsePayload
    public RespuestaModificacion modificacion(@RequestPayload SolicitudModificacion solicitudModificacion){
        return servicio.modificacion(solicitudModificacion);
    }*/

}
