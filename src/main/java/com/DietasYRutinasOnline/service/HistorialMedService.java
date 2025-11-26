package com.DietasYRutinasOnline.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;

@Service
public class HistorialMedService {
    
    @Autowired
    HistorialMedRepository historialMedRepository;

    public HistorialMed getCodigo(Long codigo){
        return historialMedRepository.findByCodigo(codigo);
    }

    public HistorialMed getEstado(Boolean estado){
        return historialMedRepository.findByEstado(true);
    }

    public List<HistorialMed> getEstadoAll(Boolean estado){
        return historialMedRepository.findAll();
    }

    public HistorialMed desactivarHistorial(HistorialMed hm){
        hm.setEstado(false);
        return historialMedRepository.save(hm);
    }

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