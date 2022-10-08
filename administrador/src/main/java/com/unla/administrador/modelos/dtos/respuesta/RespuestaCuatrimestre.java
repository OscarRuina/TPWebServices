package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaCuatrimestre {
    private long id;

    private String comienzo;

    private String cierre;

    private List<RespuestaMateriaCuatrimestre> materias;
}
