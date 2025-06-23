package com.DietasYRutinasOnline.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name="TransaccionUsuario")
public class TransaccionUsuario implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transusu")
    private long codigo;

	@Column(name="fecha")
	private LocalDateTime registro;
	
	@Column(name="login")
	private LocalDateTime login;
	
	@Column(name="logout")
	private LocalDateTime logout;
	
	@Column(name="cambio_password")
	private LocalDateTime cambioPassword;
	
	@Column(name="cambio_correo")
	private LocalDateTime cambioCorreo;
	
	@ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
	
	/*@ManyToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Rol rol;*/
	
}
