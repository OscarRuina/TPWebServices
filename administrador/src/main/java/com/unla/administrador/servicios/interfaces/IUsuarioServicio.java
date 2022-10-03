package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;

public interface IUsuarioServicio {

    Usuario buscarId(long id);

    Usuario login(SolicitudLogin solicitudLogin);

    Usuario registrar(SolicitudRegistroUsuario registroUsuario);

}
