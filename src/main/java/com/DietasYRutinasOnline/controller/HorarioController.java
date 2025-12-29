package com.DietasYRutinasOnline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.ENUM.Dia;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.HorarioService;
import com.DietasYRutinasOnline.service.RutinaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/horario")
public class HorarioController {
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	public RutinaService rutinaService;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	ReunionRepository reunionRepository;
	
	@Autowired
	public HorarioService horarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;

	@PostMapping("/grabarHorario")
	public String grabarHorario(
	        HttpSession sesion, 
	        @ModelAttribute("objHorario") Horario h,
	        @RequestParam("dia") Dia dia,
	        @RequestParam("parte") String parte,
	        Model model) {

	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");
	    if (paciente!=null) {
			h.setPaciente(paciente);
			h.setEstado(true);
			horarioService.grabarHorario(paciente, h, dia, parte, model);

			return "redirect:/home/ver_horario";
		}
		else{
			return "iniciar_sesion";
		}
	}
	
	@GetMapping("/eliminar_hora/{codigo}")
	public String eliminarHora(HttpSession sesion, Horario h,
			@ModelAttribute ("codigo") Long codigo, 
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	//Horario h = horarioRepository.findByCodigo(codigo);
	    	//horaEliminada.setEstado(false);
	    	//horarioRepository.save(horaEliminada);
	    	horarioService.eliminar(h, codigo);
			model.addAttribute("eliminado", "El horario ha eliminado con exito");
	    	
	        /*Rol vistaUsuario = objUsuario.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);*/
	    }
		/*List<Rutina> listaRutinas = rutinaRepository.findAll();
		model.addAttribute("listaRutinas", listaRutinas);
		
		List<Horario> miHorario = horarioRepository.findByPacienteAndEstado(objUsuario, true);
		model.addAttribute("miHorario", miHorario);*/
		
		Horario nuevoHorario = new Horario();
		model.addAttribute("objHorario", nuevoHorario);
		return "redirect:/home/ver_horario";
	}
	
}
