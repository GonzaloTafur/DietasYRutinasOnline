package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.TransaccionUsuario;
import com.DietasYRutinasOnline.repository.NutriologoRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.TransUsuarioRepository;
import com.DietasYRutinasOnline.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/autenticacion/")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

	@Autowired
	private NutriologoRepository nutriologoRepository;

	@Autowired
	private RolRepository rolRepository;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private TransUsuarioRepository transUsuarioRepository;

    @RequestMapping(value="/validar_usuario", method= RequestMethod.POST)
	public String validarUsuario(
			HttpServletRequest request, 
			@RequestParam("correo")String correo, 
			@RequestParam("password")String password, 
			Model model) {
		
		//Usuario objUsuario = usuarioRepository.findByCorreoAndPassword(correo, password);
		Nutriologo nutriologo = usuarioService.getCorreoNutriologos(correo);
		Paciente paciente = usuarioService.getCorreoPaciente(correo);

		if (nutriologo!=null && passwordEncoder.matches(password, nutriologo.getPassword())) {
		//if (objUsuario!=null) {
			
			HttpSession sesion = request.getSession();
			sesion.setAttribute("nutriologo", nutriologo);

			// Buscando el rol "Superusuario" para redirigir al nutriologo a otra ventana
			Nutriologo superusuario = nutriologoRepository.findByRol(rolRepository.findByCodigo(1));

	        if(superusuario==nutriologo){
				TransaccionUsuario objTransUsuario = new TransaccionUsuario();
				objTransUsuario.setLogin(LocalDateTime.now());
				objTransUsuario.setUsuario(nutriologo);
				//objTransUsuario.setRol(vistaUsuario);
				transUsuarioRepository.save(objTransUsuario);

				return "admin/principal";
			}

	        TransaccionUsuario objTransUsuario = new TransaccionUsuario();
	        objTransUsuario.setLogin(LocalDateTime.now());
	        objTransUsuario.setUsuario(nutriologo);
	        //objTransUsuario.setRol(vistaUsuario);
            transUsuarioRepository.save(objTransUsuario);
	        
			return "menu";
		}
		else if (paciente!=null && passwordEncoder.matches(password, paciente.getPassword())) {
			//if (objUsuario!=null) {
				HttpSession sesion = request.getSession();
				sesion.setAttribute("paciente", paciente);
				model.addAttribute("paciente", paciente);
				
				//Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
				/*if(vistaUsuario!=null && vistaUsuario.getNombre().equals("Paciente")) {
					
					TransaccionUsuario objTransUsuario = new TransaccionUsuario();
					objTransUsuario.setLogin(LocalDateTime.now());
					//objTransUsuario.setUsuario(objUsuario);
					//objTransUsuario.setRol(vistaUsuario);
					transUsuarioRepository.save(objTransUsuario);
					
					return "menu";
				}*/
				
				TransaccionUsuario objTransUsuario = new TransaccionUsuario();
				objTransUsuario.setLogin(LocalDateTime.now());
				objTransUsuario.setUsuario(paciente);
				//objTransUsuario.setRol(vistaUsuario);
				transUsuarioRepository.save(objTransUsuario);
				
				return "menu";
		}
        else{
            //model.addAttribute("error", "El correo electronico y la contraseña no coinciden. Intenta de nuevo.");
		    return "iniciar_sesion";
        }
		
	}

}
