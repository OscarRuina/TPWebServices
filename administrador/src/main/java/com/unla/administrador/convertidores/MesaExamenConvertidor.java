package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.MesaExamen;
import com.unla.administrador.modelos.datos.NotaFinal;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaMesaExamenNotasFinales;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaNotasFinales;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMesaExamen;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMateriaEstudianteLista;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaUsuarioMesaExamenEstudianteLista;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class MesaExamenConvertidor {

    private MesaExamenConvertidor(){}

    public static RespuestaRegistroMesaExamen convertirRespuestaMesaExamen(MesaExamen mesaExamen){
        RespuestaRegistroMesaExamen dto = new RespuestaRegistroMesaExamen();

        dto.setId(mesaExamen.getId());
        dto.setDia(mesaExamen.getDia());
        dto.setHora(mesaExamen.getHora());

        dto.setMateria(mesaExamen.getMateria().getNombre());

        AtomicReference<String> docente = new AtomicReference<>("");
        mesaExamen.getMateria().getUsuarios().forEach(
                usuarioMateria -> {
                    if(usuarioMateria.getUsuario().getRol().equals("ROLE_DOCENTE")){
                        docente.set(usuarioMateria.getUsuario().getNombre() + " "
                                + usuarioMateria.getUsuario().getApellido());
                    }
                }
        );

        dto.setDocente(String.valueOf(docente));

        dto.setActivo(mesaExamen.isActivo());
        return dto;
    }

    public static RespuestaMesaExamenNotasFinales convertirRespuestaMesaExamenNotasFinales(MesaExamen mesaExamen){
        RespuestaMesaExamenNotasFinales dto = new RespuestaMesaExamenNotasFinales();

        dto.setId(mesaExamen.getId());
        dto.setDia(mesaExamen.getDia());
        dto.setHora(mesaExamen.getHora());

        dto.setMateria(mesaExamen.getMateria().getNombre());

        AtomicReference<String> docente = new AtomicReference<>("");
        mesaExamen.getMateria().getUsuarios().forEach(
                usuarioMateria -> {
                    if(usuarioMateria.getUsuario().getRol().equals("ROLE_DOCENTE")){
                        docente.set(usuarioMateria.getUsuario().getNombre() + " "
                                + usuarioMateria.getUsuario().getApellido());
                    }
                }
        );

        dto.setDocente(String.valueOf(docente));

        dto.setActivo(mesaExamen.isActivo());

        List<RespuestaNotasFinales> notasFinales = new ArrayList<>();

        mesaExamen.getNotasFinales().forEach(
                notaFinal -> {
                    notasFinales.add(convertirRespuestaNotasFinales(notaFinal));
                }
        );

        dto.setNotas(notasFinales);

        return dto;
    }

    public static RespuestaNotasFinales convertirRespuestaNotasFinales(NotaFinal notaFinal){
        RespuestaNotasFinales dto = new RespuestaNotasFinales();

        dto.setId(notaFinal.getEstudiante().getId());
        dto.setAlumno(notaFinal.getEstudiante().getNombre() + " " + notaFinal.getEstudiante().getApellido());
        dto.setNotaExamen(notaFinal.getNotaExamen());
        dto.setNotaFinal(notaFinal.getNotaFinal());

        return dto;
    }

    public static RespuestaUsuarioMesaExamenEstudianteLista convertirRespuestaUsuarioMesaExamenEstudianteLista(
            NotaFinal notaFinal){
        RespuestaUsuarioMesaExamenEstudianteLista respuestaUsuarioMateria = new RespuestaUsuarioMesaExamenEstudianteLista();
        respuestaUsuarioMateria.setId(notaFinal.getEstudiante().getId());
        respuestaUsuarioMateria.setEstudiante(notaFinal.getEstudiante().getNombre() + " " + notaFinal.getEstudiante().getApellido());
        respuestaUsuarioMateria.setDni(notaFinal.getEstudiante().getDni());
        respuestaUsuarioMateria.setEmail(notaFinal.getEstudiante().getEmail());
        return respuestaUsuarioMateria;
    }

}
