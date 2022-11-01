package com.unla.docente.repositorios;

import com.unla.docente.modelos.datos.Materia;
import com.unla.docente.modelos.datos.Usuario;
import com.unla.docente.modelos.datos.UsuarioMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioMateriaRepositorio extends JpaRepository<UsuarioMateria,Long> {

    List<UsuarioMateria> findByUsuario_Id(long usuarioId);

    UsuarioMateria findByUsuario_IdAndMateria_Id(long usuarioId, long materiaId);

    List<UsuarioMateria> findByMateria_IdAndInscriptoTrue(long id);
}
