package com.DietasYRutinasOnline.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
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
@NoArgsConstructor
@Data
@Table(name="Asistencia")
public class Asistencia {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasistencia")
    private Long idasistencia;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idreunion")
    private Reunion reunion;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idusuario")
    private Usuario paciente;
	
	@Column(name="fecasistencia")
	private LocalDateTime fecha;
	
	@Column(name="estasistencia")
	private Boolean estado;

}
