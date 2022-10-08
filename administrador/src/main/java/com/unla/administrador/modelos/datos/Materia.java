package com.unla.administrador.modelos.datos;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MATERIAS")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @NotBlank
    @Column(name = "nombre")
    @Size(max = 250)
    private String nombre;

    @NotBlank
    @Column(name = "carrera")
    @Size(max = 250)
    private String carrera;

    @Column(name = "año")
    @Range(min=0, max=9999)
    private int año;

    @NotBlank
    @Column(name = "dia")
    @Size(max = 250)
    private String dia;

    @NotBlank
    @Column(name = "hora_inicio")
    @Size(max = 250)
    private String horaInicio;

    @NotBlank
    @Column(name = "hora_finalizacion")
    @Size(max = 250)
    private String horaFinalizacion;

    @Column(name = "activo")
    private boolean activo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "materia", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<UsuarioMateria> usuarios;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "materia", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<MateriaCuatrimestre> cuatrimestres;


}
