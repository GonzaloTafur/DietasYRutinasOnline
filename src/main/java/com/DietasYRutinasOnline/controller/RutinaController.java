package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
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

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Ejercicio;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.EjercicioRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.EjercicioService;
import com.DietasYRutinasOnline.service.HorarioService;
import com.DietasYRutinasOnline.service.ObjetivoService;
import com.DietasYRutinasOnline.service.RutinaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rutina")
public class RutinaController {
	
	@Autowired
	private EjercicioService ejercicioService;
	
	@Autowired
	private RutinaService rutinaService;

	@Autowired
	private ObjetivoService objetivoService;
	
	@Autowired
	HistorialMedRepository historialMedRepository;
	
	@Autowired
	private HorarioService horarioService;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	@Autowired
	NotificacionRepository notificacionRepository;
	
	@Autowired
	RolRepository rolRepository;


	@GetMapping("/")
	public String verRutinas(
			HttpSession sesion,
			@ModelAttribute("objRutina") Rutina objRutina,
			Model model) {
	    //Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
		if (paciente!=null || nutriologo!=null) {
	        //Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);
			model.addAttribute("paciente", paciente);

			List<Rutina> listaRutinas = rutinaService.getEstado(true);
	    	model.addAttribute("listaRutinas", listaRutinas);

			return "rutinas/menu_rutinas";
	    }
	    //HistorialMed pacienteActual = HistorialMedRepository.findByPacienteAndEstado(objUsuario, true);
	    //model.addAttribute("pacienteActual", pacienteActual);
	   
	    //List<Rutina> listaRutinas = rutinaService.getEstado(true);
	    //model.addAttribute("listaRutinas", listaRutinas);
	    
	    
	    /* VISTA PARA LOS PACIENTES*/
	    /*if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Deficit")) {
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
	    }*/
	    	
		return "iniciar_sesion";
	}

	
	
	// ---- VER EJERCICIOS -------------------------------------------------------------------
	@GetMapping("/ver_ejercicios")
	public String verEjercicios(Model model) {
		List<Ejercicio> listaEjercicios = ejercicioService.getEstado(true);
		model.addAttribute("listaEjercicios", listaEjercicios);
		
		List<String> cbxEjercicios = ejercicioService.cbxEjercicio();
		if (cbxEjercicios.size() > 5) {
			cbxEjercicios = cbxEjercicios.subList(0, 5);
	    }
		model.addAttribute("cbxEjercicios", cbxEjercicios);
		return "rutinas/lista_ejercicios";
	}
	
	@GetMapping("/buscarEjercicios")
	public String buscarEjercicios(String gm, Model model) {
		
		List<String> cbxEjercicios = ejercicioService.cbxEjercicio();
		if (cbxEjercicios.size() > 5) {
			cbxEjercicios = cbxEjercicios.subList(0, 5);
	    }
		
		List<Ejercicio> listaEjercicios = ejercicioService.getGrupomuscular(gm);
		model.addAttribute("listaEjercicios", listaEjercicios);
		
		model.addAttribute("cbxEjercicios", cbxEjercicios);
		return "rutinas/lista_ejercicios";
	}
	
	// ---- VENTANA CREAR RUTINA -------------------------------------------------------------------
	@GetMapping("crear_rutina")
	public String crearRutina(Model model) {
		List<Ejercicio> listaEjercicios = ejercicioService.getEstado(true);
		model.addAttribute("listaEjercicios", listaEjercicios);
		
		List<Objetivo> lstObjetivo = objetivoService.getEstado(true);
		model.addAttribute("lstObjetivo", lstObjetivo);
		Rutina ru = new Rutina();
		model.addAttribute("ru", ru);
		return "rutinas/nueva_rutina";
	}

