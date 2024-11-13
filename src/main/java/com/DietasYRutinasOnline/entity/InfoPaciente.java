package com.DietasYRutinasOnline.entity;

import java.math.BigDecimal;
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
@Table(name="InfoPaciente")
public class InfoPaciente {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinfopaciente")
    private int idinfopaciente;

    @OneToOne
    //@ManyToOne
    //@MapsId("idusuario")
    @JoinColumn(name = "idusuario")
    private Usuario paciente;
    
	/*@Override
	public String toString() {
		return "Cuestionario [idcuestionario=" + idcuestionario + ", paciente=" + paciente.toString() + ", frecEjercicios="
				+ frecEjercicios + ", alergia=" + alergia + ", pesoCorporal=" + pesoCorporal + ", perimCintura="
				+ perimCintura + ", perimCadera=" + perimCadera + ", perimMuslo=" + perimMuslo + ", perimBrazo="
				+ perimBrazo + ", objetivo=" + objetivo + "]";
	}*/

	@Column(name="frecEjercicios")
	private String frecEjercicios;
	
	@Column(name="condicion")
	private String condicion;
	
	@Column(name="pesoCorporal")
	private BigDecimal pesoCorporal;
	
	@Column(name="estatura")
	private BigDecimal estatura;
	
	@Column(name="perimCintura")
	private BigDecimal perimCintura;
	
	@Column(name="perimCadera")
	private BigDecimal perimCadera;
	
	@Column(name="perimMuslo")
	private BigDecimal perimMuslo;
	
	@Column(name="perimBrazo")
	private BigDecimal perimBrazo;
	
	@Column(name="objetivo")
	private String objetivo;

	//@ManyToOne
	//@JoinColumn(name = "iddieta")
    //private Dieta dieta;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "PacienteDieta", 
      joinColumns = @JoinColumn(name = "id_paciente", referencedColumnName ="idinfopaciente"), 
      inverseJoinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="iddieta"))
    private List<Dieta> dieta;

	public int getIdinfopaciente() {
		return idinfopaciente;
	}

	public void setIdinfopaciente(int idinfopaciente) {
		this.idinfopaciente = idinfopaciente;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public String getFrecEjercicios() {
		return frecEjercicios;
	}

	public void setFrecEjercicios(String frecEjercicios) {
		this.frecEjercicios = frecEjercicios;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public BigDecimal getPesoCorporal() {
		return pesoCorporal;
	}

	public void setPesoCorporal(BigDecimal pesoCorporal) {
		this.pesoCorporal = pesoCorporal;
	}

	public BigDecimal getEstatura() {
		return estatura;
	}

	public void setEstatura(BigDecimal estatura) {
		this.estatura = estatura;
	}

	public BigDecimal getPerimCintura() {
		return perimCintura;
	}

	public void setPerimCintura(BigDecimal perimCintura) {
		this.perimCintura = perimCintura;
	}

	public BigDecimal getPerimCadera() {
		return perimCadera;
	}

	public void setPerimCadera(BigDecimal perimCadera) {
		this.perimCadera = perimCadera;
	}

	public BigDecimal getPerimMuslo() {
		return perimMuslo;
	}

	public void setPerimMuslo(BigDecimal perimMuslo) {
		this.perimMuslo = perimMuslo;
	}

	public BigDecimal getPerimBrazo() {
		return perimBrazo;
	}

	public void setPerimBrazo(BigDecimal perimBrazo) {
		this.perimBrazo = perimBrazo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public List<Dieta> getDieta() {
		return dieta;
	}

	public void setDieta(List<Dieta> dieta) {
		this.dieta = dieta;
	}

}
