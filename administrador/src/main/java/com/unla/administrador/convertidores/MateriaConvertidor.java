package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateria;

import java.util.ArrayList;
import java.util.List;

public final class MateriaConvertidor {

    private MateriaConvertidor() {
    }

    public static RespuestaMateria convertirRespuestaMateria(Materia materia){
        RespuestaMateria respuestaMateria = new RespuestaMateria();
        respuestaMateria.setId(materia.getId());
        respuestaMateria.setNombre(materia.getNombre());
        respuestaMateria.setCarrera(materia.getCarrera());
        respuestaMateria.setAño(materia.getAño());
        respuestaMateria.setDia(materia.getDia());
        respuestaMateria.setHoraInicio(materia.getHoraInicio());
        respuestaMateria.setHoraFinalizacion(materia.getHoraFinalizacion());
        List<RespuestaUsuarioMateria> listaUsuarios = new ArrayList<>();
        for (UsuarioMateria userMateria:
             materia.getUsuarios()) {
            listaUsuarios.add(UsuarioMateriaConvertidor.convertirRespuestaUsuarioMateria(userMateria));
        }
        respuestaMateria.setListaUsuarios(listaUsuarios);
        return respuestaMateria;
    }
}
