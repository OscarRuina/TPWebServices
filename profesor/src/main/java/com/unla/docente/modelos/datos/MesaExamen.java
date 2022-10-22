package com.unla.docente.modelos.datos;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "MESA_EXAMEN")
public class MesaExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(name = "dia")
    private LocalDate dia;

    @Column(name = "hora")
    private String hora;

    @Column(name = "activo")
    private boolean activo;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "materia_Id")
    private Materia materia;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mesaExamen", cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<NotaFinal> notasFinales;

}
