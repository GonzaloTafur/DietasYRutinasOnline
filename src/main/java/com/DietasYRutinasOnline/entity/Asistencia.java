package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
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
public class Asistencia implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long codigo;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reunion", referencedColumnName = "id_reunion")
    private Reunion reunion;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente", referencedColumnName = "id_usuario")
    private Paciente paciente;
	
	@Column(name="fecha")
	private LocalDateTime fecha;
	
	@Column(name="estado")
	private Boolean estado;

}
