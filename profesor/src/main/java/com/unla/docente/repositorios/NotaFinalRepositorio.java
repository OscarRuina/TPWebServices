package com.unla.docente.repositorios;

import com.unla.docente.modelos.datos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaFinalRepositorio extends JpaRepository<NotaFinal,Long> {
    NotaFinal findByEstudiante_IdAndMesaExamen_Id(long usuarioId, long mesaExamenId);
}
