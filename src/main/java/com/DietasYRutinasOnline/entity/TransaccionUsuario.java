package com.DietasYRutinasOnline.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TransaccionUsuario")
public class TransaccionUsuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtransaccion")
    private int idtransaccion;

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
    @JoinColumn(name = "idtipousu")
    private TipoUsuario tipo;

	public int getIdtransaccion() {
		return idtransaccion;
	}

	public void setIdtransaccion(int idtransaccion) {
		this.idtransaccion = idtransaccion;
	}

	public LocalDateTime getRegistro() {
		return registro;
	}

	public void setRegistro(LocalDateTime registro) {
		this.registro = registro;
	}

	public LocalDateTime getLogin() {
		return login;
	}

	public void setLogin(LocalDateTime login) {
		this.login = login;
	}

	public LocalDateTime getLogout() {
		return logout;
	}

	public void setLogout(LocalDateTime logout) {
		this.logout = logout;
	}

	public LocalDateTime getCambioPassword() {
		return cambioPassword;
	}

	public void setCambioPassword(LocalDateTime cambioPassword) {
		this.cambioPassword = cambioPassword;
	}

	public LocalDateTime getCambioCorreo() {
		return cambioCorreo;
	}

	public void setCambioCorreo(LocalDateTime cambioCorreo) {
		this.cambioCorreo = cambioCorreo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	
}
