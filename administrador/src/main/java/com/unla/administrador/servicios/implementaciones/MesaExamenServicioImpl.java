package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MesaExamen;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMesaExamen;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.repositorios.MesaExamenRepositorio;
import com.unla.administrador.servicios.interfaces.IMesaExamenServicio;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MesaExamenServicioImpl implements IMesaExamenServicio {

    @Autowired
    private MesaExamenRepositorio repositorio;

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Override
    public MesaExamen buscarId(long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Mesa de Examen no encontrado",
                        "404"
                )
        );

    }

    @Override
    public List<MesaExamen> listar(long materiaId) {
        return repositorio.findByMateria_IdAndActivoTrue(materiaId);
    }

    @Override
    public MesaExamen registrar(SolicitudRegistroMesaExamen registroMesaExamen) {
        MesaExamen mesaExamen = new MesaExamen();
        mesaExamen.setDia(registroMesaExamen.getDia());
        mesaExamen.setHora(registroMesaExamen.getHora());
        mesaExamen.setActivo(true);

        Materia materia = materiaRepositorio.findById(registroMesaExamen.getIdMateria()).orElseThrow();

        mesaExamen.setMateria(materia);

        return repositorio.save(mesaExamen);
    }

}
