package com.unla.administrador.modelos.dtos.solicitud;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudAsignarMateria {

    @NotNull
    private long idMateria;

    @NotBlank
    @Size(max = 255)
    private String turno;

}
