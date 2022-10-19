package com.unla.administrador.repositorios;

import com.unla.docente.modelos.datos.Usuario;
import com.unla.docente.modelos.datos.UsuarioMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioMateriaRepositorio extends JpaRepository<UsuarioMateria,Long> {

    List<UsuarioMateria> findByUsuario(Usuario usuario);

}
