package com.unla.administrador.repositorios;

import com.unla.administrador.modelos.datos.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepositorio extends JpaRepository<Inscripcion, Long> {

}
