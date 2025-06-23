package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Table(name="HistorialMedico")
public class HistorialMed implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long codigo;

	@Column(name="frec_ejercicios")
	private String frecEjercicios;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_condicion", referencedColumnName = "id_condicion")
    private Condicion condicion;
	
	@Column(name="peso_corporal")
	private BigDecimal pesoCorporal;
	
	@Column(name="estatura")
	private BigDecimal estatura;
	
	@Column(name="perimetro_cintura")
	private BigDecimal perimCintura;
	
	@Column(name="perimetro_cadera")
	private BigDecimal perimCadera;
	
	@Column(name="perimetro_muslo")
	private BigDecimal perimMuslo;
	
	@Column(name="perimetro_brazo")
	private BigDecimal perimBrazo;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_objetivo", referencedColumnName = "id_objetivo")
    private Objetivo objetivo;
	
	@Column(name="estado")
	private Boolean estado;
	
	@Column(name="fecha_modif")
	private LocalDateTime fecha;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "HistorialDieta", 
      joinColumns = @JoinColumn(name = "id_histotial", referencedColumnName ="id_historial"), 
      inverseJoinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="id_dieta"))
    private List<Dieta> dieta;

}
