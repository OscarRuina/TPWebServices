package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Inscripcion;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudInscripcion;
import java.util.List;

public interface IInscripcionServicio {

    Inscripcion buscar(long id);

    Inscripcion registro(SolicitudInscripcion solicitudInscripcion);

    List<Inscripcion> listar();

}
