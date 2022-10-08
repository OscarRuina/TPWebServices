package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.repositorios.MateriaCuatrimestreRepositorio;
import com.unla.administrador.servicios.interfaces.IMateriaCuatrimestreServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MateriaCuatrimestreServicioImpl implements IMateriaCuatrimestreServicio {

    @Autowired
    private MateriaCuatrimestreRepositorio materiaCuatrimestreRepositorio;

    @Override
    public MateriaCuatrimestre buscarId(long id) {


        return materiaCuatrimestreRepositorio.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Relacion Materia Cuatrimestre no encontrada",
                        "404"
                )
        );
    }

    @Override
    public MateriaCuatrimestre crearRelacion(Materia materia, Cuatrimestre cuatrimestre, String turno) {
        MateriaCuatrimestre materiaCuatrimestre = new MateriaCuatrimestre();
        materiaCuatrimestre.setCuatrimestre(cuatrimestre);
        materiaCuatrimestre.setMateria(materia);
        materiaCuatrimestre.setTurno(turno);
        return materiaCuatrimestreRepositorio.save(materiaCuatrimestre);
    }
}
