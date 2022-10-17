package com.unla.estudiante.modelos.datos;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @NotBlank
    @Column(name = "nombre")
    @Size(max = 250)
    private String nombre;

    @NotBlank
    @Column(name = "apellido")
    @Size(max = 250)
    private String apellido;

    @NotBlank
    @Column(name = "dni")
    @Size(max = 250)
    private String dni;

    @NotBlank
    @Column(name = "email")
    @Size(max = 250)
    @Email
    private String email;

    @NotBlank
    @Column(name = "carrera")
    @Size(max = 250)
    private String carrera;

    @Column(name = "nombre_usuario")
    @Size(max = 250)
    private String nombreUsuario;

    @NotBlank
    @Column(name = "contraseña")
    @Size(max = 250)
    private String contraseña;

    @Column(name = "primer_login")
    private boolean primerLogin;

    @Column(name = "activo")
    private boolean activo;

    @NotBlank
    @Column(name = "rol")
    @Size(max = 250)
    private String rol;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<UsuarioMateria> materias;

}
