package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaNotasFinales {

    private long id;

    private String alumno;

    private double notaExamen;

    private double notaFinal;

}
