package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.websocket.Decoder.Text;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="Ejercicio")
public class Ejercicio implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Long codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "grupo_muscular")
    private String grupomuscular;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "series")
    private int series;

    @Column(name = "repeticiones")
    private String repeticiones;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToMany(mappedBy = "ejercicio")
    private List<Rutina> rutina;

    @Column(name="estado", columnDefinition="boolean default true")
	private Boolean estado;
    
}

