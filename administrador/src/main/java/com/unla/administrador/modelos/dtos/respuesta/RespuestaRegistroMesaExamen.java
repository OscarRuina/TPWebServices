package com.unla.administrador.modelos.dtos.respuesta;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaRegistroMesaExamen {

    private long id;

    private LocalDate dia;

    private String hora;

    private String materia;

    private String docente;

    private boolean activo;





}
