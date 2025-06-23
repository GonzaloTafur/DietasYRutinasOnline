package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.util.List;

import org.attoparser.dom.Text;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="Rutina")
public class Rutina implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutina")
    private Long codigo;
	
	  @Column(name = "nombre", length=60, nullable = false)
    private String nombre;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_objetivo", referencedColumnName = "id_objetivo")
    private Objetivo objetivo;
    
    @Column(name = "parte_cuerpo", length = 20)
    private String parteCuerpo;

    @Column(name = "nivel", length=25)
    private String nivel;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToMany
    @JoinTable(
      name = "RutinaEjercicio", 
      joinColumns = @JoinColumn(name = "id_rutina", referencedColumnName ="id_rutina"), 
      inverseJoinColumns = @JoinColumn(name = "id_ejercicio", referencedColumnName ="id_ejercicio"))
    private List<Ejercicio> ejercicio;
    
    @ManyToOne
    @JoinColumn(name = "id_nutriologo", referencedColumnName = "id_usuario")
    private Nutriologo nutriologo;

}
