package com.DietasYRutinasOnline.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlimentoDTO {
    
    private Long codigo;

	private String nombre;
	
	private String nutrientes;
	
	private String tipo;
	
	private String descripcion;

	private Boolean estado;

}
