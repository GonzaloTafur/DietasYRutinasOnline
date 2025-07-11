package com.DietasYRutinasOnline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.repository.NutriologoRepository;

@Service
public class NutriologoService {
    
    @Autowired
    public NutriologoRepository nutriologoRepository;

    public Nutriologo getCodigo(long codigo){
        return nutriologoRepository.findByCodigo(codigo);
    }
}
