package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.PacienteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("cuestionario")
public class CuestionarioController {

    @Autowired
    PacienteService pacienteService;

    @Autowired
    HistorialMedService historialMedService;

    @PostMapping("/grabar_cuestionario")
    public String grabarCuestionario(HttpSession session, Model model){
        Paciente paciente = (Paciente) session.getAttribute("paciente");
		if (paciente!=null) {
            pacienteService.grabarCuestionario(paciente);

            HistorialMed medidas = new HistorialMed();
            model.addAttribute("medidas", medidas);

            return "cuestionario_medidas";
        }
        
        return "iniciar_sesion";
    }

    @GetMapping("/")
    public String completarCuestionario(HttpSession session, Model model){
        HistorialMed hm = new HistorialMed();
        model.addAttribute("medidas", hm);
        return "cuestionario_medidas";
    }

    @PostMapping("/grabar_medidas")
    public String grabarMedidas(HttpSession session, HistorialMed hm){
        
        Paciente paciente = (Paciente) session.getAttribute("paciente");
        if (paciente!=null) {
            hm.setEstado(true);
	        hm.setFecha(LocalDateTime.now());
            hm.setPaciente(paciente);
            historialMedService.guardarHistorial(hm);
            return "redirect:/perfil/";
        }
        return "iniciar_sesion";
        
    }


}
