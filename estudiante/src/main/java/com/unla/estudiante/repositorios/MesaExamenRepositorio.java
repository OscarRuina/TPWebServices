package com.unla.estudiante.repositorios;

import com.unla.estudiante.modelos.datos.MesaExamen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaExamenRepositorio extends JpaRepository<MesaExamen,Long> {

    List<MesaExamen> findByActivo(boolean activo);

}
