package com.DietasYRutinasOnline.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.ENUM.Dia;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.NutriologoRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ReunionService {
    
    @Autowired
    ReunionRepository reunionRepository;

    @Autowired
    NutriologoRepository nutriologoRepository;

    @Autowired
    AsistenciaRepository asistenciaRepository;


    public Reunion getCodigo(Long codigo){
        return reunionRepository.findByCodigo(codigo);
    }

    public List<Reunion> getEstado(Boolean estado){
        return reunionRepository.findAll();
    }

    public List<Reunion> verReuniones(Model model, Long codigo, Paciente paciente){

        //Paciente paciente = (Paciente) sesion.getAttribute("paciente");
        
        Nutriologo nutriologoReunion = nutriologoRepository.findByCodigo(codigo);
		model.addAttribute("nutriologoReunion", nutriologoReunion);

		List<Reunion> listaReuniones = reunionRepository.findByNutriologoAndEstado(nutriologoReunion, true);
		//model.addAttribute("listaReuniones", listaReuniones);

		Map<Long, Boolean> estadoAsistencia = listaReuniones.stream().collect(
			Collectors.toMap(
				Reunion::getCodigo, objReunion -> {
					Asistencia asistencia = asistenciaRepository.findByPacienteAndReunionAndEstado(paciente, objReunion, true);
					return asistencia!=null ? asistencia.getEstado() : false;
				}
			));
		model.addAttribute("estadoAsistencia", estadoAsistencia);
        
        return reunionRepository.findAll();

    }

    public List<Reunion> getNutriologo(Nutriologo nutriologo,  Boolean estado) {
        return reunionRepository.findByNutriologoAndEstado(nutriologo, true);
    }

    public Reunion grabarReunion(HttpSession sesion, Dia dia, Reunion re){
        Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");

        Reunion reunionUsuario = reunionRepository.findByNutriologoAndDiaAndEstado(nutriologo, dia, true);

        if(reunionUsuario!=null) {
            System.out.println("No se ha podido grabar");
        }
        re.setNutriologo(nutriologo);
	    re.setEstado(true);
        return reunionRepository.save(re);
    }

    public Reunion eliminarReunion(Reunion re){
        re.setEstado(false);
        return reunionRepository.save(re);
    }

}
