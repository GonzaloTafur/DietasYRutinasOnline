package com.DietasYRutinasOnline.entity;

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
@Table(name="Dieta")
public class Dieta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddieta")
    private Long iddieta;

	@Column(name="nombre", length = 50)
	private String nombre;
	
	@Column(name="objetivo", length = 20)
	private String objetivo;
	
	@Column(name="descripcion")
	private Text descripcion;
	
	@Column(name="estado")
	private Boolean estado;

	@ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario nutriologo;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "DietaAlimento", 
      joinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="iddieta"), 
      inverseJoinColumns = @JoinColumn(name = "id_alimento", referencedColumnName ="idalimento"))
    private List<Alimento> alimento;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name = "DietaCondicion", 
      joinColumns = @JoinColumn(name = "id_dieta", referencedColumnName ="iddieta"), 
      inverseJoinColumns = @JoinColumn(name = "id_condicion", referencedColumnName ="idcondicion"))
    private List<Condicion> condicion;

	@ManyToMany(mappedBy = "dieta")
    private List<InfoPaciente> paciente;

}
