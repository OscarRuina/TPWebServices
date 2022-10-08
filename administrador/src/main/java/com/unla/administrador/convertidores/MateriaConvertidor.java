package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMateria;

import java.util.ArrayList;
import java.util.List;

public final class MateriaConvertidor {

    private MateriaConvertidor() {
    }

    public static RespuestaRegistroMateria convertirRespuestaMateria(Materia materia){
        RespuestaRegistroMateria respuestaMateria = new RespuestaRegistroMateria();
        respuestaMateria.setId(materia.getId());
        respuestaMateria.setNombre(materia.getNombre());
        respuestaMateria.setCarrera(materia.getCarrera());
        respuestaMateria.setCuatrimestre(materia.getCuatrimestre());
        respuestaMateria.setAñoMateria(materia.getAñoMateria());
        respuestaMateria.setAñoCuatrimestre(materia.getAñoCuatrimestre());
        respuestaMateria.setDia(materia.getDia());
        respuestaMateria.setHoraInicio(materia.getHoraInicio());
        respuestaMateria.setHoraFinalizacion(materia.getHoraFinalizacion());
        respuestaMateria.setTurno(materia.getTurno());
        return respuestaMateria;
    }
}
