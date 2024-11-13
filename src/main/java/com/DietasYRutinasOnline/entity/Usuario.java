package com.DietasYRutinasOnline.entity;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Usuario")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idusuario;
	
	@ManyToOne
    @JoinColumn(name = "idtipousu")
    private TipoUsuario tipousuario;

	/*@Override
	public String toString() {
		return "Usuario [idusuario=" + idusuario + ", tipousuario=" + tipousuario.toString() + ",\n nomusuario=" + nomusuario
				+ ", apepaterno=" + apepaterno + ", apematerno=" + apematerno + ", fechanacimiento=" + fechanacimiento
				+ ", nacionalidad=" + nacionalidad + ", sexo=" + sexo + ", estusuario=" + estusuario + ", correo="
				+ correo + ", password=" + password + "]";
	}*/

	@Column(name = "nomusuario")
    private String nombres;

    @Column(name = "apeusuario")
    private String apellidos;

    @Column(name = "fechanacimiento")
    private Date fechanacimiento;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "biografia")
    private String biografia;
    
    @Column(name = "estusuario")
    private String estado;

    @Column(name = "correo")
    private String correo;

    @Column(name = "password")
    private String password;

    /*@OneToMany
    @JoinTable(
        name = "TransaccionUsuario",
        joinColumns = @JoinColumn(name = "idusuario", referencedColumnName = "idusuario"),
        inverseJoinColumns = @JoinColumn(name = "idtransaccion", referencedColumnName = "idtransaccion")
    )
    private Set<TransaccionUsuario> transaccion;*/
    
    @OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
    private List<TransaccionUsuario> transaccion;

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public TipoUsuario getTipousuario() {
		return tipousuario;
	}

	public void setTipousuario(TipoUsuario tipousuario) {
		this.tipousuario = tipousuario;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<TransaccionUsuario> getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(List<TransaccionUsuario> transaccion) {
		this.transaccion = transaccion;
	}

    
}
