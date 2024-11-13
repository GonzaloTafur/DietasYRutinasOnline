package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TipoUsuarioRepository;


@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	@Autowired
	HorarioRepository horarioRepository;
	
	/*
	@GetMapping("/verRutinas")
	public String verRutinas(Model model) {
	    List<Rutina> listaRutinas;
	    listaRutinas = rutinaRepository.findByEstrutina("Activo");
	    model.addAttribute("listaRutinas", listaRutinas);
	    model.addAttribute("esPaciente", true);
	    return "listas/menu_rutinas";
	}*/
	
	@GetMapping("/verRutinas")
	public String verRutinas(
			HttpSession sesion,
			@ModelAttribute("objRutina") Rutina objRutina,
			Model model) {
	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
	    InfoPaciente pacienteActual = infoPacienteRepository.findByPaciente(objUsuario);
	    model.addAttribute("pacienteActual", pacienteActual);
	   
	    List<Rutina> listaRutinas;
	    listaRutinas = rutinaRepository.findByEstado("Activo");
	    model.addAttribute("listaRutinas", listaRutinas);
	    
	    /* VISTA PARA LOS PACIENTES*/
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
	    
	    return "rutinas/menu_rutinas";
	}

	
	@GetMapping("/verDietas")
	public String gestionarPartidos(
			HttpSession sesion, 
			@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    InfoPaciente pacienteActual = infoPacienteRepository.findByPaciente(objUsuario);
	    model.addAttribute("pacienteActual", pacienteActual);
	    
	    List<Dieta> listaDietas;
	    listaDietas = dietaRepository.findByEstado("Activo");
	    model.addAttribute("listaDietas", listaDietas);
	    
	    /* VISTA PARA LOS PACIENTES*/
	    if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Deficit")) {
	    	listaDietas = dietaRepository.findByObjetivo("Deficit");
		    model.addAttribute("listaDietas", listaDietas);
		    
		    /*if(pacienteActual.getCondicion().equals("Lacteos")) {
		    	
		    	//listaDietas = rutinaRepository.findByNivelAndTipo("Principiante", "Deficit");
		    	model.addAttribute("listaDietas", listaDietas);
		    }
		    else if(pacienteActual.getCondicion().equals("Gluten")) {
		    	
		    	//listaDietas = dietaRepository.findByAlimento()
		    	model.addAttribute("listaDietas", listaDietas);
		    }*/
	    }
	    else if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Volumen")) {
	    	listaDietas = dietaRepository.findByObjetivo("Volumen");
	    	model.addAttribute("listaDietas", listaDietas);
	    }

	    return "dietas/menu_dietas";
	}
	
	@GetMapping("/verPerfil")
	public String verPerfil(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        //TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	    	TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	    	boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);

	        //List<Dieta> dietasPaciente = dietaRepository.findByIddieta(iddieta);
	        //model.addAttribute("dietasPaciente", dietasPaciente);
	    }
	    model.addAttribute("objUsuario", objUsuario);
	    
	    InfoPaciente miInfo = infoPacienteRepository.findByPaciente(objUsuario);
	    model.addAttribute("miInfo", miInfo);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misDietas", misDietas);
	    
	    return "perfil";
	}
	
	@GetMapping("/perfilNutriologo")
	public String perfilNutriologo(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        //TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	    	TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	    	boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);

	        //List<Dieta> dietasPaciente = dietaRepository.findByIddieta(iddieta);
	        //model.addAttribute("dietasPaciente", dietasPaciente);
	    }
	    model.addAttribute("objUsuario", objUsuario);
	    
	    //InfoPaciente miInfo = infoPacienteRepository.findByPaciente(objUsuario);
	    //model.addAttribute("miInfo", miInfo);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misDietas", misDietas);
	    
	    return "usuario/nutriologo";
	}
	
	@GetMapping("/ajustesCuenta")
	public String ajustesCuenta(HttpSession sesion, Model model){
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		if (objUsuario!=null) {
			model.addAttribute("objUsuario", objUsuario);
			return "usuario/contraseña";
		}
		else {
			return "index";
		}
	}
	
	@GetMapping("/verHorario")
	public String verHorario(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
		List<Horario> miHorario = horarioRepository.findByPacienteAndEstado(objUsuario, "Activo");
		model.addAttribute("miHorario", miHorario);
	    
	    List<Rutina> cbxRutinas = rutinaRepository.findAll();
		model.addAttribute("listaRutinas", cbxRutinas);
		
		Horario objHorario = new Horario();
		model.addAttribute("objHorario", objHorario);
		
		// DEVOLVER LISTA DE RUTINAS DEBAJO DEL HORARIO
		InfoPaciente pacienteActual = infoPacienteRepository.findByPaciente(objUsuario);
	    model.addAttribute("pacienteActual", pacienteActual);
		
		List<Rutina> listaRutinas;
	    listaRutinas = rutinaRepository.findByEstado("Activo");
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
		
		return "usuario/horario";
	}
	
	/*@GetMapping("/mostrarHorario")
	public String mostrarHorario(Model model) {
	    List<Horario> miHorario = horarioService.obtenerHorariosPorUsuario(usuarioId);

	    // Crear un mapa de días de la semana a lista de horarios
	    Map<String, List<Horario>> horariosPorDia = new HashMap<>();
	    for (String dia : Arrays.asList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")) {
	        horariosPorDia.put(dia, new ArrayList<>());
	    }

	    // Llenar cada lista correspondiente al día de la semana
	    for (Horario horario : miHorario) {
	        horariosPorDia.get(horario.getDiaSemana()).add(horario);
	    }

	    model.addAttribute("horariosPorDia", horariosPorDia);
	    return "perfil";
	}*/
	
	@GetMapping("/retroceder")
	public String regresarMenu(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        //boolean esNutriologo = vistaUsuario.getNomtipousu().equals("Nutriologo");
	        //model.addAttribute("esPaciente", esNutriologo);
	    }
	    return "menu";
	}
}
