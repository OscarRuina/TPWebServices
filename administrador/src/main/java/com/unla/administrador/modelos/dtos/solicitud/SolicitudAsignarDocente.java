package com.unla.administrador.modelos.dtos.solicitud;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudAsignarDocente {

    @NotNull
    private long idMateria;

}
