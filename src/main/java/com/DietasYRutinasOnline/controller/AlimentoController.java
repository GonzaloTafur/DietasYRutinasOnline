package com.DietasYRutinasOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.service.AlimentoService;
import com.DietasYRutinasOnline.service.CondicionService;

@Controller
@RequestMapping("/alimento/")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @Autowired
    private CondicionService condicionService;
    
    @GetMapping("gestion_alimentos")
    public String gestionEjercicio(Model model){
        List<Alimento> lstAlimentos = alimentoService.getEstado(true);
        model.addAttribute("lstAlimentos", lstAlimentos);

        List<Condicion> lstCondicion = condicionService.getEstado(true);
        model.addAttribute("lstCondicion", lstCondicion);

        return "admin/alimentos.html";
    }

    @GetMapping("nuevo_alimento")
    public String nuevoAlimento(Model model){

        Alimento al = new Alimento();
        model.addAttribute("al", al);

        return "admin/registrar_alimento.html";
    }

    @PostMapping("grabar_alimento")
    public String grabarAlimento(Model model, Alimento al){
        //al.setEstado(true);
        alimentoService.grabarAlimento(al);
        return "redirect:/alimento/gestion_alimentos";
    }

    /*@GetMapping("editar_alimento/{codigo}")
    public String editarAlimento(@PathVariable Long codigo){
        alimentoService.getCodigo(codigo);
        return "admin/editar_alimento";
    }*/

    @GetMapping("editar_alimento/{codigo}")
    public String editarAlimento(@PathVariable Long codigo, Model model){
        Alimento al = alimentoService.getCodigo(codigo);
        model.addAttribute("al", al);
        return "admin/editar_alimento";
    }

    @PostMapping("actualizar_alimento")
    public String actualizarAlimento(Model model, Alimento al){
        alimentoService.actualizaAlimento(al);
        return "redirect:/alimento/gestion_alimentos";
    }
}
