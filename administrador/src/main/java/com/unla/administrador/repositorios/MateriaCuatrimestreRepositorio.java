package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.MateriaCuatrimestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaCuatrimestreRepositorio extends JpaRepository<MateriaCuatrimestre,Long> {

}
