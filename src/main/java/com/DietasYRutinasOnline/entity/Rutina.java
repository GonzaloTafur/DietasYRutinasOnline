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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Rutina")
public class Rutina {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrutina")
    private int idrutina;

	@Column(name = "nomrutina")
    private String nombre;
	
    @Column(name = "tiporutina")
    private String tipo;
    
    @Column(name = "parteCuerpo")
    private String parteCuerpo;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "descrutina")
    private String descripcion;

    @Column(name = "estrutina")
    private String estado;

    //@ManyToOne
    //@JoinColumn(name = "idejercicio")
    //private Ejercicio ejercicio;
    
    @ManyToMany
    @JoinTable(
      name = "RutinaEjercicio", 
      joinColumns = @JoinColumn(name = "id_rutina", referencedColumnName ="idrutina"), 
      inverseJoinColumns = @JoinColumn(name = "id_ejercicio", referencedColumnName ="idejercicio"))
    private List<Ejercicio> ejercicio;
    
    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario nutriologo;
    
    //@ManyToMany(mappedBy = "rutina")
    //private List<Horario> horario;

	public int getIdrutina() {
		return idrutina;
	}

	public void setIdrutina(int idrutina) {
		this.idrutina = idrutina;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getParteCuerpo() {
		return parteCuerpo;
	}

	public void setParteCuerpo(String parteCuerpo) {
		this.parteCuerpo = parteCuerpo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
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

	public List<Ejercicio> getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(List<Ejercicio> ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Usuario getNutriologo() {
		return nutriologo;
	}

	public void setNutriologo(Usuario nutriologo) {
		this.nutriologo = nutriologo;
	}
	
}
