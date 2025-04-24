package com.DietasYRutinasOnline.entity;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="Rol")
public class Rol implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long codigo;

	@Column(name = "nombre", length=50)
    private String nombre;

    @Column(name = "estado")
    //@ColumnDefault("True")
    private Boolean estado;
}
