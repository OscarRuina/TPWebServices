package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.MesaExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaExamenRepositorio extends JpaRepository<MesaExamen,Long> {

}
