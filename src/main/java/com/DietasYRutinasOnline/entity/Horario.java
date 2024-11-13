package com.DietasYRutinasOnline.entity;

import java.time.LocalTime;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Horario")
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhorario")
    private int idhorario;
	
	@OneToOne
	@JoinColumn(name = "idusuario")
    private Usuario paciente;
	
	@ManyToOne
	@JoinColumn(name = "idrutina")
    private Rutina rutina;
    
	@Column(name="diaSemana")
	private String dia;
	
	@Column(name="periodo")
	private String periodo;
	
	@Column(name="esthorario")
	private String estado;
	
	/*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "HorarioRutina", 
      joinColumns = @JoinColumn(name = "id_horario", referencedColumnName ="idhorario"), 
      inverseJoinColumns = @JoinColumn(name = "id_rutina", referencedColumnName ="idrutina"))
    private List<Rutina> rutina;*/

	public int getIdhorario() {
		return idhorario;
	}

	public void setIdhorario(int idhorario) {
		this.idhorario = idhorario;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public Rutina getRutina() {
		return rutina;
	}

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
