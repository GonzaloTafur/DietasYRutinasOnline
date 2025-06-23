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
@Table(name="Notificacion")
public class Notificacion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long codigo;
    
	@ManyToOne
	@JoinColumn(name = "id_transaccion", referencedColumnName = "id_transaccion")
    private Transaccion transaccion;
	
	@Column(name="rol_noti")
	private String rol;
	
	@Column(name="mensaje", columnDefinition = "TEXT")
	private String mensaje;
	
	@Column(name="estnoti")
	private Boolean estado;
	
	@Column(name="timestamp")
	private LocalDateTime timestamp;
	
	@Column(name="diaSemana")
	private String dia;

}
