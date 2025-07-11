package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.sql.Date;
import org.attoparser.dom.Text;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Usuario")
public abstract class Usuario implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long codigo;

	@Column(name = "nombres", length = 80)
    private String nombres;

    @Column(name = "apellidos", length = 80)
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private Date fechanacimiento;

    @Column(name = "nacionalidad", length = 40)
    private String nacionalidad;

    @Column(name = "sexo", length = 15)
    private String sexo;

    @Column(name = "biografia", columnDefinition = "TEXT")
    private String biografia;
    
    @Column(name = "usuario", length = 80)
    private String usuario;

    @Column(name = "correo", length = 100, unique = true)
    private String correo;

    @Column(name = "password", length = 225)
    private String password;

    @Column(name = "estado")
    private Boolean estado;
    
}
