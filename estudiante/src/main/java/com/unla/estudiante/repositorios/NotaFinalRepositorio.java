package com.unla.estudiante.repositorios;

import com.unla.estudiante.modelos.datos.NotaFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaFinalRepositorio extends JpaRepository<NotaFinal,Long> {

    NotaFinal findByEstudiante_IdAndMesaExamen_Id(long idUsuario, long idMesaExamen);

}
