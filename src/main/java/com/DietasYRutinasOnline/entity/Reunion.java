package com.DietasYRutinasOnline.entity;


import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Reunion")
public class Reunion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreunion")
    private int idreunion;
	
	@ManyToOne
	@JoinColumn(name = "idusuario")
    private Usuario nutriologo;
    
	@Column(name="motivo")
	private String motivo;
	
	@Column(name="diaSemana")
	private String dia;
	
	@Column(name="horaInicio")
	private LocalTime hora;
	
	@Column(name="enlace")
	private String enlace;
	
	@Column(name="estreunion")
	private String estado;
	
	
	public int getIdreunion() {
		return idreunion;
	}

	public void setIdreunion(int idreunion) {
		this.idreunion = idreunion;
	}

	public Usuario getNutriologo() {
		return nutriologo;
	}

	public void setNutriologo(Usuario nutriologo) {
		this.nutriologo = nutriologo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
