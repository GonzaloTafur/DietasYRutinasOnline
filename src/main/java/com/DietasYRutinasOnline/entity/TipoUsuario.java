package com.DietasYRutinasOnline.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TipoUsuario")
public class TipoUsuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipousu")
    private int idtipousu;

    @Override
	public String toString() {
		return "TipoUsuario [idtipousu=" + idtipousu + ", nomtipousu=" + nomtipousu + ", esttipousu=" + esttipousu
				+ "]";
	}

	@Column(name = "nomtipousu")
    private String nomtipousu;

    @Column(name = "esttipousu")
    private String esttipousu;

	public int getIdtipousu() {
		return idtipousu;
	}

	public void setIdtipousu(int idtipousu) {
		this.idtipousu = idtipousu;
	}

	public String getNomtipousu() {
		return nomtipousu;
	}

	public void setNomtipousu(String nomtipousu) {
		this.nomtipousu = nomtipousu;
	}

	public String getEsttipousu() {
		return esttipousu;
	}

	public void setEsttipousu(String esttipousu) {
		this.esttipousu = esttipousu;
	}
    
    
}
