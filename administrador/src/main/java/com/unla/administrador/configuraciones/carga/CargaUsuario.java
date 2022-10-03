package com.unla.administrador.configuraciones.carga;

import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargaUsuario implements CommandLineRunner {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public void run(String... args) throws Exception {
        cargaUsuarios();
    }

    private void cargaUsuarios() {
        if (usuarioRepositorio.count() == 0) {
            cargaUsuario();
        }
    }

    private void cargaUsuario() {
        usuarioRepositorio.save(
                crearUsuarioAdmin(1L, "Admin", "Admin", "11111111",
                        "admin@hotmail.com", "Admin"));
    }

    private Usuario crearUsuarioAdmin(long id, String nombre, String apellido, String dni,
            String email,
            String carrera) {
        return new Usuario(id, nombre, apellido, dni, email, carrera, "admin",
                "foo1234", false, true, "ROLE_ADMIN");
    }
}
