package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRelacionMateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionMateria;

import java.util.List;

public interface IMateriaServicio {

    Materia Agregar(SolicitudModificacionMateria altaMateria);

    Materia buscarId(long id);

    List<Materia> listarPorIdCuatrimestre(long idCuatrimestre);

    List<RespuestaMateria> listar();

    Materia modificar(long id, SolicitudModificacionMateria solicitudModificacionMateria);

    String eliminar(long id);

}
