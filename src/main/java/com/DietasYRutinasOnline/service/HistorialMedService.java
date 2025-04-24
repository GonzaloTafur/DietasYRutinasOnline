package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;

@Service
public class HistorialMedService {
    
    @Autowired
    HistorialMedRepository historialMedRepository;

    public HistorialMed getEstado(Boolean estado){
        return historialMedRepository.findByEstado(estado);
    }

    public List<HistorialMed> getEstadoAll(Boolean estado){
        return historialMedRepository.findAll();
    }

    public HistorialMed guardar(HistorialMed historial){
        return historialMedRepository.save(historial);
    }
    
}