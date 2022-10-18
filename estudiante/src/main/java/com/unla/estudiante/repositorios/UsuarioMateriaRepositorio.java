package com.unla.estudiante.repositorios;

import com.unla.estudiante.modelos.datos.UsuarioMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMateriaRepositorio extends JpaRepository<UsuarioMateria,Long> {

    UsuarioMateria findByMateria_IdAndUsuario_Id(long idMateria, long idUsuario);

}
