package com.DietasYRutinasOnline.entity;

import java.util.List;

import org.attoparser.dom.Text;

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
public class Rutina {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrutina")
    private Long idrutina;
	
	@Column(name = "nombre", length=50)
    private String nombre;
	
    @Column(name = "tipo", length = 20)
    private String tipo;
    
    @Column(name = "parteCuerpo", length = 20)
    private String parteCuerpo;

    @Column(name = "nivel", length=25)
    private String nivel;

    @Column(name = "descripcion")
    private Text descripcion;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToMany
    @JoinTable(
      name = "RutinaEjercicio", 
      joinColumns = @JoinColumn(name = "id_rutina", referencedColumnName ="idrutina"), 
      inverseJoinColumns = @JoinColumn(name = "id_ejercicio", referencedColumnName ="idejercicio"))
    private List<Ejercicio> ejercicio;
    
    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario nutriologo;

}
