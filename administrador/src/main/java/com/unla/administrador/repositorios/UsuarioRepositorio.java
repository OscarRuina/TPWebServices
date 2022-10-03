package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByNombreUsuarioIgnoreCase(String nombreUsuario);

    List<Usuario> findByRolAndActivoTrue(String rol);

}
