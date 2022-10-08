package com.unla.administrador.modelos.dtos.solicitud;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SolicitudRegistroMateria {

    @NotBlank
    @Size(max = 250)
    private String nombre;

    @NotBlank
    @Size(max = 250)
    private String carrera;

    @NotBlank
    @Size(max = 250)
    private String cuatrimestre;

    @NotNull
    private int añoCuatrimestre;

    @NotNull
    private int añoMateria;

    @NotBlank
    @Size(max = 250)
    private String dia;

    @NotBlank
    @Size(max = 250)
    private String horaInicio;

    @NotBlank
    @Size(max = 250)
    private String horaFinalizacion;

    @NotBlank
    @Size(max = 250)
    private String turno;

}
