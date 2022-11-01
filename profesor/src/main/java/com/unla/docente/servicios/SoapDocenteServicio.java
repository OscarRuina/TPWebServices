package com.unla.docente.servicios;

import com.unla.docente.modelos.datos.*;
import com.unla.docente.repositorios.*;
import com.unla.docente.soapdocentes.*;
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

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private NotaFinalRepositorio notaFinalRepositorio;

    @Autowired
    private MesaExamenRepositorio mesaExamenRepositorio;

    public RespuestaMateriasAsignadas traerMateriasAsignadas(SolicitudMateriasAsignadas solicitudMaterias){
        //Usuario docente = repositorio.findById(solicitudMaterias.getId()).orElseThrow();
        List<UsuarioMateria> listaUsuarioMaterias = usuarioMateriaRepositorio.findByUsuario_Id(solicitudMaterias.getId());
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
            item.setHoraFinalizacion(usuarioMateria.getMateria().getHoraFinalizacion());
            respuestaMateriasAsignadas.getItem().add(item);
        }
        return respuestaMateriasAsignadas;
    }

    public RespuestaAlumnosPorMateria traerAlumnosPorMateria(SolicitudAlumnosPorMateria solicitudAlumnosPorMateria){
        Materia materia = materiaRepositorio.findById(solicitudAlumnosPorMateria.getIdMateria()).orElseThrow();
        //aca hacer
        List<UsuarioMateria> usuarioMateriaList = usuarioMateriaRepositorio.findByMateria_IdAndInscriptoTrue(materia.getId());

        RespuestaAlumnosPorMateria respuestaAlumnosPorMateria = new RespuestaAlumnosPorMateria();
        respuestaAlumnosPorMateria.setNombre(materia.getNombre());
        respuestaAlumnosPorMateria.setCarrera(materia.getCarrera());
        respuestaAlumnosPorMateria.setCuatrimestre(materia.getCuatrimestre());
        respuestaAlumnosPorMateria.setAñoCuatrimestre(materia.getAñoCuatrimestre());


        for (UsuarioMateria alumnoMateria:
                usuarioMateriaList) {

            if (alumnoMateria.getUsuario().getId() != solicitudAlumnosPorMateria.getIdDocente()){

                RespuestaAlumnosPorMateria.Alumno alumno = new RespuestaAlumnosPorMateria.Alumno();
                alumno.setId(alumnoMateria.getUsuario().getId());
                alumno.setNombre(alumnoMateria.getUsuario().getNombre());
                alumno.setApellido(alumnoMateria.getUsuario().getApellido());
                alumno.setDni(alumnoMateria.getUsuario().getDni());
                alumno.setEmail(alumnoMateria.getUsuario().getEmail());

                respuestaAlumnosPorMateria.getAlumno().add(alumno);
            }
        }

        return respuestaAlumnosPorMateria;
    };

    public RespuestaAlumnosCursada cargaNotasCursada(SolicitudAlumnosCursada solicitudAlumnosCursada){
        //Materia materia = materiaRepositorio.findById(solicitudAlumnosCursada.getIdMateria()).orElseThrow();
        RespuestaAlumnosCursada respuestaAlumnosCursada = new RespuestaAlumnosCursada();
        respuestaAlumnosCursada.setIdDocente(solicitudAlumnosCursada.getIdDocente());
        respuestaAlumnosCursada.setIdMateria(solicitudAlumnosCursada.getIdMateria());
        for (SolicitudAlumnosCursada.NotaAlumnoCursada notaAlumnoCursada:
                solicitudAlumnosCursada.getNotaAlumnoCursada()) {

            //Usuario alumno = usuarioRepositorio.findById(notaAlumnoCursada.getId()).orElseThrow();
            UsuarioMateria alumnoMateria = usuarioMateriaRepositorio.findByUsuario_IdAndMateria_Id(notaAlumnoCursada.getId(), solicitudAlumnosCursada.getIdMateria());

            alumnoMateria.setNotaParcial1(notaAlumnoCursada.getNotaParcial1());
            alumnoMateria.setNotaParcial2(notaAlumnoCursada.getNotaParcial2());
            double notaCursada = (alumnoMateria.getNotaParcial1()+alumnoMateria.getNotaParcial2())/2;
            alumnoMateria.setNotaCursada(notaCursada);
            usuarioMateriaRepositorio.save(alumnoMateria);

            //ahora seteo los valores para la respuesta
            RespuestaAlumnosCursada.NotaAlumnoCursada respuestaNotaAlumnoCursada = new RespuestaAlumnosCursada.NotaAlumnoCursada();
            respuestaNotaAlumnoCursada.setId(alumnoMateria.getId());
            respuestaNotaAlumnoCursada.setNotaParcial1(alumnoMateria.getNotaParcial1());
            respuestaNotaAlumnoCursada.setNotaParcial2(alumnoMateria.getNotaParcial2());
            respuestaNotaAlumnoCursada.setNotaCursada(alumnoMateria.getNotaCursada());

            respuestaAlumnosCursada.getNotaAlumnoCursada().add(respuestaNotaAlumnoCursada);
        }

        return  respuestaAlumnosCursada;
    };

    public RespuestaAlumnosFinal cargaNotasFinal(SolicitudAlumnosFinal solicitudAlumnosFinal){

        Materia materia = materiaRepositorio.findById(solicitudAlumnosFinal.getIdMateria()).orElseThrow();
        RespuestaAlumnosFinal respuestaAlumnosFinal = new RespuestaAlumnosFinal();
        respuestaAlumnosFinal.setIdDocente(solicitudAlumnosFinal.getIdDocente());
        respuestaAlumnosFinal.setIdMateria(solicitudAlumnosFinal.getIdMateria());
        respuestaAlumnosFinal.setIdMesaExamen(solicitudAlumnosFinal.getIdMesaExamen());
        for (SolicitudAlumnosFinal.NotaAlumnoFinal notaAlumnoFinal:
                solicitudAlumnosFinal.getNotaAlumnoFinal()) {
            Usuario alumno = usuarioRepositorio.findById(notaAlumnoFinal.getId()).orElseThrow();
            MesaExamen mesaExamen = mesaExamenRepositorio.findById(solicitudAlumnosFinal.getIdMesaExamen()).orElseThrow();
            UsuarioMateria usuarioMateria = usuarioMateriaRepositorio.findByUsuario_IdAndMateria_Id(notaAlumnoFinal.getId(), solicitudAlumnosFinal.getIdMateria());

            NotaFinal notaFinal = notaFinalRepositorio.findByEstudiante_IdAndMesaExamen_Id(notaAlumnoFinal.getId(), solicitudAlumnosFinal.getIdMesaExamen());
            //NotaFinal notaFinal = new NotaFinal();
            //notaFinal.setEstudiante(alumno);
            //notaFinal.setMesaExamen(mesaExamen);
            notaFinal.setNotaExamen(notaAlumnoFinal.getNotaExamen());
            double notaFinalAlumno = (notaAlumnoFinal.getNotaExamen() + usuarioMateria.getNotaCursada())/2;
            notaFinal.setNotaFinal(notaFinalAlumno);

            if (notaFinal.getNotaExamen() >= 7) {
                notaFinal.setAprobado(true);
            }else{
                notaFinal.setAprobado(false);
            }

            notaFinalRepositorio.save(notaFinal);

            //ahora seteo los valores para la respuesta
            RespuestaAlumnosFinal.NotaAlumnoFinal respuestaNotaAlumnoFinal = new RespuestaAlumnosFinal.NotaAlumnoFinal();
            respuestaNotaAlumnoFinal.setId(notaFinal.getId());
            respuestaNotaAlumnoFinal.setNotaCursada(usuarioMateria.getNotaCursada());
            respuestaNotaAlumnoFinal.setNotaExamen(notaAlumnoFinal.getNotaExamen());
            respuestaNotaAlumnoFinal.setNotaFinal(notaFinalAlumno);

            respuestaAlumnosFinal.getNotaAlumnoFinal().add(respuestaNotaAlumnoFinal);
        }

        return  respuestaAlumnosFinal;
    };



}
