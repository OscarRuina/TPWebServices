package com.unla.administrador.modelos.dtos.respuesta;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaUsuarioMateria {

    private long id;

    private String nombre;

    private String apellido;

    private int dni;

    private String rol;

    private double notaParcial1;

    private double notaParcial2;

    private double notaCursada;
}
