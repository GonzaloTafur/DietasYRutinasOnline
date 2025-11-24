package com.DietasYRutinasOnline.entity.ENUM;

public enum RolEnum {
    SU("Superusuario"), 
    E("Estándar");

    private final String nombre;

    RolEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
