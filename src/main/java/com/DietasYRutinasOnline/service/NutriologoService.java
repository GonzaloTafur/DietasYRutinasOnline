package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.ENUM.RolEnum;
import com.DietasYRutinasOnline.repository.NutriologoRepository;

@Service
public class NutriologoService {
    
    @Autowired
    public NutriologoRepository nutriologoRepository;

    public Nutriologo getCodigo(long codigo){
        return nutriologoRepository.findByCodigo(codigo);
    }
    
    public Nutriologo getSuperUsuario(RolEnum rol){
        return nutriologoRepository.findByRol(rol.SU);
    }

    public Nutriologo guardarNutriologo(Nutriologo nutriologo){
        return nutriologoRepository.save(nutriologo);
    }
}
