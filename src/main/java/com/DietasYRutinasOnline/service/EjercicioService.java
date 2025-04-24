package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Ejercicio;
import com.DietasYRutinasOnline.repository.EjercicioRepository;

@Service
public class EjercicioService {

    @Autowired
    EjercicioRepository ejercicioRepository;

    public List<Ejercicio> getEstado(Boolean estado){
        return ejercicioRepository.findAll();
    }

    public Ejercicio getCodigo(Long codigo){
        return ejercicioRepository.findById(codigo).get();
    }

    public Ejercicio guardaEjercicio(Ejercicio e){
        //e = new Ejercicio();
        e.setEstado(true);
        return ejercicioRepository.save(e);
    }
    
    public Ejercicio actualizaEjercicio(Ejercicio e){
        e.setNombre(e.getNombre());
        e.setGrupomuscular(e.getGrupomuscular());
        e.setTipo(e.getTipo());
        e.setSeries(e.getSeries());
        e.setRepeticiones(e.getRepeticiones());
        e.setDescripcion(e.getDescripcion());
        e.setEstado(true);
        
        return ejercicioRepository.save(e);
    }
}
