package com.DietasYRutinasOnline.entity.ENUM;

public enum Dia {
    LU("Lunes"),
    MA("Martes"),
    MI("Miércoles"),
    JU("Jueves"),
    VI("Viernes"),
    SA("Sábado"),
    DO("Domingo");

    private final String nombre;

    Dia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
