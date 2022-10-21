package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudAsignarDocente;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudCambioContraseña;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;
import java.util.List;

public interface IUsuarioServicio {

    Usuario buscarId(long id);

    List<Usuario> listar(String rol);

    Usuario login(SolicitudLogin solicitudLogin);

    String primerLogin(long id,SolicitudCambioContraseña cambioContraseña);

    String logout();

    Usuario registrar(SolicitudRegistroUsuario registroUsuario);

    Usuario modificar(long id,SolicitudModificacionUsuario modificacionUsuario);

    String eliminar(long id);

    String asignarMateriaDocente(long id, SolicitudAsignarDocente asignarDocente);

    List<UsuarioMateria> listarMaterias(long idUsuario);

    List<UsuarioMateria> listarMateriasEstudiante(long idUsuario);

}
