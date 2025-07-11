package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.DTO.AlimentoDTO;
import com.DietasYRutinasOnline.repository.AlimentoRepository;

@Service
public class AlimentoService {
    
    @Autowired
    AlimentoRepository alimentoRepository;

    public List<Alimento> getEstado(Boolean estado){
        return alimentoRepository.findAll();
    }
    
    public Alimento getCodigo(Long codigo){
        return alimentoRepository.findById(codigo).get();
    }

    public Alimento grabarAlimento(Alimento al){
        //al.setEstado(true);
        return alimentoRepository.save(al);
    }
    
    public Alimento actualizaAlimento(Alimento al){
        al.setNombre(al.getNombre());
        al.setNutrientes(al.getNutrientes());
        //al.setCondicion(al.getCondicion());
        al.setTipo(al.getTipo());
        al.setDescripcion(al.getDescripcion());
        al.setEstado(true);
        
        return alimentoRepository.save(al);
    }

    public List<String> cbxAlimento(){
        return alimentoRepository.findDistinctTipos();
    }

    public List<Alimento> getTipo(String tipo){
        return alimentoRepository.findByTipo(tipo);
    }
}
