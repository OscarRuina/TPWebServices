package com.unla.administrador.modelos.dtos.solicitud;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudRegistroMesaExamen {

    @NotNull
    private LocalDate dia;

    @NotBlank
    @Size(max = 6,message = "El formato es incorrecto, debe ser hh:mm")
    private String hora;

    @NotNull
    private long idMateria;



}
