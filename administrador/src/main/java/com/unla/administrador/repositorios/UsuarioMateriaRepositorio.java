package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.UsuarioMateria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMateriaRepositorio extends JpaRepository<UsuarioMateria,Long> {

    List<UsuarioMateria> findByUsuario_IdAndInscriptoTrue(long id);

    List<UsuarioMateria> findByMateria_IdAndInscriptoTrue(long id);

}
