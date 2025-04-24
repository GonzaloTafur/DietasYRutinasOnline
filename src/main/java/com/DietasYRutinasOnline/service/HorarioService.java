package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.repository.HorarioRepository;

@Service
public class HorarioService {
    
    @Autowired
    HorarioRepository horarioRepository;

    public List<Horario> getEstado(Boolean estado){
        return horarioRepository.findAll();
    }
}
