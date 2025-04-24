package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.TransaccionUsuario;
import com.DietasYRutinasOnline.repository.TransUsuarioRepository;

@Service
public class TransUsuarioService {
    
    @Autowired
    TransUsuarioRepository transUsuarioRepository;

    public List<TransaccionUsuario> getEstado(Boolean estado){
        return transUsuarioRepository.findAll();
    }
}
