package com.DietasYRutinasOnline.entity.ENUM;

public enum ObjetivoEnum {
    V("Volumen"), 
    D("Deficit");

    private final String nombre;

    ObjetivoEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}