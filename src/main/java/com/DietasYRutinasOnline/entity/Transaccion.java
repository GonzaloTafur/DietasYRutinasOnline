package com.DietasYRutinasOnline.entity;

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
@Table(name="TransaccionGeneral")
public class Transaccion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtrangeneral")
    private int idregistro;

	@Column(name="fecha")
	private LocalDateTime fecha;
	
	@Column(name="tipo")
	private String tipo;
	
	@ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "idrutina")
    private Rutina rutina;
	
	@ManyToOne
	@JoinColumn(name = "iddieta")
    private Dieta dieta;
	
	@ManyToOne
    @JoinColumn(name = "idhorario")
    private Horario horario;
	
	@ManyToOne
    @JoinColumn(name = "idinfopaciente")
    private InfoPaciente infopaciente;

	@ManyToOne
    @JoinColumn(name = "idreunion")
    private Reunion reunion;
	
	@ManyToOne
    @JoinColumn(name = "idasistencia")
    private Asistencia asistencia;

}
