package com.DietasYRutinasOnline.entity.ENUM;

public enum FrecEjercicios {
    UNO("1/5"), 
    DOS("2/5"), 
    TRES("3/5"), 
    CUATRO("4/5"), 
    CINCO("5/5");

    private final String nombre;

    FrecEjercicios(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
