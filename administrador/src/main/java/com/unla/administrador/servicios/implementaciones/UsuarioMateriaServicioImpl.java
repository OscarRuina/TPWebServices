package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.repositorios.UsuarioMateriaRepositorio;
import com.unla.administrador.servicios.interfaces.IUsuarioMateriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMateriaServicioImpl implements IUsuarioMateriaServicio {

    @Autowired
    private UsuarioMateriaRepositorio usuarioMateriaRepositorio;

    @Override
    public UsuarioMateria crearRelacion(Usuario usuario, Materia materia) {
        UsuarioMateria usuarioMateria = new UsuarioMateria();
        usuarioMateria.setMateria(materia);
        usuarioMateria.setUsuario(usuario);
        return usuarioMateriaRepositorio.save(usuarioMateria);
    }
}
