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
@Table(name="Alimento")
public class Alimento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalimento")
    private Long idalimento;

	@Column(name="nombre", length = 40)
	private String nombre;
	
	@Column(name="nutrientes")
	private String nutrientes;
	
	@Column(name="tipo", length = 20)
	private String tipo;
	
	@Column(name="descripcion")
	private String descripcion;

	@ManyToMany(mappedBy = "alimento")
    private List<Dieta> dieta;

}
