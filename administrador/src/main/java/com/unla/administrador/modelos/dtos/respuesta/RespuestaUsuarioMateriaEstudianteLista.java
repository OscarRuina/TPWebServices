package com.unla.administrador.modelos.dtos.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaUsuarioMateriaEstudianteLista {

    private long id;

    private String estudiante;

    private String dni;

    private String email;

}
