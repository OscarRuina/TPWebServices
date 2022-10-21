package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaUsuarioMateriaEstudiante {

    private long id;

    private String nombreMateria;

    private double notaParcial1;

    private double notaParcial2;

    private double notaCursada;

}
