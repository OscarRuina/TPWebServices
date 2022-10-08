package com.unla.administrador.convertidores;

import com.unla.administrador.configuraciones.swagger.controladores.CuatrimestreControlador;
import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateriaCuatrimestre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class CuatrimestreConvertidor {

    private CuatrimestreConvertidor(){

    }

    public static RespuestaCuatrimestre convertirRespuestaCuatrimestre(Cuatrimestre cuatrimestre){
        RespuestaCuatrimestre respuestaCuatrimestre = new RespuestaCuatrimestre();
        respuestaCuatrimestre.setId(cuatrimestre.getId());
        respuestaCuatrimestre.setComienzo(cuatrimestre.getComienzo().toString());
        respuestaCuatrimestre.setCierre(cuatrimestre.getCierre().toString());

        if(cuatrimestre.getMaterias().size() != 0) {
            List<RespuestaMateriaCuatrimestre> listaMaterias = new ArrayList<>();
            for (MateriaCuatrimestre materiaCuatrimestre :
                    cuatrimestre.getMaterias()) {
                listaMaterias.add(MateriaCuatrimestreConvertidor.convertirRespuestaMateriaCuatrimestre(materiaCuatrimestre));
            }
            respuestaCuatrimestre.setMaterias(listaMaterias);
        }
        return respuestaCuatrimestre;
    }
}
