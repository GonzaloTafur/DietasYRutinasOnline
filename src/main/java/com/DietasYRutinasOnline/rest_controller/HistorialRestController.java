package com.DietasYRutinasOnline.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.PacienteService;
import com.DietasYRutinasOnline.service.UsuarioService;

@RestController
@RequestMapping("/medidas")
public class HistorialRestController {

    @Autowired
    PacienteService pacienteService;
    
    @Autowired
    HistorialMedService historialMedService;


    @PostMapping("/grabar_cuestionario/{codigo}")
    public HistorialMed grabarMedidas(@PathVariable Long codigo, @RequestBody HistorialMed hm, @RequestBody Paciente paciente){
        paciente = pacienteService.grabarCuestionario(paciente);
        return historialMedService.guardarHistorial(hm);
    }

    @GetMapping("/lista_medidas")
    public HistorialMed listaMedidas(HistorialMed hm){
        Paciente paciente = pacienteService.getCodigo(5);
        return historialMedService.getPaciente(paciente, true);
    }
}
