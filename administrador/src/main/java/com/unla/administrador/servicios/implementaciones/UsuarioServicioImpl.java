package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.repositorios.UsuarioRepositorio;
import com.unla.administrador.servicios.interfaces.IUsuarioServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Override
    public Usuario buscarId(long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Usuario no encontrado",
                        "404"
                )
        );
    }

    @Override
    public Usuario login(SolicitudLogin solicitudLogin) {
        Usuario usuario = repositorio.findByNombreUsuarioIgnoreCase(solicitudLogin.getNombreUsuario()).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Usuario no encontrado",
                        "404"
                )
        );
        if(!usuario.getContraseña().equals(solicitudLogin.getContraseña())){
            throw new RuntimeException("Contraseña incorrecta");
        }
        return usuario;
    }
}
