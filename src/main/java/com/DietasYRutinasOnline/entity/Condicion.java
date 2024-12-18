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
@Table(name="Condicion")
public class Condicion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcondicion")
    private int idcondicion;

	@Column(name="nomcondicion")
	private String nombre;
	
	@Column(name="estcondicion")
	private String estado;

	@ManyToMany(mappedBy = "condicion")
    private List<Dieta> dieta;

	public int getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(int idcondicion) {
		this.idcondicion = idcondicion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Dieta> getDieta() {
		return dieta;
	}

	public void setDieta(List<Dieta> dieta) {
		this.dieta = dieta;
	}
	
}
