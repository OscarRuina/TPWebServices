package com.unla.docente.repositorios;

import com.unla.docente.modelos.datos.MesaExamen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaExamenRepositorio extends JpaRepository<MesaExamen,Long> {

    List<MesaExamen> findByMateria_IdAndActivoTrue(long materiaId);

}
