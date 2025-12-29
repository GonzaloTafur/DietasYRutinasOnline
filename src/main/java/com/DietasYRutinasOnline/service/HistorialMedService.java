package com.DietasYRutinasOnline.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;

@Service
public class HistorialMedService {
    
    @Autowired
    HistorialMedRepository historialMedRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public HistorialMed getCodigo(Long codigo){
        return historialMedRepository.findByCodigo(codigo);
    }

    public HistorialMed getEstado(Boolean estado){
        return historialMedRepository.findByEstado(true);
    }

    public HistorialMed getEstadoAndPaciente(Paciente paciente, Boolean estado){
        return historialMedRepository.findByPacienteAndEstado(paciente, estado);
    }

    public List<HistorialMed> getEstadoAll(Boolean estado){
        return historialMedRepository.findAll();
    }

    public HistorialMed desactivarHistorial(HistorialMed hm){
        hm.setEstado(false);
        return historialMedRepository.save(hm);
    }

    /*public HistorialMed guardarNuevoHistorial(HistorialMed hm, Paciente paciente){
        hm.setEstado(true);
        hm.setFecha(LocalDate.now());
        hm.setPaciente(paciente);
        historialMedRepository.save(hm);

        paciente.setHistorial(hm);
        pacienteRepository.save(paciente);

        return hm;
    }*/

    public HistorialMed guardarHistorial(HistorialMed hm){
        //hm.setEstado(true);
	    //hm.setFecha(LocalDateTime.now());
        return historialMedRepository.save(hm);
    }

    public HistorialMed editarHistorial(HistorialMed hm){
        //hm.setFrecEjercicios(hm.getFrecEjercicios());
	    hm.setPesoCorporal(hm.getPesoCorporal());
	    hm.setEstatura(hm.getEstatura());
	    hm.setPerimCintura(hm.getPerimCintura());
        hm.setPerimCadera(hm.getPerimCadera());
	    hm.setPerimMuslo(hm.getPerimMuslo());
	    hm.setPerimBrazo(hm.getPerimBrazo());

        return historialMedRepository.save(hm);
    }

    public HistorialMed getPaciente(Paciente paciente, Boolean estado){
        return historialMedRepository.findByPacienteAndEstado(paciente, estado);
    }

    public List<HistorialMed> getSegumiento(Paciente paciente){
        return historialMedRepository.findByPaciente(paciente);
    }
    
}