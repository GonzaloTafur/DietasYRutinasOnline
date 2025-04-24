package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    Paciente findByCorreo(String correo);

    Paciente findByHistorialMedico(HistorialMed historialMedico);

    Paciente findByCodigo(Long codigo);
    
}
