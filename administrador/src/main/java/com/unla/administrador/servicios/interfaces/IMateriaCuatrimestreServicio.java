package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;

public interface IMateriaCuatrimestreServicio {

    MateriaCuatrimestre buscarId(long id);
    MateriaCuatrimestre crearRelacion(Materia materia, Cuatrimestre cuatrimestre, String turno);
}
