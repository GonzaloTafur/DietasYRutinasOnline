package com.DietasYRutinasOnline.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class HorarioService {
    
    @Autowired
    HorarioRepository horarioRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public List<Horario> getEstado(Boolean estado){
        return horarioRepository.findAll();
    }

    public List<Horario> horarioPerfil(Long codigo){
        Paciente suPerfil = pacienteRepository.findByCodigo(codigo);
	    if(suPerfil==null) {
	    	//List<Horario> suHorario = horarioRepository.findByPacienteAndEstado(suPerfil, true);
	    	//model.addAttribute("suHorario", suHorario);
	    }
        return horarioRepository.findByPacienteAndEstado(suPerfil, true);
    }

    public Horario grabarHorario(HttpSession sesion, Model model, Horario h, String dia, String parte){

        Paciente paciente = (Paciente) sesion.getAttribute("usuario");
        //h.setPaciente(objUsuario);
	    h.setEstado(true);
	    	
	    Horario conflictoHorario = horarioRepository.findByPacienteAndDiaAndParteAndEstado(paciente, dia, parte, true);
	    Horario conflictoDia = horarioRepository.findByPacienteAndDiaAndEstado(paciente, dia, true);

        if(conflictoHorario!=null) {	    		
	    		model.addAttribute("error", "El horario está en conflicto con otro existente");
	    }
	    else if(conflictoDia!=null){
	    	model.addAttribute("error", "Te sugerimos que no elijas más de una rutina para un mismo día");
	    }

	    //objHorario.setEstado(true);

	    model.addAttribute("exito", "Se añadió con éxito");
	    		
	    /*Transaccion objTransaccion = new Transaccion();
	    objTransaccion.setFecha(LocalDateTime.now());
	    objTransaccion.setTipo("CREACIÓN");
	    objTransaccion.setUsuario(objUsuario);
	    objTransaccion.setHorario(objHorario);
	    transaccionRepository.save(objTransaccion);*/
	    
        return horarioRepository.save(h);
    }

    public Horario eliminar(Horario h, Long codigo){
        horarioRepository.findByCodigo(codigo);
        h.setEstado(false);
        return horarioRepository.save(h);
    }
}
