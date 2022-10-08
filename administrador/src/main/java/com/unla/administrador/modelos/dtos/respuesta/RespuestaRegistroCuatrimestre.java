package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaRegistroCuatrimestre {

    private long id;

    private String nombre;

    private String comienzo;

    private String cierre;

}
