package com.DietasYRutinasOnline.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Asistencia")
public class Asistencia {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasistencia")
    private int idasistencia;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idreunion")
    private Reunion reunion;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idusuario")
    private Usuario paciente;
	
	@Column(name="fecasistencia")
	private LocalDateTime fecha;
	
	@Column(name="estasistencia")
	private String estado;

	public int getIdasistencia() {
		return idasistencia;
	}

	public void setIdasistencia(int idasistencia) {
		this.idasistencia = idasistencia;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
