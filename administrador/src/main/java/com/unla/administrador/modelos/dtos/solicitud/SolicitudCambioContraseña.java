package com.unla.administrador.modelos.dtos.solicitud;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudCambioContraseña {

    @NotBlank
    @Size(max = 255)
    private String contraseña;

}
