package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.NutriologoRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired 
    UsuarioRepository usuarioRepository;

    @Autowired
    NutriologoRepository nutriologoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public List<Usuario> getEstado(Boolean estado){
        return usuarioRepository.findAll();
    }

    public List<Nutriologo> getEstadoNutriologo(Boolean estado){
        return nutriologoRepository.findAll();
    }

    public List<Usuario> getRol(Rol rol){
        return usuarioRepository.findAll();
    }

    public Nutriologo getCorreoNutriologos(@RequestParam("correo") String correo){
        return nutriologoRepository.findByCorreo(correo);
    }

    public Paciente getCorreoPaciente(@RequestParam("correo") String correo){
        return pacienteRepository.findByCorreo(correo);
    }

    public Paciente guardarPaciente(Paciente p) {
        return pacienteRepository.save(p);
    }

    public Nutriologo guardarNutriologo(Nutriologo n) {
        return nutriologoRepository.save(n);
    }

}
