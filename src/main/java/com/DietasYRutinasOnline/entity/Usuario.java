package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.sql.Date;
import org.attoparser.dom.Text;
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
@Data
@NoArgsConstructor
@Table(name="Usuario")
public class Usuario implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long idusuario;
	
	@ManyToOne
    @JoinColumn(name = "idrol")
    private Rol rol;

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
    
    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "correo", length = 100)
    private String correo;

    @Column(name = "password", length = 30)
    private String password;
    
}
