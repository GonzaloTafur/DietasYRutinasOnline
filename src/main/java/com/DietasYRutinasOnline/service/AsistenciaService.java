package com.DietasYRutinasOnline.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import jakarta.servlet.http.HttpSession;

@Service
public class AsistenciaService {
    
    @Autowired
    AsistenciaRepository asistenciaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ReunionRepository reunionRepository;

    public List<Asistencia> getEstado(Boolean estado){
        return asistenciaRepository.findAll();
    }

    public Asistencia confirmarAsistencia(HttpSession sesion, Long codigo, Asistencia as){

        Paciente paciente = (Paciente) sesion.getAttribute("usuario");

        pacienteRepository.findById(paciente.getCodigo())
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
	    
	    Reunion reunion = reunionRepository.findByCodigo(codigo);
	            //.orElseThrow(() -> new IllegalArgumentException("Reunión no encontrada"));

        as = asistenciaRepository.findByPacienteAndReunionAndEstado(paciente, reunion, true);

        if (as!=null) {
            System.out.println("Ya está en esa reunión");
        }
        as.setPaciente(paciente);
	    as.setReunion(reunion);
	    as.setEstado(true);
	    as.setFecha(LocalDateTime.now());
	    return asistenciaRepository.save(as);
    }

    public Asistencia cancelarAsistencia(HttpSession sesion, Asistencia as, Long codigo){
        Paciente paciente = (Paciente) sesion.getAttribute("usuario");
	    Reunion reunion = reunionRepository.findByCodigo(codigo);

	    as = asistenciaRepository.findByPacienteAndReunionAndEstado(paciente, reunion, true);
	    if (as==null) {
	    	System.out.println("No se pudo cancelar");	        
	    }

        as.setEstado(false);
        return asistenciaRepository.save(as);
    }

    public List<Asistencia> getReunion(Reunion re, Boolean estado){
        return asistenciaRepository.findByReunionAndEstado(re, true);
    }

    public Long asistenciaPaciente(Paciente suPerfil, Boolean estado){
        //Paciente suPerfil = pacienteRepository.findByCodigo(codigo);
        return asistenciaRepository.countByPacienteAndEstado(suPerfil, true);
    }
    
}
