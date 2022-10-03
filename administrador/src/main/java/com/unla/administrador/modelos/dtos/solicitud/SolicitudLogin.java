package com.unla.administrador.modelos.dtos.solicitud;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudLogin {

    @NotBlank
    @Size(max = 255)
    private String nombreUsuario;

    @NotBlank
    @Size(max = 255)
    private String contraseña;

}
