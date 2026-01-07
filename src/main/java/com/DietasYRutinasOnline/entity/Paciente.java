package com.DietasYRutinasOnline.entity;

import java.util.List;

import com.DietasYRutinasOnline.entity.ENUM.FrecEjercicios;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    /*@Enumerated(EnumType.STRING)
    @Column(name="id_objetivo")
    private Objetivo objetivo;*/

    @Enumerated(EnumType.STRING)    
	@Column(name="frec_ejercicios")
	private FrecEjercicios frecEjercicios;

    //@Column(name="id_historial")
    //private Integer historial;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_historial", referencedColumnName = "id_historial")
    private HistorialMed historial;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "DietaPaciente", 
      joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName ="id_usuario"), 
      inverseJoinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="id_dieta"))
    private List<Dieta> dieta;

}
