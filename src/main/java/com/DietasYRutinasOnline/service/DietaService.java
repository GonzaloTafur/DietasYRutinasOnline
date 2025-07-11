package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.DTO.AlimentoDTO;
import com.DietasYRutinasOnline.repository.DietaRepository;

@Service
public class DietaService {
    
    @Autowired
    DietaRepository dietaRepository;

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
        return dietaRepository.save(d);
    }

    public List<Dieta> getNutriologo(Usuario suPerfil) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNutriologo'");
    }
}
