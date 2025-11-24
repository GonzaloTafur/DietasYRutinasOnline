package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.DTO.UsuarioDTO;
import com.DietasYRutinasOnline.repository.NutriologoRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioService {
    
    @Autowired 
    private UsuarioRepository usuarioRepository;

    @Autowired
    NutriologoRepository nutriologoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getEstado(Boolean estado){
        return usuarioRepository.findAll();
    }

    public List<Nutriologo> getEstadoNutriologo(Boolean estado){
        return nutriologoRepository.findAll();
    }

    public List<Usuario> getRol(Rol rol){
        return usuarioRepository.findAll();
    }

    public Nutriologo getCorreoNutriologos(@RequestParam("correo") String correo){
        return nutriologoRepository.findByCorreo(correo);
    }

    public Paciente getCorreoPaciente(@RequestParam("correo") String correo){
        return pacienteRepository.findByCorreo(correo);
    }

    public Usuario getCorreo(@RequestParam("correo") String correo){
        return usuarioRepository.findByCorreo(correo);
    }

    /*public Usuario guardarUsuario(Usuario u){
        return usuarioRepository.save(u);
    }*/

    public Paciente guardarPaciente(Paciente p) {
        String hash = passwordEncoder.encode(p.getPassword());
		p.setPassword(hash);
        return pacienteRepository.save(p);
    }

    public Nutriologo guardarNutriologo(Nutriologo n) {
        String hash = passwordEncoder.encode(n.getPassword());
        //Rol rol = rolRepository.findByNombre("Estandar");
        
		n.setPassword(hash);
        //n.setRol(rol);
        return nutriologoRepository.save(n);
    }

    public Usuario nuevaContraseña(Usuario u, UsuarioDTO password){
        String encryptedPassword = passwordEncoder.encode(password.getPassword());
        // Pasarle la contraseña encriptada al usuario
        u.setPassword(encryptedPassword);
        return usuarioRepository.save(u);
    }

    public Usuario getCodigo(Long codigo) {
        return usuarioRepository.findByCodigo(codigo);
    }

}
