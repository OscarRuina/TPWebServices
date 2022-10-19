package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.NotaFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaFinalRepositorio extends JpaRepository<NotaFinal,Long> {

}
