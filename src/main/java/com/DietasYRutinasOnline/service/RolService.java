package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.repository.RolRepository;



@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public List<Rol> getEstado(Boolean estado){
        return rolRepository.findAll();
    }

    public Rol getNombre(String nombre){
        return rolRepository.findByNombre(nombre);
    }

    public Rol getId(Long idrol){
        return rolRepository.findByCodigo(idrol);
    }

}
