package com.DietasYRutinasOnline.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TransaccionGeneral")
public class Transaccion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtrangeneral")
    private int idregistro;

	@Column(name="fechatrans")
	private LocalDateTime fecha;
	
	@Column(name="tipotrans")
	private String tipo;
	
	@ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "idrutina")
    private Rutina rutina;
	
	@ManyToOne
	@JoinColumn(name = "iddieta")
    private Dieta dieta;
	
	@ManyToOne
    @JoinColumn(name = "idhorario")
    private Horario horario;
	
	@ManyToOne
    @JoinColumn(name = "idinfopaciente")
    private InfoPaciente infopaciente;

	@ManyToOne
    @JoinColumn(name = "idreunion")
    private Reunion reunion;
	
	@ManyToOne
    @JoinColumn(name = "idasistencia")
    private Asistencia asistencia;

	public int getIdregistro() {
		return idregistro;
	}

	public void setIdregistro(int idregistro) {
		this.idregistro = idregistro;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Rutina getRutina() {
		return rutina;
	}

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}

	public Dieta getDieta() {
		return dieta;
	}

	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public InfoPaciente getInfopaciente() {
		return infopaciente;
	}

	public void setInfopaciente(InfoPaciente infopaciente) {
		this.infopaciente = infopaciente;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}

	public Asistencia getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Asistencia asistencia) {
		this.asistencia = asistencia;
	}

	
}
