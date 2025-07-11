package com.DietasYRutinasOnline.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.PacienteService;

@RestController
@RequestMapping("/paciente")
public class PacienteRestController {

    @Autowired
    PacienteService pacienteService;

    @PutMapping("/grabar_cuestionario/{codigo}")
    public Paciente grabarCuestionario(@PathVariable Long codigo, @RequestBody Paciente paciente){

        paciente.setCodigo(codigo);
        return pacienteService.grabarCuestionario(paciente);
    }
    
}
