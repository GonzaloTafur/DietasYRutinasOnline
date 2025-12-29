package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.ENUM.FrecEjercicios;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    Paciente findByCorreo(String correo);

    //Paciente findByHistorialMedico(HistorialMed historialMedico);

    Paciente findByCodigo(Long codigo);

    Paciente findByObjetivo(Objetivo objetivo);

    Paciente findByFrecEjercicios(FrecEjercicios frecEjercicios);
    
}
