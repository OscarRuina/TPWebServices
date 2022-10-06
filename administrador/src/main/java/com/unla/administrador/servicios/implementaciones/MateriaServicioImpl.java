package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionMateria;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioMateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioRepositorio;
import com.unla.administrador.servicios.interfaces.IMateriaServicio;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServicioImpl implements IMateriaServicio {

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private UsuarioMateriaRepositorio usuarioMateriaRepositorio;

    @Override
    public Materia Agregar(SolicitudModificacionMateria altaMateria) {
        Materia materia = new Materia();
        materia.setActivo(true);
        materia.setDia(altaMateria.getDia());
        materia.setAño(Integer.getInteger(altaMateria.getAño()));
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
    public List<Materia> listarPorIdCuatrimestre(long idCuatrimestre) {
        return materiaRepositorio.findByCuatrimestres(idCuatrimestre);
    }

    @Override
    public List<Materia> listar() {
        return materiaRepositorio.findAll();
    }

    @Override
    public Materia modificar(long id, SolicitudModificacionMateria modificacionMateria) {
        Materia materia = buscarId(id);
        materia.setAño(Integer.getInteger(modificacionMateria.getAño()));
        materia.setDia(modificacionMateria.getDia());
        materia.setCarrera(modificacionMateria.getCarrera());
        materia.setNombre(modificacionMateria.getNombre());
        //materia.setCuatrimestres(modificacionMateria.getCuatrimestres());
        //materia.setUsuarios(modificacionMateria.getUsuarios());
        materia.setHoraInicio(modificacionMateria.getHoraInicio());
        materia.setHoraFinalizacion(modificacionMateria.getHoraFinalizacion());

        List<Usuario> usuarios = usuarioRepositorio.findAll();
        UsuarioMateria profesor = usuarioMateriaRepositorio.findById(Long.parseLong(modificacionMateria.getIdProfesor())).orElseThrow();

        for (UsuarioMateria usuario:
             materia.getUsuarios()) {
            Long idUsuarioMateria = usuario.getId();
            Usuario usuarioMateria = usuarioRepositorio.findById(idUsuarioMateria).orElseThrow();
            //preguntar si asi es el rol
            if (usuarioMateria.getRol() == "Profesor"){
                int posicion = materia.getUsuarios().indexOf(usuario);
                materia.getUsuarios().remove(posicion);
                materia.getUsuarios().add(profesor);
            }
        }
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
