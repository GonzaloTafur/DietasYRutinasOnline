package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.repository.CondicionRepository;

@Service
public class CondicionService {
    
    @Autowired
    CondicionRepository condicionRepository;

    public List<Condicion> getEstado(Boolean estado){
        return condicionRepository.findAll();
    }
    
}
