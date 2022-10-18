package com.unla.estudiante.servicios;

import com.unla.estudiante.modelos.datos.Materia;
import com.unla.estudiante.modelos.datos.MesaExamen;
import com.unla.estudiante.modelos.datos.NotaFinal;
import com.unla.estudiante.modelos.datos.Usuario;
import com.unla.estudiante.modelos.datos.UsuarioMateria;
import com.unla.estudiante.repositorios.MateriaRepositorio;
import com.unla.estudiante.repositorios.MesaExamenRepositorio;
import com.unla.estudiante.repositorios.NotaFinalRepositorio;
import com.unla.estudiante.repositorios.UsuarioMateriaRepositorio;
import com.unla.estudiante.repositorios.UsuarioRepositorio;
import com.unla.estudiante.soapestudiantes.RespuestaBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaBajaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudBajaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudModificacion;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoapEstudianteServicio {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private UsuarioMateriaRepositorio usuarioMateriaRepositorio;

    @Autowired
    private MesaExamenRepositorio mesaExamenRepositorio;

    @Autowired
    private NotaFinalRepositorio notaFinalRepositorio;

    public RespuestaModificacion modificacion(SolicitudModificacion solicitudModificacion) {
        Usuario usuario = repositorio.findById(solicitudModificacion.getId()).orElseThrow();
        usuario.setEmail(solicitudModificacion.getEmail());
        usuario.setContraseña(solicitudModificacion.getPassword());
        repositorio.save(usuario);
        RespuestaModificacion respuestaModificacion = new RespuestaModificacion();
        respuestaModificacion.setModificado(true);
        return respuestaModificacion;
    }

    public RespuestaInscripcionMateriaEstudiante inscripcionMateria(
            SolicitudInscripcionMateriaEstudiante inscripcionMateriaEstudiante) {

        AtomicBoolean inscripto = new AtomicBoolean(true);

        Usuario estudiante = repositorio.findById(inscripcionMateriaEstudiante.getIdEstudiante())
                .orElseThrow();
        Materia materia = materiaRepositorio.findById(inscripcionMateriaEstudiante.getIdMateria())
                .orElseThrow();

        /** Verifico que no haya horarios superpuestos **/
        estudiante.getMaterias().forEach(usuarioMateria -> {
            if (usuarioMateria.getMateria().getHoraInicio().equals(materia.getHoraInicio())) {
                inscripto.set(false);
            }
        });

        if (inscripto.get()) {
            UsuarioMateria usuarioMateria = new UsuarioMateria();
            usuarioMateria.setUsuario(estudiante);
            usuarioMateria.setMateria(materia);
            usuarioMateria.setNotaParcial1(0);
            usuarioMateria.setNotaParcial2(0);
            usuarioMateria.setNotaCursada(0);
            usuarioMateria.setInscripto(true);

            estudiante.getMaterias().add(usuarioMateria);
            repositorio.save(estudiante);
        }
        RespuestaInscripcionMateriaEstudiante respuesta =
                new RespuestaInscripcionMateriaEstudiante();

        respuesta.setInscripto(inscripto.get());
        return respuesta;
    }

    public RespuestaBajaInscripcionMateriaEstudiante bajaMateria(
            SolicitudBajaInscripcionMateriaEstudiante bajaInscripcionMateriaEstudiante) {

        Usuario estudiante = repositorio.findById(
                bajaInscripcionMateriaEstudiante.getIdEstudiante()).orElseThrow();

        Materia materia = materiaRepositorio.findById(
                bajaInscripcionMateriaEstudiante.getIdMateria()).orElseThrow();

        UsuarioMateria usuarioMateria = usuarioMateriaRepositorio.findByMateria_IdAndUsuario_Id(
                materia.getId(), estudiante.getId());

        usuarioMateria.setInscripto(false);
        usuarioMateriaRepositorio.save(usuarioMateria);

        RespuestaBajaInscripcionMateriaEstudiante respuesta =
                new RespuestaBajaInscripcionMateriaEstudiante();
        respuesta.setDadoDeBaja(true);
        return respuesta;

    }

    public RespuestaInscripcionMesaExamenEstudiante inscripcionMesaExamen(
            SolicitudInscripcionMesaExamenEstudiante inscripcionMesaExamenEstudiante) {

        Usuario estudiante = repositorio.findById(inscripcionMesaExamenEstudiante.getIdEstudiante())
                .orElseThrow();

        MesaExamen mesaExamen = mesaExamenRepositorio.findById(
                inscripcionMesaExamenEstudiante.getIdMesaExamen()).orElseThrow();

        NotaFinal notaFinal = new NotaFinal();
        notaFinal.setEstudiante(estudiante);
        notaFinal.setMesaExamen(mesaExamen);
        notaFinal.setNotaExamen(0);
        notaFinal.setNotaFinal(0);
        notaFinal.setInscripto(true);
        notaFinal.setAprobado(false);

        notaFinalRepositorio.save(notaFinal);

        RespuestaInscripcionMesaExamenEstudiante respuesta =
                new RespuestaInscripcionMesaExamenEstudiante();
        respuesta.setInscripto(true);
        return respuesta;
    }

    public RespuestaBajaInscripcionMesaExamenEstudiante bajaMesaExamen(
            SolicitudBajaInscripcionMesaExamenEstudiante bajaInscripcionMesaExamenEstudiante) {

        NotaFinal notaFinal = notaFinalRepositorio.findByEstudiante_IdAndMesaExamen_Id(
                bajaInscripcionMesaExamenEstudiante.getIdEstudiante(),
                bajaInscripcionMesaExamenEstudiante.getIdMesaExamen());

        notaFinal.setInscripto(false);
        notaFinalRepositorio.save(notaFinal);

        RespuestaBajaInscripcionMesaExamenEstudiante respuesta =
                new RespuestaBajaInscripcionMesaExamenEstudiante();
        respuesta.setDadoDeBaja(true);
        return respuesta;

    }

}
