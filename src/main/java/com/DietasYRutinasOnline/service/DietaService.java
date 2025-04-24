package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.repository.DietaRepository;

@Service
public class DietaService {
    
    @Autowired
    DietaRepository dietaRepository;

    public List<Dieta> getEstado(Boolean estado){
        return dietaRepository.findAll();
    }

    public Dieta grabarDieta(Dieta d){
        //e = new Ejercicio();
        d.setEstado(true);
        return dietaRepository.save(d);
    }
}
