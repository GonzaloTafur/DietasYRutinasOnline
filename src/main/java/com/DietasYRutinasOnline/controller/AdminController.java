package com.DietasYRutinasOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.DietasYRutinasOnline.entity.Ejercicio;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.service.EjercicioService;
import com.DietasYRutinasOnline.service.RolService;
import com.DietasYRutinasOnline.service.UsuarioService;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    EjercicioService ejercicioService;


    @GetMapping("gestion_ejercicios")
    public String gestionEjercicio(Model model){
        List<Ejercicio> lstEjercicios = ejercicioService.getEstado(true);
        model.addAttribute("lstEjercicios", lstEjercicios);
        return "admin/ejercicios.html";
    }

    @GetMapping("nuevo_ejercicio")
    public String nuevoEjercicio(Model model){
        Ejercicio e = new Ejercicio();
        model.addAttribute("e", e); 
        return "admin/registrar_ejercicio.html";
    }

    @PostMapping("grabar_ejercicio")
    public String grabarEjercicio(Model model, Ejercicio e){
        //e.setEstado(true);
        ejercicioService.guardaEjercicio(e);
        //redirect:"ejercicios";
        return "redirect:/admin/gestion_ejercicios";
    }

    @GetMapping("editar_ejercicio/{codigo}")
    public String editarEjercicio(@PathVariable Long codigo, Model model){
        Ejercicio e = ejercicioService.getCodigo(codigo);
        model.addAttribute("e", e);
        return "admin/editar_ejercicio";
    }

    @PostMapping("actualizar_ejercicio")
    public String actualizarEjercicio(Model model, Ejercicio e){
        ejercicioService.actualizaEjercicio(e);
        return "redirect:/admin/gestion_ejercicios";
        
    }

    //---------------------------------------------------------------------------------------------
    @GetMapping("gestion_usuarios")
    public String gestionUsuarios(Model model){
        List<Usuario> lstUsuarios = usuarioService.getEstado(true);
        model.addAttribute("lstUsuarios", lstUsuarios);
        //List<Nutriologo> lstNutri = usuarioService.getEstadoNutriologo(true);
        //model.addAttribute("lstNutri", lstNutri);

        //List<Rol> lstRoles = rolService.getEstado(true);
        //model.addAttribute("lstRoles", lstRoles);
        return "admin/usuario/usuarios.html";
    }
    
    @GetMapping("nuevo_rol")
    public String nuevoRol(Model model){
        Rol rol = new Rol();
        model.addAttribute("rol", rol); 
        return "admin/usuario/registrar_rol.html";
    }

    @PostMapping("grabar_rol")
    public String grabarRol(Model model, Rol rol){
        //e.setEstado(true);
        //rolService.guardaRol(rol);
        //redirect:"ejercicios";
        return "redirect:/admin/gestion_usuarios";
    }
}
