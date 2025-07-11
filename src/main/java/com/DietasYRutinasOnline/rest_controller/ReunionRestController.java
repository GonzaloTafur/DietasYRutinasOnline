package com.DietasYRutinasOnline.rest_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.service.NutriologoService;
import com.DietasYRutinasOnline.service.ReunionService;

@RestController
@RequestMapping("/reunion")
public class ReunionRestController {

    @Autowired
    public NutriologoService nutriologoService;
    
    @Autowired
    public ReunionService reunionService;

    @GetMapping("/lista")
    public List<Reunion> listaReuniones(){
        Nutriologo nutriologo = nutriologoService.getCodigo(3);
        return reunionService.getNutriologo(nutriologo, true);
    }

}
