package com.DietasYRutinasOnline.rest_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.service.AlimentoService;
import com.DietasYRutinasOnline.service.CondicionService;

@RestController
@RequestMapping("/alimento/")
public class AlimentoRestController {

    @Autowired
    private AlimentoService alimentoService;

    @Autowired
    private CondicionService condicionService;
    
    @GetMapping("gestion_alimento")
    public List<Alimento> obtenerAlimentos(){
        return alimentoService.getEstado(true);
    }

    @GetMapping("{codigo}")
    public Alimento obtenerAlimento(@PathVariable Long codigo){
        return alimentoService.getCodigo(codigo);
    }

}
