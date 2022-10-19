package com.unla.estudiante.repositorios;

import com.unla.estudiante.modelos.datos.Materia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia,Long> {

    Materia findByNombreIgnoreCase(String nombre);

    List<Materia> findByCarreraIgnoreCase(String carrera);

}
