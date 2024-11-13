package com.DietasYRutinasOnline.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Alimento")
public class Alimento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalimento")
    private int idalimento;

	@Column(name="nomalimento")
	private String nombre;
	
	@Column(name="nutrientes")
	private String nutrientes;
	
	@Column(name="tipoalimento")
	private String tipo;
	
	@Column(name="descalimento")
	private String descripcion;

	@ManyToMany(mappedBy = "alimento")
    private List<Dieta> dieta;

	public int getIdalimento() {
		return idalimento;
	}

	public void setIdalimento(int idalimento) {
		this.idalimento = idalimento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNutrientes() {
		return nutrientes;
	}

	public void setNutrientes(String nutrientes) {
		this.nutrientes = nutrientes;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Dieta> getDieta() {
		return dieta;
	}

	public void setDieta(List<Dieta> dieta) {
		this.dieta = dieta;
	}
	
}
