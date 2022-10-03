package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;
import com.unla.administrador.repositorios.UsuarioRepositorio;
import com.unla.administrador.servicios.interfaces.IUsuarioServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {


    private static final String PREFIJO = "ROLE_";
    private static final String CONTRASEÑA_TEMPORAL = "foo1234";

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

    @Override
    public Usuario registrar(SolicitudRegistroUsuario registroUsuario) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registroUsuario.getNombre());
        usuario.setApellido(registroUsuario.getApellido());
        usuario.setDni(registroUsuario.getDni());
        usuario.setEmail(registroUsuario.getEmail());
        usuario.setCarrera(registroUsuario.getCarrera());

        String nombreUsuario = registroUsuario.getNombre().toLowerCase() + registroUsuario.getApellido().toLowerCase();
        String contraseñaTemporal = CONTRASEÑA_TEMPORAL;
        String rol = PREFIJO + registroUsuario.getRol().toUpperCase();

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContraseña(contraseñaTemporal);
        usuario.setPrimerLogin(true);
        usuario.setActivo(true);
        usuario.setRol(rol);

        return repositorio.save(usuario);
    }
}
