package com.DietasYRutinasOnline.entity.DTO;

import com.DietasYRutinasOnline.entity.Dieta;

public class DietaDTO {
    
    private Dieta dieta;
    private boolean seguida;

    public DietaDTO(Dieta dieta, boolean seguida) {
        this.dieta = dieta;
        this.seguida = seguida;
    }

    public Dieta getDieta() {
        return dieta;
    }

    public boolean isSeguida() {
        return seguida;
    }
    
}
