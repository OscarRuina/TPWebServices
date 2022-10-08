package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaMateriaCuatrimestre {

    private long id;

    private String nombre;

    private String carrera;

    private int año;

    private String dia;

    private String horaInicio;

    private String horaFinalizacion;

    private String turno;
}
