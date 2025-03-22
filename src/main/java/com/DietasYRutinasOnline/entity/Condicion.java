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
@Table(name="Condicion")
public class Condicion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcondicion")
    private Long idcondicion;

	@Column(name="nombre", length=20 )
	private String nombre;
	
	@Column(name="estado")
	private Boolean estado;

	@ManyToMany(mappedBy = "condicion")
    private List<Dieta> dieta;

}
