package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaLogin;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroUsuario;

public final class UsuarioConvertidor {

    private UsuarioConvertidor() {
    }

    public static RespuestaLogin convertirRespuestaLogin(Usuario usuario) {
        RespuestaLogin dto = new RespuestaLogin();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setDni(usuario.getDni());
        dto.setEmail(usuario.getEmail());
        dto.setCarrera(usuario.getCarrera());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setPrimerLogin(usuario.isPrimerLogin());
        dto.setRol(usuario.getRol());
        return dto;
    }

    public static RespuestaRegistroUsuario convertirRespuestaRegistroUsuario(Usuario usuario) {
        RespuestaRegistroUsuario dto = new RespuestaRegistroUsuario();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setDni(usuario.getDni());
        dto.setEmail(usuario.getEmail());
        dto.setCarrera(usuario.getCarrera());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setPrimerLogin(usuario.isPrimerLogin());
        dto.setRol(usuario.getRol());
        return dto;
    }

}
