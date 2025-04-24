package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;

@Service
public class AsistenciaService {
    
    @Autowired
    AsistenciaRepository asistenciaRepository;

    public List<Asistencia> getEstado(Boolean estado){
        return asistenciaRepository.findAll();
    }
}
