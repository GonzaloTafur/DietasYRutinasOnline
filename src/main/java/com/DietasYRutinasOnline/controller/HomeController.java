package com.DietasYRutinasOnline.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Notificacion;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.CondicionRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.DietaService;
import com.DietasYRutinasOnline.service.RolService;
import com.DietasYRutinasOnline.service.RutinaService;
import com.DietasYRutinasOnline.service.UsuarioService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private RolService rolService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private DietaService dietaService;

	@Autowired
	private RutinaService rutinaService;

	@GetMapping("/")
	public String irAMenu(HttpSession sesion, Model model){
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");

		
		if(paciente!=null || nutriologo!=null){
			model.addAttribute("paciente", paciente);
			model.addAttribute("nutriologo", nutriologo);
			return "menu";
		}

		return "iniciar_sesion";
	}
	
	@GetMapping("ver_nutriologos")
	public String verNutriologos(
			HttpSession sesion,
			//@ModelAttribute("nutriologo") Nutriologo nutriologo,
			Model model) {

	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");

	    if (paciente!=null) {
	        //Rol vistaUsuario = rolService.getId(objUsuario.getRol().getCodigo());
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);

			//Rol rolNutriologo = rolService.getNombre("Nutriologo");
	   
			//List<Usuario> listaNutriologos = usuarioService.getRol(rolNutriologo);
			List<Nutriologo> listaNutriologos = usuarioService.getEstadoNutriologo(true);
			model.addAttribute("listaNutriologos", listaNutriologos);
			
			return "usuario/lista_nutriologos";
	    }
	    return "iniciar_sesion";
	}
	
	@GetMapping("ajustes_cuenta/{codigo}")
	public String ajustesCuenta(HttpSession sesion, Model model){
		//Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
		
		if (paciente!=null || nutriologo!=null) {
			model.addAttribute("u", paciente);
			model.addAttribute("u", nutriologo);
			return "usuario/seguridad";
		}
		else {
			return "iniciar_sesion";
		}
	}

	/* VER HORARO EN MANTENIMIENTO */

	/*@GetMapping("/ver_horario")
	public String verHorario(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
		List<Horario> miHorario = horarioRepository.findByPacienteAndEstado(objUsuario, true);
		model.addAttribute("miHorario", miHorario);
	    
	    List<Rutina> cbxRutinas = rutinaRepository.findAll();
		model.addAttribute("listaRutinas", cbxRutinas);
		
		Horario objHorario = new Horario();
		model.addAttribute("objHorario", objHorario);
		
		/* RECUPERAR LISTA DE RUTINAS DEBAJO DEL HORARIO */
		/*HistorialMed pacienteActual = HistorialMedRepository.findByPacienteAndEstado(objUsuario, true);
	    model.addAttribute("pacienteActual", pacienteActual);
		
		List<Rutina> listaRutinas;
	    listaRutinas = rutinaRepository.findByEstado(true);
	    model.addAttribute("listaRutinas", listaRutinas);
	    
	    if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Deficit")) {
	    	listaRutinas = rutinaRepository.findByTipo("Deficit");
	    	model.addAttribute("listaRutinas", listaRutinas);
	    	
		    if(pacienteActual.getFrecEjercicios().equals("A veces o nada") || pacienteActual.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivelAndTipo("Principiante", "Deficit");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(pacienteActual.getFrecEjercicios().equals("30 minutos a diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Deficit");
		    	model.addAttribute("listaRutinas", listaRutinas);
		    }
		    
	    }
	    else if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Volumen")) {
	    	listaRutinas = rutinaRepository.findByTipo("Volumen");
	    	model.addAttribute("listaRutinas", listaRutinas);
	    	
	    	if(pacienteActual.getFrecEjercicios().equals("A veces o nada") || pacienteActual.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivelAndTipo("Principiante", "Volumen");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(pacienteActual.getFrecEjercicios().equals("30 minutos diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Volumen");
		    	model.addAttribute("listaRutinas", listaRutinas);

		    }
	    }
	    else if (pacienteActual!=null){
	    	if(pacienteActual.getFrecEjercicios().equals("A veces o nada") || pacienteActual.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivel("Principiante");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(pacienteActual.getFrecEjercicios().equals("30 minutos a diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Deficit");
		    	model.addAttribute("listaRutinas", listaRutinas);
		    }
	    }
		
		return "usuario/horario";
	}*/
	
	@GetMapping("/ver_horario")
	public String verHorario(HttpSession sesion, Model model) {
		return "usuario/horario";
	}

	/* VER NOTIFICACIONES EN MANTENIMIENTO */

	/*@GetMapping("/verNotis")
	public String verNotis(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        
	        if(esPaciente) {  
	        	List<Notificacion> notisAutomaticas = notificacionRepository.findByRol("Paciente");
	        	
	        	/* NOTIS POR HORARIO */
	        	/*String diaSemana = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase();
	        	List<Notificacion> notificaciones = notificacionRepository.findByRol("Paciente");
	        	
	        	List<Horario> horariosUsuario = horarioRepository.findByPacienteAndEstado(objUsuario, true);

	        		List<Notificacion> notificacionesPorHorario = notificaciones.stream()
	    	        	    .filter(notificacion -> horariosUsuario.stream()
	    	        	        .anyMatch(horario -> horario.getDia().equalsIgnoreCase(diaSemana) &&
	    	        	                              horario.getDia().equalsIgnoreCase(notificacion.getDia())))
	    	        	    .collect(Collectors.toList());
	        		Collections.reverse(notificacionesPorHorario);
	        	    model.addAttribute("notificacionesPorHorario", notificacionesPorHorario);
	        	
	        	List<Notificacion> todasNotis = new ArrayList<>();
	        	todasNotis.addAll(notisAutomaticas);
	        	todasNotis.addAll(notificacionesPorHorario);
	        	Collections.reverse(todasNotis);
	        	model.addAttribute("objNotis", todasNotis);

		        return "notificaciones";
	        }
	        
	        else{
	        	List<Reunion> objReunion = reunionRepository.findByNutriologoAndEstado(objUsuario, true);

	        	if (objReunion!=null) {
	        	    List<Transaccion> tipoTransaccion = transaccionRepository.findByTipoAndReunionIn("Acceso a reunión", objReunion);

	        	    List<Notificacion> objNotis = notificacionRepository.findByRolAndTransaccionIn("Nutriologo", tipoTransaccion);
	        	    Collections.reverse(objNotis);
	        	    model.addAttribute("objNotis", objNotis);
	        	} 
	        	else {
	        	    model.addAttribute("objNotis", Collections.emptyList());
	        	}

	        	return "notificaciones";
	        }
	        
	    }
	    return "index";
	}*/

}
