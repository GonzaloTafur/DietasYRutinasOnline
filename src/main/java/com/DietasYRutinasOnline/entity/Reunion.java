package com.DietasYRutinasOnline.entity;


import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

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
@Table(name="Reunion")
public class Reunion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreunion")
    private Long idreunion;
	
	@ManyToOne
	@JoinColumn(name = "idusuario")
    private Usuario nutriologo;
    
	@Column(name="motivo")
	private String motivo;
	
	@Column(name="diaSemana")
	private String dia;
	
	@Column(name="horaInicio")
	private LocalTime hora;
	
	@Column(name="enlace")
	private String enlace;
	
	@Column(name="estado")
	private Boolean estado;
	
}
