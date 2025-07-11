package com.DietasYRutinasOnline.entity;

import java.io.File;
import java.sql.Date;

import org.attoparser.dom.Text;

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
//@Table(name = "Paciente")
public class Paciente extends Usuario{
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long codigo;
	
	@Column(name = "nombres", length = 80)
    private String nombres;

    @Column(name = "apellidos", length = 80)
    private String apellidos;

    @Column(name = "fechanacimiento")
    private Date fechanacimiento;

    @Column(name = "nacionalidad", length = 40)
    private String nacionalidad;

    @Column(name = "sexo", length = 15)
    private String sexo;

    @Column(name = "biografia")
    private Text biografia;

    @Column(name = "usuario", length = 80)
    private String usuario;

    @Column(name = "correo", length = 100, unique = true)
    private String correo;

    @Column(name = "password", length = 30)
    private String password;

    @Column(name = "estado")
    private Boolean estado;*/

    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_historial", referencedColumnName = "id_historial")
    private HistorialMed historialMedico;*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_objetivo", referencedColumnName = "id_objetivo")
    private Objetivo objetivo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_condicion", referencedColumnName = "id_condicion")
    private Condicion condicion;

	@Column(name="frec_ejercicios")
	private String frecEjercicios;

}
