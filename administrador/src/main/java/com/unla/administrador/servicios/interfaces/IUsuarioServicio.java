package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;

public interface IUsuarioServicio {

    Usuario buscarId(long id);

    Usuario login(SolicitudLogin solicitudLogin);

}
