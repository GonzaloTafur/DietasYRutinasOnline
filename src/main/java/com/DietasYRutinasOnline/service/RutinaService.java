package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.repository.RutinaRepository;

@Service
public class RutinaService {
    
    @Autowired
    RutinaRepository rutinaRepository;

    public List<Rutina> getEstado(Boolean estado){
        return rutinaRepository.findAll();
    }

    /*public List<Rutina> getTipo(String tipo){
        return rutinaRepository.findAll();
    }*/

    public Rutina grabarRutina(Rutina ru){
        //e = new Ejercicio();
        ru.setEstado(true);
        return rutinaRepository.save(ru);
    }
    

}
