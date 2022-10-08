package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudAsignarMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroCuatrimestre;

import java.util.List;

public interface ICuatrimestreServicio {

    Cuatrimestre agregar(SolicitudRegistroCuatrimestre solicitudModificacionCuatrimestre);

    Cuatrimestre buscarId(long id);

    List<Cuatrimestre> listar();

    Cuatrimestre modificar(long id, SolicitudRegistroCuatrimestre cuatrimestre);

    String eliminar(long id);

    String asignarMateria( long id ,SolicitudAsignarMateria asignarMateria);

    List<MateriaCuatrimestre> listarMaterias(long idCuatrimestre);
}
