package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaCuatrimestreMateria {

    private long id;

    private String turno;

    private String nombreCuatrimestre;

    private String nombreMateria;

}
