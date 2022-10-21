package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.MesaExamen;
import com.unla.administrador.modelos.datos.NotaFinal;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMesaExamen;
import java.util.List;

public interface IMesaExamenServicio {

    MesaExamen buscarId(long id);

    List<MesaExamen> listar(long materiaId);

    MesaExamen registrar(SolicitudRegistroMesaExamen registroMesaExamen);

    List<MesaExamen> listarActivas();

    List<NotaFinal> listarAlumnosInscriptos(long id);

}
