package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionCuatrimestre;

import java.util.List;

public interface ICuatrimestreServicio {

    Cuatrimestre agregar(Cuatrimestre cuatrimestre);

    Cuatrimestre buscarId(long id);

    List<Cuatrimestre> listar();

    Cuatrimestre modificar(Cuatrimestre cuatrimestre);

    String eliminar(long id);
}
