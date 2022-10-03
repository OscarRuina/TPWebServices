package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaRegistroUsuario {

    private long id;

    private String nombre;

    private String apellido;

    private String dni;

    private String email;

    private String carrera;

    private String nombreUsuario;

    private boolean primerLogin;

    private String rol;

}
