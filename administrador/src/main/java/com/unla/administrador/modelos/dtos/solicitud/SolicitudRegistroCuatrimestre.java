package com.unla.administrador.modelos.dtos.solicitud;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class SolicitudRegistroCuatrimestre {

    @NotBlank
    @Size(max = 255)
    private String nombre;

    @NotNull
    private LocalDate comienzo;

    @NotNull
    private LocalDate cierre;

}
