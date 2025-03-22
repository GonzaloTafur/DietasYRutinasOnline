package com.DietasYRutinasOnline.controller;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Notificacion;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.AlimentoRepository;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.CondicionRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Autowired
	ReunionRepository reunionRepository;
	
	@Autowired
	AsistenciaRepository asistenciaRepository;
	
	@Autowired
	NotificacionRepository notificacionRepository;
	
	@Autowired
	CondicionRepository condicionRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	
	@GetMapping("/verNutriologos")
	public String verNutriologos(
			HttpSession sesion,
			@ModelAttribute("objNutriologo") Usuario objNutriologo,
			Model model) {
	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
	    Rol rolNutriologo = rolRepository.findByNombre("Nutriologo");
	   
	    List<Usuario> listaNutriologos = usuarioRepository.findByRolAndEstado(rolNutriologo, true);
	    model.addAttribute("listaNutriologos", listaNutriologos);
	    
	    return "usuario/lista_nutriologos";
	}
	
	@GetMapping("/verRutinas")
	public String verRutinas(
			HttpSession sesion,
			@ModelAttribute("objRutina") Rutina objRutina,
			Model model) {
	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
	    InfoPaciente pacienteActual = infoPacienteRepository.findByPacienteAndEstado(objUsuario, true);
	    model.addAttribute("pacienteActual", pacienteActual);
	   
	    List<Rutina> listaRutinas;
	    listaRutinas = rutinaRepository.findByEstado(true);
	    model.addAttribute("listaRutinas", listaRutinas);
	    
	    
	    /* VISTA PARA LOS PACIENTES*/
	    if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Deficit")) {
	    	listaRutinas = rutinaRepository.findByTipo("Deficit");
	    	model.addAttribute("listaRutinas", listaRutinas);
	    	
		    if(pacienteActual.getFrecEjercicios().equals("A veces o nada") || pacienteActual.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivelAndTipo("Principiante", "Deficit");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(pacienteActual.getFrecEjercicios().equals("30 minutos a diario") || pacienteActual.getFrecEjercicios().equals("1 hora algunos dias")) {
		    	
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
		    else if(pacienteActual.getFrecEjercicios().equals("30 minutos diario") || pacienteActual.getFrecEjercicios().equals("1 hora algunos dias")) {
		    	
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
		    else if(pacienteActual.getFrecEjercicios().equals("30 minutos a diario") || pacienteActual.getFrecEjercicios().equals("1 hora algunos dias")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Deficit");
		    	model.addAttribute("listaRutinas", listaRutinas);
		    }
	    }
	    	
	    
	    return "rutinas/menu_rutinas";
	}

	
	@GetMapping("/verDietas")
	public String gestionarPartidos(
			HttpSession sesion, 
			@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Rol vistaUsuario = new Rol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    InfoPaciente pacienteActual = infoPacienteRepository.findByPacienteAndEstado(objUsuario, true);
	    model.addAttribute("pacienteActual", pacienteActual);
	    
	    List<Dieta> listaDietas;
	    listaDietas = dietaRepository.findByEstado(true);
	    model.addAttribute("listaDietas", listaDietas);
	    
	    /* VISTA PARA LOS PACIENTES*/
	    if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Deficit")) {
	    	listaDietas = dietaRepository.findByObjetivo("Deficit");
		    model.addAttribute("listaDietas", listaDietas);
		    
		    if(pacienteActual.getCondicion().equals("Lacteos")) {
		    	Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicion(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Gluten")) {
		    	Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionAndObjetivo(gluten, "Deficit");
		    	model.addAttribute("listaDietas", listaDietas);
		    }    
	    }
	    
	    else if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Volumen")) {
	    	listaDietas = dietaRepository.findByObjetivo("Volumen");
	    	model.addAttribute("listaDietas", listaDietas);
	    	
	    	if(pacienteActual.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	
		    	listaDietas = dietaRepository.findByCondicion(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionAndObjetivo(gluten, "Volumen");
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
	    
	    else if (pacienteActual!=null){
	    	listaDietas = dietaRepository.findByEstado(true);
	    	model.addAttribute("listaDietas", listaDietas);
	    	
	    	if(pacienteActual.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	model.addAttribute("gluten", gluten);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(gluten);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
	    
	    return "dietas/menu_dietas";
	}
	
	@GetMapping("/verPerfil")
	public String verPerfil(
			HttpSession sesion,  
			Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	    	boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente); 
	    }
	    model.addAttribute("objUsuario", objUsuario);
	    
	    /* SI ES PACIENTE*/
	    InfoPaciente miInfo = infoPacienteRepository.findByPacienteAndEstado(objUsuario, true);
	    model.addAttribute("miInfo", miInfo);
	    
	    
	    /* SI ES NUTRIOLOGO */
	    List<Reunion> objReunion = reunionRepository.findByNutriologoAndEstado(objUsuario, true);
	    model.addAttribute("objReunion", objReunion);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misDietas", misDietas);
	    
	    return "perfil";
	}
	
	@GetMapping("/ajustesCuenta")
	public String ajustesCuenta(HttpSession sesion, Model model){
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		if (objUsuario!=null) {
			model.addAttribute("objUsuario", objUsuario);
			return "usuario/seguridad";
		}
		else {
			return "index";
		}
	}
	
	@GetMapping("/verHorario")
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
		InfoPaciente pacienteActual = infoPacienteRepository.findByPacienteAndEstado(objUsuario, true);
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
	}
	
	
	@GetMapping("/verNotis")
	public String verNotis(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        
	        if(esPaciente) {  
	        	List<Notificacion> notisAutomaticas = notificacionRepository.findByRol("Paciente");
	        	
	        	/* NOTIS POR HORARIO */
	        	String diaSemana = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES")).toUpperCase();
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
	}

	@GetMapping("/retroceder")
	public String regresarMenu(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
	    return "menu";
	}
}
