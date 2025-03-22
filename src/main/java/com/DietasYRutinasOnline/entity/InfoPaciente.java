package com.DietasYRutinasOnline.entity;

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
@Table(name="InfoPaciente")
public class InfoPaciente {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinfopaciente")
    private Long idinfopaciente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idusuario")
    private Usuario paciente;

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
	
	@Column(name="estado")
	private Boolean estado;
	
	@Column(name="fechainfo")
	private LocalDateTime fecha;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "PacienteDieta", 
      joinColumns = @JoinColumn(name = "id_paciente", referencedColumnName ="idinfopaciente"), 
      inverseJoinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="iddieta"))
    private List<Dieta> dieta;

}
