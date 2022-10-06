package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.repositorios.CuatrimestreRepositorio;
import com.unla.administrador.servicios.interfaces.ICuatrimestreServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuatrimestreServicioImpl implements ICuatrimestreServicio {

    @Autowired
    private CuatrimestreRepositorio cuatrimestreRepositorio;

    @Override
    public Cuatrimestre agregar(Cuatrimestre cuatrimestre) {
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
        return cuatrimestreRepositorio.findAll();
    }

    @Override
    public Cuatrimestre modificar(Cuatrimestre cuatrimestre) {
        Cuatrimestre cuatrimestre1 = buscarId(cuatrimestre.getId());
        cuatrimestre1.setComienzo(cuatrimestre.getComienzo());
        cuatrimestre1.setCierre(cuatrimestre.getCierre());
        cuatrimestre1.setActivo(cuatrimestre.isActivo());
        cuatrimestre1.setMaterias(cuatrimestre.getMaterias());
        return cuatrimestreRepositorio.save(cuatrimestre1);
    }

    @Override
    public String eliminar(long id) {
        Cuatrimestre cuatrimestre = buscarId(id);
        cuatrimestre.setActivo(false);
        cuatrimestreRepositorio.save(cuatrimestre);
        return "Cuatrimestre Eliminado Correctamente";
    }
}
