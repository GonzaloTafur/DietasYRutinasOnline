package com.DietasYRutinasOnline.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Ejercicio")
public class Ejercicio {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idejercicio")
    private int idejercicio;

    @Column(name = "nomejercicio")
    private String nombre;

    @Column(name = "grupomuscular")
    private String grupomuscular;

    @Column(name = "tipoejercicio")
    private String tipo;

    @Column(name = "series")
    private int series;

    @Column(name = "repeticiones")
    private String repeticiones;

    @Column(name = "descejercicio")
    private String descripcion;

    @ManyToMany(mappedBy = "ejercicio")
    private List<Rutina> rutina;

	public int getIdejercicio() {
		return idejercicio;
	}

	public void setIdejercicio(int idejercicio) {
		this.idejercicio = idejercicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGrupomuscular() {
		return grupomuscular;
	}

	public void setGrupomuscular(String grupomuscular) {
		this.grupomuscular = grupomuscular;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public String getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(String repeticiones) {
		this.repeticiones = repeticiones;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Rutina> getRutina() {
		return rutina;
	}

	public void setRutina(List<Rutina> rutina) {
		this.rutina = rutina;
	}

}
