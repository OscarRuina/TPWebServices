package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaCuatrimestreMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroCuatrimestre;

import java.util.ArrayList;


public final class CuatrimestreConvertidor {

    private CuatrimestreConvertidor() {

    }

    public static RespuestaRegistroCuatrimestre convertirRespuestaCuatrimestre(
            Cuatrimestre cuatrimestre) {
        RespuestaRegistroCuatrimestre respuestaCuatrimestre = new RespuestaRegistroCuatrimestre();
        respuestaCuatrimestre.setId(cuatrimestre.getId());
        respuestaCuatrimestre.setNombre(cuatrimestre.getNombre());
        respuestaCuatrimestre.setComienzo(cuatrimestre.getComienzo().toString());
        respuestaCuatrimestre.setCierre(cuatrimestre.getCierre().toString());
        return respuestaCuatrimestre;
    }

    public static RespuestaCuatrimestreMateria convertirRespuestaCuatrimestreMateria(
            MateriaCuatrimestre materiaCuatrimestre) {
        RespuestaCuatrimestreMateria cuatrimestreMateria = new RespuestaCuatrimestreMateria();
        cuatrimestreMateria.setId(materiaCuatrimestre.getId());
        cuatrimestreMateria.setNombreCuatrimestre(materiaCuatrimestre.getCuatrimestre().getNombre());
        cuatrimestreMateria.setNombreMateria(materiaCuatrimestre.getMateria().getNombre());
        cuatrimestreMateria.setTurno(materiaCuatrimestre.getTurno());
        return cuatrimestreMateria;
    }
}
