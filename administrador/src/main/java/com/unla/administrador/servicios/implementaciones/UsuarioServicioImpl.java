package com.unla.administrador.servicios.implementaciones;

import com.unla.administrador.modelos.datos.Materia;
import com.unla.administrador.modelos.datos.Usuario;
import com.unla.administrador.modelos.datos.UsuarioMateria;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudAsignarDocente;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudCambioContraseña;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudLogin;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudModificacionUsuario;
import com.unla.administrador.modelos.dtos.solicitud.SolicitudRegistroUsuario;
import com.unla.administrador.repositorios.MateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioMateriaRepositorio;
import com.unla.administrador.repositorios.UsuarioRepositorio;
import com.unla.administrador.servicios.interfaces.IUsuarioServicio;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio {


    private static final String PREFIJO = "ROLE_";
    private static final String CONTRASEÑA_TEMPORAL = "foo1234";

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private UsuarioMateriaRepositorio usuarioMateriaRepositorio;

    @Override
    public Usuario buscarId(long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Usuario no encontrado",
                        "404"
                )
        );
    }

    @Override
    public List<Usuario> listar(String rol) {
        String roleBuscar = PREFIJO + rol.toUpperCase();
        return repositorio.findByRolAndActivoTrue(roleBuscar);
    }

    @Override
    public Usuario login(SolicitudLogin solicitudLogin) {
        Usuario usuario = repositorio.findByNombreUsuarioIgnoreCase(solicitudLogin.getNombreUsuario()).orElseThrow(
                () -> new ObjectNotFoundException(
                        "Usuario no encontrado",
                        "404"
                )
        );
        if(!usuario.getContraseña().equals(solicitudLogin.getContraseña())){
            throw new RuntimeException("Contraseña incorrecta");
        }
        return usuario;
    }

    @Override
    public String primerLogin(long id, SolicitudCambioContraseña cambioContraseña) {
        Usuario usuario = repositorio.findById(id).orElseThrow();
        usuario.setContraseña(cambioContraseña.getContraseña());
        usuario.setPrimerLogin(false);
        repositorio.save(usuario);
        return "Contraseña Modificada Correctamente";
    }

    @Override
    public String logout() {
        return "Saliste del Sistema Correctamente";
    }

    @Override
    public Usuario registrar(SolicitudRegistroUsuario registroUsuario) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registroUsuario.getNombre());
        usuario.setApellido(registroUsuario.getApellido());
        usuario.setDni(registroUsuario.getDni());
        usuario.setEmail(registroUsuario.getEmail());
        usuario.setCarrera(registroUsuario.getCarrera());

        String nombreUsuario = registroUsuario.getNombre().toLowerCase() + registroUsuario.getApellido().toLowerCase();
        String contraseñaTemporal = CONTRASEÑA_TEMPORAL;
        String rol = PREFIJO + registroUsuario.getRol().toUpperCase();

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContraseña(contraseñaTemporal);
        usuario.setPrimerLogin(true);
        usuario.setActivo(true);
        usuario.setRol(rol);

        return repositorio.save(usuario);
    }

    @Override
    public Usuario modificar(long id, SolicitudModificacionUsuario modificacionUsuario) {
        Usuario usuario = buscarId(id);
        usuario.setNombre(modificacionUsuario.getNombre());
        usuario.setApellido(modificacionUsuario.getApellido());
        usuario.setDni(modificacionUsuario.getDni());
        usuario.setEmail(modificacionUsuario.getEmail());
        usuario.setCarrera(modificacionUsuario.getCarrera());

        String nombreUsuario = modificacionUsuario.getNombre().toLowerCase() + modificacionUsuario.getApellido().toLowerCase();
        String rol = PREFIJO + modificacionUsuario.getRol().toUpperCase();

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setRol(rol);

        return repositorio.save(usuario);
    }

    @Override
    public String eliminar(long id) {
        Usuario usuario = buscarId(id);
        usuario.setActivo(false);
        repositorio.save(usuario);
        return "Usuario Eliminado Correctamente";
    }

    @Override
    public String asignarMateriaDocente(long id, SolicitudAsignarDocente asignarDocente) {
        Usuario docente = buscarId(id);
        Materia materia = materiaRepositorio.findById(asignarDocente.getIdMateria()).orElseThrow();

        UsuarioMateria usuarioMateria = new UsuarioMateria();

        usuarioMateria.setUsuario(docente);
        usuarioMateria.setMateria(materia);

        usuarioMateria.setNotaCursada(0);
        usuarioMateria.setNotaParcial1(0);
        usuarioMateria.setNotaParcial2(0);
        usuarioMateria.setInscripto(true);

        docente.getMaterias().add(usuarioMateria);

        repositorio.save(docente);

        return "Materia Agregada Correctamente";

    }

    @Override
    public List<UsuarioMateria> listarMaterias(long idUsuario) {
        Usuario usuario = buscarId(idUsuario);
        return usuario.getMaterias();
    }

    @Override
    public List<UsuarioMateria> listarMateriasEstudiante(long idUsuario) {
        Usuario usuario = buscarId(idUsuario);
        return usuarioMateriaRepositorio.findByUsuario_IdAndInscriptoTrue(usuario.getId());
    }
}
