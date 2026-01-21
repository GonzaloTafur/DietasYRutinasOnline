package com.DietasYRutinasOnline.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UsuarioDTO {

    private Long codigo;
    
    private String password;

    private String usuario;

    private String biografia;

}
