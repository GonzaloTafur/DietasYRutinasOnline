package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;

@Repository
public interface HistorialMedRepository extends JpaRepository<HistorialMed, Long>{

    HistorialMed findByEstado(Boolean estado);

    HistorialMed findByCodigo(Long codigo);

    HistorialMed findByPacienteAndEstado(Paciente paciente, Boolean estado);
        
    
}
