package com.DietasYRutinasOnline.entity;

import java.time.LocalTime;
import java.util.List;

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
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhorario")
    private Long idhorario;
	
	@OneToOne
	@JoinColumn(name = "idusuario")
    private Usuario paciente;
	
	@ManyToOne
	@JoinColumn(name = "idrutina")
    private Rutina rutina;
    
	@Column(name="diaSemana", length = 20)
	private String dia;
	
	@Column(name="periodo", length = 20)
	private String periodo;
	
	@Column(name="estado")
	private Boolean estado;
	
	@Column(name="descansoSerie", length = 20)
	private String descaSerie;
	
	@Column(name="descaEjercicio", length = 20)
	private String descaEjercicio;

}
