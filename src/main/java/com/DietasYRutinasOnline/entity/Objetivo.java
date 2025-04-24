package com.DietasYRutinasOnline.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Objetivo")
public class Objetivo implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivo")
    private Long codigo;

	@Column(name="nombre")
	private String nombre;
	
	@Column(name="descripcion", columnDefinition = "TEXT")
	private String descripcion;

    @Column(name = "estado")
    private Boolean estado;

}
