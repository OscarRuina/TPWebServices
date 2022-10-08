package com.unla.administrador.modelos.dtos.solicitud;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
public class SolicitudRelacionMateriaCuatrimestre {

    @NotBlank
    @Size(max = 250)
    private String idMateria;

    @NotBlank
    @Size(max = 250)
    private String turno;
}
