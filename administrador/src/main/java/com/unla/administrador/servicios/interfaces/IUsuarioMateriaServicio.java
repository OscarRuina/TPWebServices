package com.unla.administrador.servicios.interfaces;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.datos.UsuarioMateria;

public interface IUsuarioMateriaServicio {

    UsuarioMateria crearRelacion(Usuario usuario, Materia materia);

}
