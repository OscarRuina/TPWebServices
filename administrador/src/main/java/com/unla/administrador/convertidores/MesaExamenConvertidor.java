package com.unla.administrador.convertidores;

import com.unla.administrador.modelos.datos.MesaExamen;
import com.unla.administrador.modelos.dtos.respuesta.RespuestaRegistroMesaExamen;
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

}
