package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateriaCuatrimestre;

public final class MateriaCuatrimestreConvertidor {
    private MateriaCuatrimestreConvertidor(){

    }

    public static RespuestaMateriaCuatrimestre convertirRespuestaMateriaCuatrimestre(MateriaCuatrimestre materiaCuatrimestre){
        RespuestaMateriaCuatrimestre respuestaMateriaCuatrimestre = new RespuestaMateriaCuatrimestre();
        respuestaMateriaCuatrimestre.setId(materiaCuatrimestre.getId());
        respuestaMateriaCuatrimestre.setNombre(materiaCuatrimestre.getMateria().getNombre());
        respuestaMateriaCuatrimestre.setAño(materiaCuatrimestre.getMateria().getAño());
        respuestaMateriaCuatrimestre.setDia(materiaCuatrimestre.getMateria().getDia());
        respuestaMateriaCuatrimestre.setCarrera(materiaCuatrimestre.getMateria().getCarrera());
        respuestaMateriaCuatrimestre.setHoraInicio(materiaCuatrimestre.getMateria().getHoraInicio());
        respuestaMateriaCuatrimestre.setHoraFinalizacion(materiaCuatrimestre.getMateria().getHoraFinalizacion());
        respuestaMateriaCuatrimestre.setTurno(materiaCuatrimestre.getTurno());
        return respuestaMateriaCuatrimestre;
    }
}