	//@PostMapping("grabar_rutina")
	/*public String grabarRutina(
			HttpSession sesion, 
			@ModelAttribute("objRutina") Rutina objRutina,
			Model model) {
		
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
        if (nutriologo!=null) {
        	objRutina.setNutriologo(nutriologo);
    		objRutina.setEstado(true);
            rutinaService.grabarRutina(objRutina);
            
            Transaccion objTransaccion = new Transaccion();
            objTransaccion.setFecha(LocalDateTime.now());
            objTransaccion.setTipo("Creación");
            //objTransaccion.setUsuario(nutriologo);
            objTransaccion.setRutina(objRutina);
            transaccionRepository.save(objTransaccion);
           
	        //Rol vistaUsuario = objUsuario.getRol();
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);
	    }        
        List<Rutina> listaRutinas = rutinaService.getEstado(true);
        model.addAttribute("finalizado", "Bien hecho, ahora los pacientes podrán ver tu rutina");
        model.addAttribute("listaRutinas", listaRutinas);
		return "rutinas/menu_rutinas";
	}*/

	@PostMapping("grabar_rutina")
	public String grabarRutina(HttpSession sesion, Rutina ru, Model model) {
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
		ru.setNutriologo(nutriologo);
        rutinaService.grabarRutina(ru);  
        model.addAttribute("finalizado", "Bien hecho, ahora los pacientes podrán ver tu rutina");
		return "redirect:/perfil/";
	}
	
	// ---- VER DETALLE RUTINA -------------------------------------------------------------------
	@GetMapping("verDetalleRutina/{codigo}")
	public String verDetalleRutina(
			HttpSession sesion,
			//@RequestParam("id_rut") Long codigo,
			//@ModelAttribute("ru") Rutina ru,
			@PathVariable Long codigo,
			Model model) {
		try {
			Paciente paciente = (Paciente) sesion.getAttribute("paciente");
			Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
			if (paciente!=null || nutriologo!=null) {
				/*Rol vistaUsuario = rolRepository.findByCodigo(objUsuario.getRol().getCodigo());
				boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
				model.addAttribute("esPaciente", esPaciente);*/
				Rutina detRutina = rutinaService.getCodigo(codigo);
				model.addAttribute("detRutina", detRutina);

				return "rutinas/detalle_rutina";
			} 
			else{
				return "iniciar_sesion";
			}
		} 
		catch (Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return "redirect:/rutina";
		}
		
		//Rutina detalleRutina = rutinaRepository.findByCodigo(codigo);
		//model.addAttribute("detalleRutina", detalleRutina);
		
		
		//Usuario nutriActual = (Usuario) sesion.getAttribute("usuario");
		//model.addAttribute("esCreador", nutriActual.getCodigo() == detalleRutina.getNutriologo().getCodigo());
		
	}
	
	// ---- EDITAR RUTINA -------------------------------------------------------------------
	@PostMapping("editar_rutina")
	public String editarRutina(
			@RequestParam("id_rut")Long codigo, 
			Model model){
		//Rutina objRutina = rutinaRepository.findByCodigo(codigo);
		//model.addAttribute("objRutina", objRutina);
		
		List<Ejercicio> listaEjercicios = ejercicioService.getEstado(true);
		model.addAttribute("listaEjercicios", listaEjercicios);
		return "rutinas/editar_rutina";			
	}
	
	@PostMapping("actualizar_rutina")
	public String actualizarRutina(
			HttpSession sesion,
			@ModelAttribute("objRutina") Rutina ru, 
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	/*Rutina rutinaActual = rutinaRepository.findByCodigo(objRutina.getCodigo());
        	rutinaActual.setNombre(objRutina.getNombre());
			rutinaActual.setTipo(objRutina.getTipo());
			rutinaActual.setNivel(objRutina.getNivel());
			rutinaActual.setDescripcion(objRutina.getDescripcion());
			rutinaActual.setEjercicio(objRutina.getEjercicio());
			rutinaService.grabarRutina(rutinaActual);
            
	        /*Rol vistaUsuario = objUsuario.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);*/
			rutinaService.actualizarRutina(ru);
	    }        
        return "redirect:/rutina/";
	}

}
