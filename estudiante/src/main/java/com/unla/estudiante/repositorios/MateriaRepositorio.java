package com.unla.estudiante.repositorios;

import com.unla.estudiante.modelos.datos.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia,Long> {

}
