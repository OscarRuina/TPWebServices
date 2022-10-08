package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.convertidores.MateriaConvertidor;
import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMateria;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioMateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioRepositorio;
import com.unla.administrador.servicios.interfaces.IMateriaServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MateriaServicioImpl implements IMateriaServicio {

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Override
    public Materia agregar(SolicitudRegistroMateria altaMateria) {
        Materia materia = new Materia();
        materia.setActivo(true);
        materia.setDia(altaMateria.getDia());
        materia.setAño(altaMateria.getAño());
        materia.setNombre(altaMateria.getNombre());
        materia.setCarrera(altaMateria.getCarrera());
        materia.setHoraInicio(altaMateria.getHoraInicio());
        materia.setHoraFinalizacion(altaMateria.getHoraFinalizacion());
        return materiaRepositorio.save(materia);
    }

    @Override
    public Materia buscarId(long id) {
        return materiaRepositorio.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Materia no encontrada",
                        "404"
                )
        );
    }

    @Override
    public List<Materia> listar() {
        return  materiaRepositorio.findByActivoTrue();
    }

    @Override
    public Materia modificar(long id, SolicitudRegistroMateria modificacionMateria) {
        Materia materia = buscarId(id);
        materia.setAño(modificacionMateria.getAño());
        materia.setDia(modificacionMateria.getDia());
        materia.setCarrera(modificacionMateria.getCarrera());
        materia.setNombre(modificacionMateria.getNombre());
        materia.setHoraInicio(modificacionMateria.getHoraInicio());
        materia.setHoraFinalizacion(modificacionMateria.getHoraFinalizacion());
        return materiaRepositorio.save(materia);
    }

    @Override
    public String eliminar(long id) {
        Materia materia = buscarId(id);
        materia.setActivo(false);
        materiaRepositorio.save(materia);
        return "Materia eliminada correctamente";
    }
}
