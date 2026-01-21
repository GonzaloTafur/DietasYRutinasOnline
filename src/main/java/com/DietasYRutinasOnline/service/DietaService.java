package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.DTO.AlimentoDTO;
import com.DietasYRutinasOnline.entity.ENUM.ObjetivoEnum;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.ObjetivoRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;

@Service
public class DietaService {
    
    @Autowired
    DietaRepository dietaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ObjetivoRepository objetivoRepository;


    public List<Dieta> getEstado(Boolean estado){
        return dietaRepository.findAll();
    }

    public Dieta getCodigo(Long codigo){
        return dietaRepository.findById(codigo).get();
    }

    public Dieta getDetalle(Long codigo, AlimentoDTO al){
        return dietaRepository.findByCodigo(codigo);
    }


    public Dieta grabarDieta(Dieta d){
        //e = new Ejercicio();
        d.setEstado(true);
        return dietaRepository.save(d);
    }

    public Dieta actualizarDieta(Dieta d){
        d.setNombre(d.getNombre());
        d.setObjetivo(d.getObjetivo());
        d.setDescripcion(d.getDescripcion());
        d.setAlimento(d.getAlimento());
        d.setCondicion(d.getCondicion());
        d.setEstado(true);
        return dietaRepository.save(d);
    }

    public List<Dieta> getNutriologo(Nutriologo nutriologo) {
        return dietaRepository.findByNutriologo(nutriologo);
    }

    public List<Dieta> filtroDieta(Paciente paciente){
        Objetivo volumen = objetivoRepository.findByNombre("Volumen");
        Objetivo deficit = objetivoRepository.findByNombre("Deficit");

        if (pacienteRepository.findByObjetivo(volumen)!=null){ 
            return dietaRepository.findByObjetivo(ObjetivoEnum.V);
        }
        else if(pacienteRepository.findByObjetivo(deficit)!=null){
            return dietaRepository.findByObjetivo(ObjetivoEnum.D);
        }
        else{
            return dietaRepository.findByEstado(true);
        }
    }

    /*public List<Dieta> filtroDietaCondicion(Paciente paciente){
        
    }*/
}
