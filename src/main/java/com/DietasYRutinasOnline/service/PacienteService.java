package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.repository.CondicionRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.ObjetivoRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;

import jakarta.transaction.Transactional;

@Service
public class PacienteService {

    @Autowired
    HistorialMedRepository historialMedRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ObjetivoRepository objetivoRepository;

    @Autowired
    CondicionRepository condicionRepository;


    public Paciente getCodigo(long codigo){
        return pacienteRepository.findByCodigo(codigo);
    }
    
    public Paciente grabarCuestionario(Paciente paciente){
        paciente.setObjetivo(paciente.getObjetivo());
        paciente.setCondicion(paciente.getCondicion());
        paciente.setFrecEjercicios(paciente.getFrecEjercicios());
        /*paciente.setObjetivo(objetivoRepository.findById().get);
        paciente.setCondicion(condicionRepository.findById(dto.getCondicion()).orElse(null));
        paciente.setFrecEjercicios(paciente.getFrecEjercicios());*/
        return pacienteRepository.save(paciente);
    }

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }  
    
    public Paciente getObjetivo(Objetivo objetivo){
        return pacienteRepository.findByObjetivo(objetivo);
    }

    public Paciente seguirDieta(Paciente paciente, Dieta dieta){
        paciente.getDieta().add(dieta);
        return pacienteRepository.save(paciente);
    }
    
    public Paciente dejarSeguirDieta(Paciente paciente, Dieta dieta){
        paciente.getDieta().remove(dieta);
        return pacienteRepository.save(paciente);
    }
}
