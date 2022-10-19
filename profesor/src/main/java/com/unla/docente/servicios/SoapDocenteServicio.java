package com.unla.docente.servicios;

import com.unla.docente.modelos.datos.Usuario;
import com.unla.docente.modelos.datos.UsuarioMateria;
import com.unla.docente.repositorios.UsuarioRepositorio;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioMateriaRepositorio;
import com.unla.docente.soapdocentes.RespuestaMateriasAsignadas;
import com.unla.docente.soapdocentes.SolicitudMateriasAsignadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoapDocenteServicio {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private UsuarioMateriaRepositorio usuarioMateriaRepositorio;


    public RespuestaMateriasAsignadas traerMateriasAsignadas(SolicitudMateriasAsignadas solicitudMaterias){
        Usuario docente = repositorio.findById(solicitudMaterias.getId()).orElseThrow();
        List<UsuarioMateria> listaUsuarioMaterias = usuarioMateriaRepositorio.findByUsuario(docente);
        RespuestaMateriasAsignadas respuestaMateriasAsignadas = new RespuestaMateriasAsignadas();

        for (UsuarioMateria usuarioMateria:
             listaUsuarioMaterias) {
            RespuestaMateriasAsignadas.Item item = new RespuestaMateriasAsignadas.Item();
            item.setId(usuarioMateria.getMateria().getId());
            item.setNombre(usuarioMateria.getMateria().getNombre());
            item.setCarrera(usuarioMateria.getMateria().getCarrera());
            item.setAñoMateria(usuarioMateria.getMateria().getAñoMateria());
            item.setDia(usuarioMateria.getMateria().getDia());
            item.setAñoCuatrimestre(usuarioMateria.getMateria().getAñoCuatrimestre());
            item.setHoraInicio(usuarioMateria.getMateria().getHoraInicio());
            item.setHoraInicio(usuarioMateria.getMateria().getHoraInicio());
            respuestaMateriasAsignadas.getItem().add(item);
        }

        return respuestaMateriasAsignadas;
    }


}
