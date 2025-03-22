package com.DietasYRutinasOnline.entity;

import java.sql.Date;
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
public class TransaccionUsuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtransaccion")
    private long idtransaccion;

	@Column(name="fecharegistro")
	private LocalDateTime registro;
	
	@Column(name="fechalogin")
	private LocalDateTime login;
	
	@Column(name="fechalogout")
	private LocalDateTime logout;
	
	@Column(name="cambioPassword")
	private LocalDateTime cambioPassword;
	
	@Column(name="cambioCorreo")
	private LocalDateTime cambioCorreo;
	
	@ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "idrol")
    private Rol rol;
	
}
