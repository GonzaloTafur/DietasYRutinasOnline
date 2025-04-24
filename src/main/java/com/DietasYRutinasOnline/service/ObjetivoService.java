package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.repository.ObjetivoRepository;

@Service
public class ObjetivoService {
    
    @Autowired
    public ObjetivoRepository objetivoRepository;

    public List<Objetivo> getEstado(Boolean estado){
        return objetivoRepository.findAll();
    }

}
