package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMateria;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioMateriaRepositorio;
import com.unla.administrador.servicios.interfaces.IMateriaServicio;
import java.util.ArrayList;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServicioImpl implements IMateriaServicio {

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private UsuarioMateriaRepositorio usuarioMateriaRepositorio;

    @Override
    public Materia agregar(SolicitudRegistroMateria altaMateria) {
        Materia materia = new Materia();
        materia.setActivo(true);
        materia.setDia(altaMateria.getDia());
        materia.setAñoMateria(altaMateria.getAñoMateria());
        materia.setNombre(altaMateria.getNombre());
        materia.setCarrera(altaMateria.getCarrera());
        materia.setCuatrimestre(altaMateria.getCuatrimestre());
        materia.setAñoCuatrimestre(altaMateria.getAñoCuatrimestre());
        materia.setHoraInicio(altaMateria.getHoraInicio());
        materia.setHoraFinalizacion(altaMateria.getHoraFinalizacion());
        materia.setTurno(altaMateria.getTurno());
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
        materia.setAñoMateria(modificacionMateria.getAñoMateria());
        materia.setDia(modificacionMateria.getDia());
        materia.setCarrera(modificacionMateria.getCarrera());
        materia.setCuatrimestre(modificacionMateria.getCuatrimestre());
        materia.setAñoCuatrimestre(modificacionMateria.getAñoCuatrimestre());
        materia.setNombre(modificacionMateria.getNombre());
        materia.setHoraInicio(modificacionMateria.getHoraInicio());
        materia.setHoraFinalizacion(modificacionMateria.getHoraFinalizacion());
        materia.setTurno(modificacionMateria.getTurno());
        return materiaRepositorio.save(materia);
    }

    @Override
    public String eliminar(long id) {
        Materia materia = buscarId(id);
        materia.setActivo(false);
        materiaRepositorio.save(materia);
        return "Materia eliminada correctamente";
    }

    @Override
    public List<Materia> listarPdf(String turno) {
        return materiaRepositorio.findByActivoTrueAndTurnoOrderByAñoMateriaAsc(turno);
    }

    @Override
    public List<UsuarioMateria> listarEstudiantes(long id) {
        Materia materia = buscarId(id);
        List<UsuarioMateria> listaARetornar = new ArrayList<>();
        usuarioMateriaRepositorio.findByMateria_IdAndInscriptoTrue(materia.getId()).forEach(
                usuarioMateria -> {
                    if (usuarioMateria.getUsuario().getRol().equalsIgnoreCase("ROLE_ESTUDIANTE")){
                        listaARetornar.add(usuarioMateria);
                    }
                }
        );
        return listaARetornar;
    }
}
