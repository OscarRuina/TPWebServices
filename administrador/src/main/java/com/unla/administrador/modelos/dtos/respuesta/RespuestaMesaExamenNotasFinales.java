package com.unla.administrador.modelos.dtos.respuesta;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaMesaExamenNotasFinales {

    private long id;

    private LocalDate dia;

    private String hora;

    private String materia;

    private String docente;

    private boolean activo;

    private List<RespuestaNotasFinales> notas;



}
