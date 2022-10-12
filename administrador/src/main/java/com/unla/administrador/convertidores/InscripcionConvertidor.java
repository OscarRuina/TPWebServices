package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.Inscripcion;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaInscripcion;

public final class InscripcionConvertidor {

    private InscripcionConvertidor(){}

    public static RespuestaInscripcion convertirRespuestaInscripcion(Inscripcion inscripcion){
        RespuestaInscripcion dto = new RespuestaInscripcion();
        dto.setId(inscripcion.getId());
        dto.setInicio(inscripcion.getInicio());
        dto.setFin(inscripcion.getFin());
        dto.setTipo(inscripcion.getTipo());
        return dto;
    }

}
