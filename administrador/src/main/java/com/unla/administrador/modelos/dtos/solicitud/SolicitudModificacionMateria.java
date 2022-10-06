package com.unla.administrador.modelos.dtos.solicitud;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SolicitudModificacionMateria {

    @NotBlank
    @Size(max = 250)
    private String nombre;

    @NotBlank
    @Size(max = 250)
    private String carrera;

    @NotBlank
    @Size(max = 250)
    private String año;

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
    private String idProfesor;
}
