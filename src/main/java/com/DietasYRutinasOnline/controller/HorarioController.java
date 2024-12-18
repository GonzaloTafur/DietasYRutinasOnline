package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TipoUsuarioRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

@Controller
@RequestMapping("/horario")
public class HorarioController {
	
	@Autowired
	TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	ReunionRepository reunionRepository;
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;

	@PostMapping("/grabarHorario")
	public String grabarHorario(
	        HttpSession sesion, 
	        @ModelAttribute("objHorario") Horario objHorario,
	        @RequestParam("dia") String dia,
	        @RequestParam("periodo") String periodo,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	objHorario.setPaciente(objUsuario);
	    	objHorario.setEstado("Activo");
	    	
	    	Horario conflictoHorario = horarioRepository.findByPacienteAndDiaAndPeriodoAndEstado(objUsuario, dia, periodo, "Activo");
	    	Horario conflictoDia = horarioRepository.findByPacienteAndDiaAndEstado(objUsuario, dia, "Activo");
	    	
	    	if(conflictoHorario!=null) {	    		
	    		model.addAttribute("error", "El horario está en conflicto con otro existente");
	    	}
	    	else if(conflictoDia!=null){
	    		model.addAttribute("error", "Te sugerimos que no elijas más de una rutina para un mismo día");
	    	}
	    	else {
	    		//objHorario.setEstado("Activo");
	    		horarioRepository.save(objHorario);
	    		model.addAttribute("exito", "Se añadió con éxito");
	    		
	    		Transaccion objTransaccion = new Transaccion();
	            objTransaccion.setFecha(LocalDateTime.now());
	            objTransaccion.setTipo("CREACIÓN");
	            objTransaccion.setUsuario(objUsuario);
	            objTransaccion.setHorario(objHorario);
	            transaccionRepository.save(objTransaccion);
	    	}
	    }
	    List<Rutina> listaRutinas = rutinaRepository.findAll();
	    model.addAttribute("listaRutinas", listaRutinas);
	        
	    List<Horario> miHorario = horarioRepository.findByPacienteAndEstado(objUsuario, "Activo");
	    model.addAttribute("miHorario", miHorario);
	        
	    model.addAttribute("objHorario", new Horario());
	    return "usuario/horario";
	}
	
	@GetMapping("/eliminarHora/{idhorario}")
	public String eliminarHora(
			HttpSession sesion, 
			@ModelAttribute ("idhorario") int idhorario, 
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Horario horaEliminada = horarioRepository.findByIdhorario(idhorario);
	    	horaEliminada.setEstado("Inactivo");
	    	horarioRepository.save(horaEliminada);
	    	model.addAttribute("eliminado", "El horario ha eliminado con exito");
	    	
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
		List<Rutina> listaRutinas = rutinaRepository.findAll();
		model.addAttribute("listaRutinas", listaRutinas);
		
		List<Horario> miHorario = horarioRepository.findByPacienteAndEstado(objUsuario, "Activo");
		model.addAttribute("miHorario", miHorario);
		
		Horario nuevoHorario = new Horario();
		model.addAttribute("objHorario", nuevoHorario);
		return "usuario/horario";
	}
	
}
