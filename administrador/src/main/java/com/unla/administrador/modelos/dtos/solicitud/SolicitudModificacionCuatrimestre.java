package com.unla.administrador.modelos.dtos.solicitud;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class SolicitudModificacionCuatrimestre {

    @NotBlank
    @Size(max = 255)
    private String comienzo;

    @NotBlank
    @Size(max = 255)
    private String cierre;

    private List<SolicitudRelacionMateriaCuatrimestre> materias;

}
