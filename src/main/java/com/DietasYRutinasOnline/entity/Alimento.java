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
@Table(name="Alimento")
public class Alimento implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimento")
    private Long codigo;

	@Column(name="nombre", length = 40)
	private String nombre;
	
	@Column(name="nutrientes")
	private String nutrientes;
	
	@Column(name="tipo", length = 20)
	private String tipo;
	
	@Column(name="descripcion", columnDefinition = "TEXT")
	private String descripcion;

	@ManyToMany(mappedBy = "alimento")
    private List<Dieta> dieta;

	/*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condicion", referencedColumnName = "id_condicion")
    private Condicion condicion;*/

	@Column(name="estado")
	private Boolean estado = true;
}
