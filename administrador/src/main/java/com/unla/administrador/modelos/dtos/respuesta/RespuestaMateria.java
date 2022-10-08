package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaMateria {

    private long id;

    private String nombre;

    private String carrera;

    private int año;

    private String dia;

    private String horaInicio;

    private String horaFinalizacion;

    private List<RespuestaUsuarioMateria> listaUsuarios;
}
