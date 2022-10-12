package com.unla.administrador.modelos.dtos.respuesta;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaInscripcion {

    private long id;

    private LocalDate inicio;

    private LocalDate fin;

    private String tipo;

}
