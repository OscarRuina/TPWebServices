package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.Cuatrimestre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuatrimestreRepositorio extends JpaRepository<Cuatrimestre,Long> {

    List<Cuatrimestre> findByActivoTrue();

}
