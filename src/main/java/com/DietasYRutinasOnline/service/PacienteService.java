package com.DietasYRutinasOnline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.repository.CondicionRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.ObjetivoRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;

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

}
