package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMateria;

import java.util.List;

public interface IMateriaServicio {

    Materia agregar(SolicitudRegistroMateria altaMateria);

    Materia buscarId(long id);

    List<Materia> listar();

    Materia modificar(long id, SolicitudRegistroMateria solicitudRegistroMateria);

    String eliminar(long id);

}
