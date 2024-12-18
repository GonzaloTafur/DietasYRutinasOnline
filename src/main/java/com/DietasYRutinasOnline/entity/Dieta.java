package com.DietasYRutinasOnline.entity;

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
@Table(name="Dieta")
public class Dieta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddieta")
    private int iddieta;

	@Column(name="nomdieta")
	private String nombre;
	
	@Column(name="objdieta")
	private String objetivo;
	
	@Column(name="descdieta")
	private String descripcion;
	
	@Column(name="estdieta")
	private String estado;

	@ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario nutriologo;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "DietaAlimento", 
      joinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="iddieta"), 
      inverseJoinColumns = @JoinColumn(name = "id_alimento", referencedColumnName ="idalimento"))
    private List<Alimento> alimento;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "DietaCondicion", 
      joinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="iddieta"), 
      inverseJoinColumns = @JoinColumn(name = "id_condicion", referencedColumnName ="idcondicion"))
    private List<Condicion> condicion;

	@ManyToMany(mappedBy = "dieta")
    private List<InfoPaciente> paciente;

	public int getIddieta() {
		return iddieta;
	}

	public void setIddieta(int iddieta) {
		this.iddieta = iddieta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getNutriologo() {
		return nutriologo;
	}

	public void setNutriologo(Usuario nutriologo) {
		this.nutriologo = nutriologo;
	}

	public List<Alimento> getAlimento() {
		return alimento;
	}

	public void setAlimento(List<Alimento> alimento) {
		this.alimento = alimento;
	}

	public List<Condicion> getCondicion() {
		return condicion;
	}

	public void setCondicion(List<Condicion> condicion) {
		this.condicion = condicion;
	}

	public List<InfoPaciente> getPaciente() {
		return paciente;
	}

	public void setPaciente(List<InfoPaciente> paciente) {
		this.paciente = paciente;
	}
	
}
