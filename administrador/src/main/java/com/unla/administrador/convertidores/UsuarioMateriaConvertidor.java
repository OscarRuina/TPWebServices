package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateria;

public final class UsuarioMateriaConvertidor {
    private UsuarioMateriaConvertidor() {
    }

    public static RespuestaUsuarioMateria convertirRespuestaUsuarioMateria(UsuarioMateria usuarioMateria){
        RespuestaUsuarioMateria respuestaUsuarioMateria = new RespuestaUsuarioMateria();
        respuestaUsuarioMateria.setId(usuarioMateria.getId());
        respuestaUsuarioMateria.setNombre(usuarioMateria.getUsuario().getNombre());
        respuestaUsuarioMateria.setApellido(usuarioMateria.getUsuario().getApellido());
        respuestaUsuarioMateria.setDni(Integer.parseInt(usuarioMateria.getUsuario().getDni()));
        respuestaUsuarioMateria.setRol(usuarioMateria.getUsuario().getRol());
        respuestaUsuarioMateria.setNotaCursada(usuarioMateria.getNotaCursada());
        respuestaUsuarioMateria.setNotaParcial1(usuarioMateria.getNotaParcial1());
        respuestaUsuarioMateria.setNotaParcial2(usuarioMateria.getNotaParcial2());
        return respuestaUsuarioMateria;
    }
}
