package com.DietasYRutinasOnline.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="Horario")
public class Horario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long codigo;
	
	@OneToOne
	@JoinColumn(name = "paciente", referencedColumnName = "id_usuario")
    private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "rutina", referencedColumnName = "id_rutina")
    private Rutina rutina;
    
	@Column(name="dia_semana", length = 20)
	private String dia;
	
	@Column(name="parte_dia", length = 20)
	private String parte;
	
	@Column(name="estado")
	private Boolean estado;
	
	@Column(name="descanso_serie", length = 20)
	private String descaSerie;
	
	@Column(name="descanso_ejercicio", length = 20)
	private String descaEjercicio;

}
