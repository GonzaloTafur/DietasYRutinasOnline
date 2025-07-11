package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.NutriologoService;
import com.DietasYRutinasOnline.service.PacienteService;
import com.DietasYRutinasOnline.service.ReunionService;
import com.DietasYRutinasOnline.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private NutriologoService nutriologoService;

	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	HistorialMedService historialMedService;
	
	@Autowired
	TransaccionRepository transaccionRepository;

	@Autowired
	private ReunionService reunionService;


	@GetMapping("/")
	public String verPerfil(HttpSession sesion, Long codigo, Model model) {

		try {
			Paciente paciente = (Paciente) sesion.getAttribute("paciente");
			Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
			//Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
			if (paciente!=null) {

				model.addAttribute("paciente", paciente); 
				HistorialMed miInfo = historialMedService.getPaciente(paciente, true);
				//HistorialMed hm = historialMedService.getCodigo(codigo);
				//Paciente miInfo = pacienteService.getHistorial(hm);
				if(miInfo==null){
					HistorialMed cuestionario = new HistorialMed();
					model.addAttribute("cuestionario", cuestionario);
					return "cuestionario";
				}
				
				model.addAttribute("miInfo", miInfo);
				return "perfil/perfil";
			}
			//model.addAttribute("objUsuario", objUsuario);
			else if(nutriologo!=null){
				model.addAttribute("nutriologo", nutriologo);
				//List<Reunion> objReunion = reunionRepository.findByNutriologoAndEstado(objUsuario, true);
				List<Reunion> re = reunionService.getNutriologo(nutriologo, true);
				model.addAttribute("objReunion", re);
				
				List<Rutina> misRutinas = rutinaRepository.findByNutriologo(nutriologo);
				model.addAttribute("misRutinas", misRutinas);
				
				List<Dieta> misDietas = dietaRepository.findByNutriologo(nutriologo);
				model.addAttribute("misDietas", misDietas);
			
				return "perfil/perfil_nutriologo";
			}
			return "iniciar_sesion";
		} 
		catch (Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return "redirect:/home/";
		}

	}
	
	@PostMapping("/editarPerfil")
	public String editarPerfil(
			HttpSession sesion, 
			Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		if (objUsuario!=null) {
			model.addAttribute("objUsuario", objUsuario);
			return "usuario/editar_perfil";
		}
		else {
			return "index";
		}  
	}


	/* EDICIÓN DE HISTORIAL MEDICO */	
	public boolean puedeEditar(HistorialMed HistorialMed) {
	    LocalDateTime fechaHoy = LocalDateTime.now();
	    LocalDateTime fechaModificacion = HistorialMed.getFecha();
	    return ChronoUnit.DAYS.between(fechaModificacion, fechaHoy) >= 7;
	}

	@GetMapping("/editarInfo")
	public String editarInfo(HttpSession sesion, Model model) {
	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");
	    if (paciente != null) {
	        /*Rol vistaUsuario = rolRepository.findByCodigo(objUsuario.getRol().getCodigo());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
			*/
	        //HistorialMed infoActiva = historialMedRepository.findByPacienteAndEstado(objUsuario, true);
			HistorialMed miHistorial = historialMedService.getEstado(true);
			//Paciente pacienteActual = usuarioService.findByHistorialMed(miHistorial);


	        if (miHistorial!=null && puedeEditar(miHistorial)) {
	        	miHistorial.setEstado(false);
	            historialMedService.guardarHistorial(miHistorial);
	        	
	            HistorialMed nuevaInfo = new HistorialMed();
	            //nuevaInfo.setFrecEjercicios(miHistorial.getFrecEjercicios());
	            nuevaInfo.setPesoCorporal(miHistorial.getPesoCorporal());
	            nuevaInfo.setEstatura(miHistorial.getEstatura());
	            nuevaInfo.setPerimCintura(miHistorial.getPerimCintura());
	            nuevaInfo.setPerimCadera(miHistorial.getPerimCadera());
	            nuevaInfo.setPerimMuslo(miHistorial.getPerimMuslo());
	            nuevaInfo.setPerimBrazo(miHistorial.getPerimBrazo());

	            model.addAttribute("infoActiva", miHistorial);
	            model.addAttribute("nuevaInfo", nuevaInfo);
	            return "usuario/editar_HistorialMed";
	        } 
	        else {
	        	model.addAttribute("paciente", paciente);
	    	    
	    	    //HistorialMed miInfo = historialMedRepository.findByPacienteAndEstado(objUsuario, true);
	    	    model.addAttribute("miInfo", miHistorial);
	        	
	            model.addAttribute("inactivo", "Debe esperar al menos 7 días antes de poder realizar otra modificación.");
	            return "redirect:/perfil/";
	        }
	    }
	    return "redirect:/index";
	}

	@PostMapping("/actualizarInfo")
	public String actualizarInfo(
	        HttpSession sesion,
	        @ModelAttribute("nuevaInfo") HistorialMed nuevaInfo,
	        Model model) {

	    Paciente paciente = (Paciente) sesion.getAttribute("usuario");
	    if (paciente != null) {
	        //Paciente usuRegistrado = usuarioService.findById(paciente.getIdusuario()).orElse(null);
	        //nuevaInfo.setPaciente(usuRegistrado);
	        nuevaInfo.setEstado(true);
	        nuevaInfo.setFecha(LocalDateTime.now());

			//paciente.setHistorialMedico(nuevaInfo);
			//usuarioService.guardarPaciente(paciente);

	        historialMedService.guardarHistorial(nuevaInfo);

	        //Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);

			model.addAttribute("exito", "Su información se actualizó con éxito");
			model.addAttribute("objUsuario", paciente);

			//HistorialMed miInfo = historialMedRepository.findByPacienteAndEstado(objUsuario, true);
			model.addAttribute("miInfo", nuevaInfo);

			return "redirect:/perfil/";
	    }

	    return "iniciar_sesion";
	}
	
	@GetMapping("/verSeguimiento")
	public String verSeguimiento(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		
		List<HistorialMed> listaSeguimiento = historialMedService.getEstadoAll(true);
	    model.addAttribute("listaSeguimiento", listaSeguimiento);
	    Collections.reverse(listaSeguimiento);
	    
		return "usuario/seguimiento";
	}
	
	@GetMapping("/verSuPerfil")
	public String verSuPerfil(
			HttpSession sesion,
			@RequestParam("id_usuario") Long codigo,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	
	    	if(objUsuario.getCodigo() == codigo) {
	    		/*Rol vistaUsuario = rolRepository.findByCodigo(objUsuario.getRol().getCodigo());
	    		boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	    		model.addAttribute("esPaciente", esPaciente);
	    		
	    		List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    		model.addAttribute("misRutinas", misRutinas);
	    
	    		List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    		model.addAttribute("misDietas", misDietas);
	    		
	    		model.addAttribute("objUsuario", objUsuario);*/
	    		return "perfil";
	    	}
	    	/*else {
	    		Usuario suPerfil = usuarioService.findByIdusuario(idusuario);
	    		model.addAttribute("suPerfil", suPerfil);
		
	    		List<Rutina> susRutinas = rutinaRepository.findByNutriologo(suPerfil);
	    		model.addAttribute("susRutinas", susRutinas);
	    
	    		List<Dieta> susDietas = dietaRepository.findByNutriologo(suPerfil);
	    		model.addAttribute("susDietas", susDietas);
	    		return "usuario/nutriologo";
	    	}*/
	        
	    }
	    return "redirect:/login";
	}
	
}
