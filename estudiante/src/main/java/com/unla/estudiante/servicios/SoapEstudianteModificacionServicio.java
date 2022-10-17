package com.unla.estudiante.servicios;

import com.unla.estudiante.modelos.datos.Usuario;
import com.unla.estudiante.repositorios.UsuarioRepositorio;
import com.unla.estudiante.soapestudiantes.RespuestaModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudModificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoapEstudianteModificacionServicio {

    @Autowired
    private UsuarioRepositorio repositorio;

    public RespuestaModificacion modificacion(SolicitudModificacion solicitudModificacion){
        Usuario usuario = repositorio.findById(solicitudModificacion.getId()).orElseThrow();
        usuario.setEmail(solicitudModificacion.getEmail());
        usuario.setContraseña(solicitudModificacion.getPassword());
        repositorio.save(usuario);
        RespuestaModificacion respuestaModificacion = new RespuestaModificacion();
        respuestaModificacion.setModificado(true);
        return respuestaModificacion;
    }

}
