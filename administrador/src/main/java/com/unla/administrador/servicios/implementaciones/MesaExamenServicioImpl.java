package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.MesaExamen;
import com.unla.administrador.modelos.datos.NotaFinal;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroMesaExamen;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.repositorios.MesaExamenRepositorio;
import com.unla.administrador.repositorios.NotaFinalRepositorio;
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

    @Autowired
    private NotaFinalRepositorio notaFinalRepositorio;

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

    @Override
    public List<MesaExamen> listarActivas() {
        return repositorio.findByActivoTrue();
    }

    @Override
    public List<NotaFinal> listarAlumnosInscriptos(long id) {
        MesaExamen mesaExamen = buscarId(id);
        return notaFinalRepositorio.findByMesaExamen_IdAndInscriptoTrue(mesaExamen.getId());
    }

}
