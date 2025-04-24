package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.time.LocalTime;

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
public class Reunion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reunion")
    private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "nutriologo", referencedColumnName = "id_usuario")
    private Nutriologo nutriologo;
    
	@Column(name="motivo")
	private String motivo;
	
	@Column(name="dia_semana")
	private String dia;
	
	@Column(name="hora_inicio")
	private LocalTime hora;
	
	@Column(name="enlace")
	private String enlace;
	
	@Column(name="estado")
	private Boolean estado;
	
}
