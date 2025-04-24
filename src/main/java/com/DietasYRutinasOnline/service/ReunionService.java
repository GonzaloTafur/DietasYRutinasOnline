package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.repository.ReunionRepository;

@Service
public class ReunionService {
    
    @Autowired
    ReunionRepository reunionRepository;

    public List<Reunion> getEstado(Boolean estado){
        return reunionRepository.findAll();
    }
}
