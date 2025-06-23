package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="Transaccion")
public class Transaccion implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private int idregistro;

	@Column(name="fecha")
	private LocalDateTime fecha;
	
	@Column(name="tipo")
	private String tipo;
	
	@ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "id_rutina")
    private Rutina rutina;
	
	@ManyToOne
	@JoinColumn(name = "id_dieta")
    private Dieta dieta;
	
	@ManyToOne
    @JoinColumn(name = "id_horario")
    private Horario horario;
	
	@ManyToOne
    @JoinColumn(name = "id_historial")
    private HistorialMed historialMedico;

	@ManyToOne
    @JoinColumn(name = "id_reunion")
    private Reunion reunion;
	
	@ManyToOne
    @JoinColumn(name = "id_asistencia")
    private Asistencia asistencia;

}
