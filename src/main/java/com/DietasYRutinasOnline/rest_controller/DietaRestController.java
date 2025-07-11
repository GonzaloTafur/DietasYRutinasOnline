package com.DietasYRutinasOnline.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.DTO.AlimentoDTO;
import com.DietasYRutinasOnline.service.AlimentoService;
import com.DietasYRutinasOnline.service.DietaService;

@RestController
@RequestMapping("/dieta")
public class DietaRestController {

    @Autowired
    private AlimentoService alimentoService;
    
    @Autowired
    private DietaService dietaService;


    @GetMapping("/detalleDieta/{codigo}")
    public Dieta detalleDieta(@PathVariable Long codigo){
        AlimentoDTO al = (AlimentoDTO) alimentoService.getEstado(true);
        return dietaService.getDetalle(codigo, al);
    }
}
