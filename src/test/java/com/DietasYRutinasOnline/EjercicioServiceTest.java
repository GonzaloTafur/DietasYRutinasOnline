package com.DietasYRutinasOnline;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.DietasYRutinasOnline.entity.Ejercicio;

public class EjercicioServiceTest {
    
    @Test
    public static void test1(){
        Ejercicio e = new Ejercicio();
        e.setNombre("Peso muerto rumano");
        e.setDescripcion("Femorales");
        e.setTipo("Tren Inferior");
        e.setRepeticiones("6 a 8");
        e.setSeries(3);
        e.setDescripcion("Baja la barra con las piernas ligeramente flexionadas y la espalda recta, hasta sentir el estiramiento en los isquiotibiales. Sube contrayendo glúteos y femorales.");
        e.setEstado(true);							
        
        Assertions.assertThat(e);
    }
}
