package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudAsignarMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroCuatrimestre;
import com.unla.administrador.repositorios.CuatrimestreRepositorio;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.servicios.interfaces.ICuatrimestreServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuatrimestreServicioImpl implements ICuatrimestreServicio {

    @Autowired
    private CuatrimestreRepositorio cuatrimestreRepositorio;

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Override
    public Cuatrimestre agregar(SolicitudRegistroCuatrimestre altaCuatrimestre) {
        Cuatrimestre cuatrimestre = new Cuatrimestre();
        cuatrimestre.setNombre(altaCuatrimestre.getNombre());
        cuatrimestre.setComienzo(altaCuatrimestre.getComienzo());
        cuatrimestre.setCierre(altaCuatrimestre.getCierre());
        cuatrimestre.setActivo(true);
        return cuatrimestreRepositorio.save(cuatrimestre);
    }

    @Override
    public Cuatrimestre buscarId(long id) {
        return cuatrimestreRepositorio.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Cuatrimestre no encontrado",
                        "404"
                )
        );
    }

    @Override
    public List<Cuatrimestre> listar() {
        return cuatrimestreRepositorio.findByActivoTrue();
    }

    @Override
    public Cuatrimestre modificar(long id, SolicitudRegistroCuatrimestre cuatrimestre) {
        Cuatrimestre cuatrimestre1 = buscarId(id);
        cuatrimestre1.setNombre(cuatrimestre.getNombre());
        cuatrimestre1.setComienzo(cuatrimestre.getComienzo());
        cuatrimestre1.setCierre(cuatrimestre.getCierre());
        return cuatrimestreRepositorio.save(cuatrimestre1);
    }

    @Override
    public String eliminar(long id) {
        Cuatrimestre cuatrimestre = buscarId(id);
        cuatrimestre.setActivo(false);
        cuatrimestreRepositorio.save(cuatrimestre);
        return "Cuatrimestre Eliminado Correctamente";
    }

    @Override
    public String asignarMateria(long id,SolicitudAsignarMateria asignarMateria) {
        Cuatrimestre cuatrimestre = buscarId(id);

        Materia materia = materiaRepositorio.findById(asignarMateria.getIdMateria()).orElseThrow();

        MateriaCuatrimestre materiaCuatrimestre = new MateriaCuatrimestre();

        materiaCuatrimestre.setCuatrimestre(cuatrimestre);
        materiaCuatrimestre.setMateria(materia);
        materiaCuatrimestre.setTurno(asignarMateria.getTurno());

        cuatrimestre.getMaterias().add(materiaCuatrimestre);

        cuatrimestreRepositorio.save(cuatrimestre);

        return "Materia Agregada Correctamente";
    }

    @Override
    public List<MateriaCuatrimestre> listarMaterias(long idCuatrimestre) {
        Cuatrimestre cuatrimestre = buscarId(idCuatrimestre);
        return cuatrimestre.getMaterias();
    }
}
