package com.DietasYRutinasOnline.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.ENUM.FrecEjercicios;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.service.CondicionService;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.ObjetivoService;
import com.DietasYRutinasOnline.service.PacienteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("cuestionario")
public class CuestionarioController {

    @Autowired
    PacienteService pacienteService;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    HistorialMedService historialMedService;

    @Autowired
    CondicionService condicionService;

    @Autowired
    ObjetivoService objetivoService;


    @GetMapping("/")
    public String completarCuestionario(HttpSession session, Model model){
        HistorialMed hm = new HistorialMed();
        model.addAttribute("medidas", hm);
        return "cuestionario";
    }

    @PostMapping("/grabar_cuestionario")
    public String grabarCuestionario(HttpSession session, Model model, @ModelAttribute("frmPaciente") Paciente frmPaciente){
        try{ 
            Paciente paciente = (Paciente) session.getAttribute("paciente");
            if (paciente!=null) {
                //pacienteService.grabarCuestionario(paciente);
                paciente.setObjetivo(frmPaciente.getObjetivo());
                paciente.setCondicion(frmPaciente.getCondicion());
                paciente.setFrecEjercicios(frmPaciente.getFrecEjercicios());
                pacienteRepository.save(paciente);
                System.out.println(
                    "Cuestionario grabado para el paciente " + paciente.getCodigo() + ": " 
                    + paciente.getCondicion().getNombre() 
                    + ", " + paciente.getObjetivo() + ", " 
                    + paciente.getFrecEjercicios());

                HistorialMed medidas = new HistorialMed();
                model.addAttribute("medidas", medidas);

                return "cuestionario_medidas";
            }
            
            return "iniciar_sesion";
        } 
        catch (Exception e){
            System.out.println("Error al grabar el cuestionario: " + e.getMessage());
            model.addAttribute("error", "Error al grabar el cuestionario. Intente nuevamente.");
            List<FrecEjercicios> cbxFrecuencia = Arrays.asList(FrecEjercicios.values());
			model.addAttribute("cbxFrecuencia", cbxFrecuencia);
			List<Condicion> cbxCondiciones = condicionService.getEstado(true);
			model.addAttribute("cbxCondiciones", cbxCondiciones);
			List<Objetivo> ckbObjetivos = objetivoService.getEstado(true);
			model.addAttribute("ckbObjetivos", ckbObjetivos);
            return "cuestionario";
        }
    }

    @PostMapping("/grabar_medidas")
    public String grabarMedidas(HttpSession session, Model model, @ModelAttribute("medidas") HistorialMed hm){
        try {
           Paciente paciente = (Paciente) session.getAttribute("paciente");
            if (paciente!=null) {
                hm.setEstado(true);
                hm.setFecha(LocalDate.now());
                hm.setPaciente(paciente);
                historialMedService.guardarHistorial(hm);

                paciente.setHistorial(hm);
                pacienteService.guardarPaciente(paciente);
                return "redirect:/home/";
            }
            return "iniciar_sesion";   
        } 
        catch (Exception e) {
           System.out.println("Error al grabar las medidas: " + e.getMessage());
           model.addAttribute("error", "Error al grabar el cuestionario. Intente nuevamente.");
           return "cuestionario_medidas";
        }
    }

}
