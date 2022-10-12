package com.unla.administrador.modelos.dtos.solicitud;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudInscripcion {

    @NotNull
    private LocalDate inicio;

    @NotNull
    private LocalDate fin;

}
