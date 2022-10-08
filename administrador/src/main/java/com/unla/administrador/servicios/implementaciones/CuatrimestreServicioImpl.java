package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.configuraciones.swagger.controladores.CuatrimestreControlador;
import com.unla.administrador.convertidores.CuatrimestreConvertidor;
import com.unla.administrador.convertidores.MateriaConvertidor;
import com.unla.administrador.modelos.datos.Cuatrimestre;
import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaCuatrimestre;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRelacionMateriaCuatrimestre;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionCuatrimestre;
import com.unla.administrador.repositorios.CuatrimestreRepositorio;
import com.unla.administrador.servicios.interfaces.ICuatrimestreServicio;
import com.unla.administrador.servicios.interfaces.IMateriaCuatrimestreServicio;
import com.unla.administrador.servicios.interfaces.IMateriaServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuatrimestreServicioImpl implements ICuatrimestreServicio {

    @Autowired
    private CuatrimestreRepositorio cuatrimestreRepositorio;

    @Autowired
    private IMateriaServicio materiaServicio;

    @Autowired
    private IMateriaCuatrimestreServicio materiaCuatrimestreServicio;

    @Override
    public Cuatrimestre agregar(SolicitudModificacionCuatrimestre altaCuatrimestre) {
        Cuatrimestre cuatrimestre = new Cuatrimestre();
        cuatrimestre.setComienzo(LocalDate.parse(altaCuatrimestre.getComienzo()));
        cuatrimestre.setCierre(LocalDate.parse(altaCuatrimestre.getCierre()));
        cuatrimestre.setActivo(true);
        /*for (SolicitudRelacionMateriaCuatrimestre materia:
                altaCuatrimestre.getMaterias()) {
            Materia materiaRelacion = materiaServicio.buscarId(Long.parseLong(materia.getIdMateria()));
            materiaCuatrimestreServicio.crearRelacion(materiaRelacion, cuatrimestre, materia.getTurno());
        }*/

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
    public List<RespuestaCuatrimestre> listar() {
        List<RespuestaCuatrimestre> cuatrimestres = new ArrayList<>();
        List<Cuatrimestre> cuatrimestresBD = cuatrimestreRepositorio.findAll();

        for (Cuatrimestre cuatrimestre:
                cuatrimestresBD) {
            cuatrimestres.add(CuatrimestreConvertidor.convertirRespuestaCuatrimestre(cuatrimestre));
        }
        return cuatrimestres;
    }

    @Override
    public RespuestaCuatrimestre modificar(long id, SolicitudModificacionCuatrimestre cuatrimestre) {

        Cuatrimestre cuatrimestre1 = buscarId(id);
        cuatrimestre1.setComienzo(LocalDate.parse(cuatrimestre.getComienzo()));
        cuatrimestre1.setCierre(LocalDate.parse(cuatrimestre.getCierre()));
        //cuatrimestre1.setActivo(cuatrimestre.isActivo());
        List<MateriaCuatrimestre> listaMaterias = new ArrayList<>();
        for (SolicitudRelacionMateriaCuatrimestre relacionMateriaCuatrimestre:
             cuatrimestre.getMaterias()) {
            MateriaCuatrimestre materiaAAgregar = materiaCuatrimestreServicio.buscarId(Long.parseLong(relacionMateriaCuatrimestre.getIdMateria()));
            listaMaterias.add(materiaAAgregar);
        }
        cuatrimestre1.setMaterias(listaMaterias);

        return CuatrimestreConvertidor.convertirRespuestaCuatrimestre(cuatrimestreRepositorio.save(cuatrimestre1));
    }

    @Override
    public String eliminar(long id) {
        Cuatrimestre cuatrimestre = buscarId(id);
        cuatrimestre.setActivo(false);
        cuatrimestreRepositorio.save(cuatrimestre);
        return "Cuatrimestre Eliminado Correctamente";
    }
}
