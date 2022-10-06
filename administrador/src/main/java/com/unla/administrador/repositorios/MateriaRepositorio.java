package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia,Long> {
    //sigue sin hacer lo que quiero supongo
    List<Materia> findByCuatrimestres(long id);
}
