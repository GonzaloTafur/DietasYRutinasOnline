package com.DietasYRutinasOnline.rest_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.DTO.UsuarioDTO;
import com.DietasYRutinasOnline.service.UsuarioService;

@RestController
@RequestMapping("/usuario/")
public class UsuarioRestController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("ver_nutriologos")
    public List<Nutriologo> verNutriologos(){
        return usuarioService.getEstadoNutriologo(true);   
    }

    @PostMapping("insertar_paciente")
    public Paciente add(@RequestBody Paciente p){
        return usuarioService.guardarPaciente(p);
    }

    @PostMapping("insertar_nutriologo")
    public Nutriologo add(@RequestBody Nutriologo n){
        return usuarioService.guardarNutriologo(n);
    }

    /*@PutMapping("actualizar_password/{codigo}")
    public Usuario update(@PathVariable Long codigo, @RequestBody Usuario u){
        String encryptedPassword = passwordEncoder.encode(u.getPassword());

        // Obtener el id del usuario al que le vas a cambiar la contraseña
        u = usuarioService.getCodigo(codigo);

        // Pasarle la contraseña encriptada al usuario
        u.setPassword(encryptedPassword);
        
        return usuarioService.nuevaContraseña(u);
    }*/
    
    @PutMapping("actualizar_password/{codigo}")
    public Usuario actualizarPassword(@PathVariable Long codigo, @RequestBody UsuarioDTO password){
        String encryptedPassword = passwordEncoder.encode(password.getPassword());
        
        // Obtener el id del usuario al que le vas a cambiar la contraseña
        Usuario u = usuarioService.getCodigo(codigo);
        
        // Pasarle la contraseña encriptada al usuario
        u.setPassword(encryptedPassword);
        
        return usuarioService.nuevaContraseña(u);
    }
}
