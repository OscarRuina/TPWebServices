package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionCuatrimestre;

import java.util.List;

public interface ICuatrimestreServicio {

    Cuatrimestre agregar(SolicitudModificacionCuatrimestre solicitudModificacionCuatrimestre);

    Cuatrimestre buscarId(long id);

    List<RespuestaCuatrimestre> listar();

    RespuestaCuatrimestre modificar(long id, SolicitudModificacionCuatrimestre cuatrimestre);

    String eliminar(long id);
}
