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
import com.unla.estudiante.soapestudiantes.Analitico;
import com.unla.estudiante.soapestudiantes.Analitico.Nota;
import com.unla.estudiante.soapestudiantes.Materias;
import com.unla.estudiante.soapestudiantes.Materias.Item;
import com.unla.estudiante.soapestudiantes.MesasExamen;
import com.unla.estudiante.soapestudiantes.RespuestaBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaBajaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.RespuestaModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudBajaInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudBajaInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudIdEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudInscripcionMateriaEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudInscripcionMesaExamenEstudiante;
import com.unla.estudiante.soapestudiantes.SolicitudListaMaterias;
import com.unla.estudiante.soapestudiantes.SolicitudMesaExamen;
import com.unla.estudiante.soapestudiantes.SolicitudMesasExamen;
import com.unla.estudiante.soapestudiantes.SolicitudModificacion;
import com.unla.estudiante.soapestudiantes.SolicitudNombre;
import java.util.List;
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
            if (usuarioMateria.isInscripto() && String.valueOf(usuarioMateria.getMateria().getAñoCuatrimestre())
                    .equals(String.valueOf(materia.getAñoCuatrimestre()))
                    && usuarioMateria.getMateria().getCuatrimestre()
                    .equals(materia.getCuatrimestre())
                    && usuarioMateria.getMateria().getDia().equals(materia.getDia())
                    && usuarioMateria.getMateria().getHoraInicio()
                    .equals(materia.getHoraInicio())) {
                inscripto.set(false);
            }
        });

        UsuarioMateria usuarioMateriaDb = usuarioMateriaRepositorio.findByMateria_IdAndUsuario_Id(
                inscripcionMateriaEstudiante.getIdMateria(),
                inscripcionMateriaEstudiante.getIdEstudiante());

        if (inscripto.get()) {
            /** Si no existe lo crea**/
            if (usuarioMateriaDb == null) {
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
            /** Si existe lo modifica **/
            if (usuarioMateriaDb != null && !usuarioMateriaDb.isInscripto()) {
                usuarioMateriaDb.setInscripto(true);
                usuarioMateriaRepositorio.save(usuarioMateriaDb);
            }
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

    public com.unla.estudiante.soapestudiantes.Materia getMateria(SolicitudNombre nombre) {
        com.unla.estudiante.soapestudiantes.Materia materia =
                new com.unla.estudiante.soapestudiantes.Materia();

        Materia bd = materiaRepositorio.findByNombreIgnoreCase(nombre.getNombre());
        List<UsuarioMateria> materias = usuarioMateriaRepositorio.findByMateria_Id(bd.getId());

        materia.setId(bd.getId());
        materia.setNombre(bd.getNombre());
        materia.setDocente(
                materias.get(0).getUsuario().getApellido() + " " + materias.get(0).getUsuario()
                        .getNombre());
        materia.setDia(bd.getDia());
        materia.setHora(bd.getHoraInicio());
        return materia;
    }

    public Materias getMaterias(SolicitudListaMaterias listaMaterias) {
        Materias materias = new Materias();
        List<Materia> db = materiaRepositorio.findByCarreraIgnoreCase(listaMaterias.getCarrera());
        db.forEach(materia -> {
            Item soap = new Item();
            List<UsuarioMateria> usuarioMaterias = usuarioMateriaRepositorio.findByMateria_Id(
                    materia.getId());
            soap.setId(materia.getId());
            soap.setNombre(materia.getNombre());
            soap.setDocente(
                    usuarioMaterias.get(0).getUsuario().getApellido() + " " + usuarioMaterias.get(0)
                            .getUsuario()
                            .getNombre());
            soap.setDia(materia.getDia());
            soap.setHora(materia.getHoraInicio());
            materias.getItem().add(soap);
        });

        return materias;
    }

    public com.unla.estudiante.soapestudiantes.MesaExamen getMesaExamen(
            SolicitudMesaExamen mesaExamen) {
        com.unla.estudiante.soapestudiantes.MesaExamen mesaExamen1 =
                new com.unla.estudiante.soapestudiantes.MesaExamen();
        MesaExamen db = mesaExamenRepositorio.findById(mesaExamen.getId()).orElseThrow();
        mesaExamen1.setId(db.getId());
        mesaExamen1.setNombre(db.getMateria().getNombre());
        List<UsuarioMateria> usuarioMaterias = usuarioMateriaRepositorio.findByMateria_Id(
                db.getMateria().getId());
        mesaExamen1.setDocente(
                usuarioMaterias.get(0).getUsuario().getApellido() + " " + usuarioMaterias.get(0)
                        .getUsuario()
                        .getNombre()
        );
        mesaExamen1.setDia(db.getDia().toString());
        mesaExamen1.setHora(db.getHora());
        return mesaExamen1;
    }

    public MesasExamen getMesasExamen(SolicitudMesasExamen mesasExamen) {
        MesasExamen mesas = new MesasExamen();
        List<MesaExamen> db = mesaExamenRepositorio.findByActivo(mesasExamen.isActivo());
        db.forEach(mesaExamen -> {
            MesasExamen.Item item = new MesasExamen.Item();
            item.setId(mesaExamen.getId());
            item.setNombre(mesaExamen.getMateria().getNombre());
            List<UsuarioMateria> usuarioMaterias = usuarioMateriaRepositorio.findByMateria_Id(
                    mesaExamen.getMateria().getId());
            item.setDocente(
                    usuarioMaterias.get(0).getUsuario().getApellido() + " " + usuarioMaterias.get(0)
                            .getUsuario()
                            .getNombre()
            );
            item.setDia(mesaExamen.getDia().toString());
            item.setHora(mesaExamen.getHora());
            mesas.getItem().add(item);
        });
        return mesas;
    }

    public Analitico getAnalitico(SolicitudIdEstudiante id) {
        Analitico analitico = new Analitico();
        List<NotaFinal> notas = notaFinalRepositorio.findByEstudiante_IdAndAprobadoTrue(id.getId());

        notas.forEach(notaFinal -> {
            Nota nota = new Nota();
            nota.setId(notaFinal.getId());
            nota.setMateria(notaFinal.getMesaExamen().getMateria().getNombre());
            nota.setNotaExamen(notaFinal.getNotaExamen());
            nota.setNotaFinal(notaFinal.getNotaFinal());
            analitico.getNota().add(nota);
        });

        return analitico;
    }

}
