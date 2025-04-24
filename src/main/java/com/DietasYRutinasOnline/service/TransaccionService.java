package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.repository.TransaccionRepository;

@Service
public class TransaccionService {
    
    @Autowired
    TransaccionRepository transaccionRepository;

    public List<Transaccion> getEstado(Boolean estado){
        return transaccionRepository.findAll();
    }
}
