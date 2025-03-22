package com.DietasYRutinasOnline.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="Ejercicio")
public class Ejercicio {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idejercicio")
    private Long idejercicio;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "grupomuscular")
    private String grupomuscular;

    @Column(name = "tipoejercicio")
    private String tipo;

    @Column(name = "series")
    private int series;

    @Column(name = "repeticiones")
    private String repeticiones;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "ejercicio")
    private List<Rutina> rutina;

}
