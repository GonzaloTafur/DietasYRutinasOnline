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
	        @ModelAttribute("objHorario") Horario objHorario,
	        @RequestParam("dia") String dia,
	        @RequestParam("parte") String parte,
	        Model model) {

	    Paciente paciente = (Paciente) sesion.getAttribute("usuario");
	    if (paciente!=null) {
	    	/*objHorario.setPaciente(paciente);
	    	objHorario.setEstado(true);
	    	
	    	Horario conflictoHorario = horarioRepository.findByPacienteAndDiaAndParteAndEstado(paciente, dia, parte, true);
	    	Horario conflictoDia = horarioRepository.findByPacienteAndDiaAndEstado(paciente, dia, true);
	    	
	    	if(conflictoHorario!=null) {	    		
	    		model.addAttribute("error", "El horario está en conflicto con otro existente");
	    	}
	    	else if(conflictoDia!=null){
	    		model.addAttribute("error", "Te sugerimos que no elijas más de una rutina para un mismo día");
	    	}
	    	else {
	    		//objHorario.setEstado(true);
	    		horarioRepository.save(objHorario);
	    		model.addAttribute("exito", "Se añadió con éxito");
	    		
	    		Transaccion objTransaccion = new Transaccion();
	            objTransaccion.setFecha(LocalDateTime.now());
	            objTransaccion.setTipo("CREACIÓN");
	            objTransaccion.setUsuario(objUsuario);
	            objTransaccion.setHorario(objHorario);
	            transaccionRepository.save(objTransaccion);
	    	}*/

			horarioService.grabarHorario(sesion, model, objHorario, dia, parte);
	    }
	    /*List<Rutina> listaRutinas = rutinaRepository.findAll();
	    model.addAttribute("listaRutinas", listaRutinas);
	        
	    List<Horario> miHorario = horarioRepository.findByPacienteAndEstado(objUsuario, true);
	    model.addAttribute("miHorario", miHorario);*/
	        
	    Horario nuevoHorario = new Horario();
		model.addAttribute("objHorario", nuevoHorario);
		return "redirect:/home/ver_horario";
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
