package com.unla.docente.repositorios;

import com.unla.docente.modelos.datos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {



}
