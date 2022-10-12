package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Inscripcion;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudInscripcion;
import com.unla.administrador.repositorios.InscripcionRepositorio;
import com.unla.administrador.servicios.interfaces.IInscripcionServicio;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscripcionServicioImpl implements IInscripcionServicio {

    @Autowired
    private InscripcionRepositorio repositorio;

    @Override
    public Inscripcion buscar(long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Inscripcion no encontrada",
                        "404"
                )
        );
    }

    @Override
    public Inscripcion registro(SolicitudInscripcion solicitudInscripcion) {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setInicio(solicitudInscripcion.getInicio());
        inscripcion.setFin(solicitudInscripcion.getFin());
        inscripcion.setTipo(solicitudInscripcion.getTipo());
        return repositorio.save(inscripcion);
    }

    @Override
    public List<Inscripcion> listar() {
        return repositorio.findAll();
    }
}
