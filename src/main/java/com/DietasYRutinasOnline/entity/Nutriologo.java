package com.DietasYRutinasOnline.entity;

import java.io.File;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
//@Table(name = "Nutriologo")
public class Nutriologo extends Usuario{
    
    /*
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

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "CV")
    private File CV;

    @ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Rol rol;

}
