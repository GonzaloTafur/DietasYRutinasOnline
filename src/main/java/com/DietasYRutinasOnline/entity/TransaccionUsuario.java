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
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "rol", referencedColumnName = "id_rol")
    private Rol rol;
	
}
