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
@Table(name="Notificacion")
public class Notificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnoti")
    private int idnoti;
    
	@ManyToOne
	@JoinColumn(name = "idtrangeneral")
    private Transaccion transaccion;
	
	@Column(name="rolnoti")
	private String rol;
	
	@Column(name="mensaje")
	private String mensaje;
	
	@Column(name="estnoti")
	private String estado;
	
	@Column(name="timestamp")
	private LocalDateTime timestamp;
	
	@Column(name="diaSemana")
	private String dia;

	public int getIdnoti() {
		return idnoti;
	}

	public void setIdnoti(int idnoti) {
		this.idnoti = idnoti;
	}

	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
	
}
