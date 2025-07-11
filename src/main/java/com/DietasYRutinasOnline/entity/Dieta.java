package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="Dieta")
public class Dieta implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dieta")
    private Long codigo;

	@Column(name="nombre", length = 50, nullable = false)
	private String nombre;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_objetivo", referencedColumnName = "id_objetivo")
    private Objetivo objetivo;
	
	@Column(name="descripcion", columnDefinition = "TEXT")
	private String descripcion;
	
	@Column(name="estado")
	private Boolean estado;

	@ManyToOne
    @JoinColumn(name = "id_nutriologo", referencedColumnName = "id_usuario")
    private Nutriologo nutriologo;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "DietaAlimento", 
      joinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="id_dieta"), 
      inverseJoinColumns = @JoinColumn(name = "id_alimento", referencedColumnName ="id_alimento"))
    private List<Alimento> alimento;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "DietaCondicion", 
      joinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="id_dieta"), 
      inverseJoinColumns = @JoinColumn(name = "id_condicion", referencedColumnName ="id_condicion"))
    private List<Condicion> condicion;

	//@ManyToMany(mappedBy = "dieta")
    //private List<HistorialMed> historialMedico;

}
