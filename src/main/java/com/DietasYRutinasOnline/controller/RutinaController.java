package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.EjercicioRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

@Controller
@RequestMapping("/rutina")
public class RutinaController {
	
	@Autowired
	EjercicioRepository ejercicioRepository;
	
	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	// ---- VER EJERCICIOS -------------------------------------------------------------------
	@GetMapping("/verEjercicios")
	public String verEjercicios(Model model) {
		List<Ejercicio> listaEjercicios = ejercicioRepository.findAll();
		model.addAttribute("listaEjercicios", listaEjercicios);
		
		List<String> cbxEjercicios = ejercicioRepository.findDistinctGrupomuscular();
		if (cbxEjercicios.size() > 5) {
			cbxEjercicios = cbxEjercicios.subList(0, 5);
	    }
		model.addAttribute("cbxEjercicios", cbxEjercicios);
		return "rutinas/lista_ejercicios";
	}
	
	@GetMapping("/buscarEjercicios")
	public String buscarEjercicios(String grupomuscular, Model model) {
		
		List<String> cbxEjercicios = ejercicioRepository.findDistinctGrupomuscular();
		if (cbxEjercicios.size() > 5) {
			cbxEjercicios = cbxEjercicios.subList(0, 5);
	    }
		
		List<Ejercicio> listaEjercicios = ejercicioRepository.findByGrupomuscular(grupomuscular);
		model.addAttribute("listaEjercicios", listaEjercicios);
		
		model.addAttribute("cbxEjercicios", cbxEjercicios);
		return "rutinas/lista_ejercicios";
	}
	
	/*@PostMapping("/seguirRutina")
	public String seguirRutina(
	        HttpSession sesion,
	        @ModelAttribute("objRutina") Rutina objRutina,
	        Model model) {
	    
	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        
	        Horario miHorario = horarioRepository.findByPaciente(objUsuario);
	        model.addAttribute("miHorario", miHorario);
	        
	        if (miHorario!=null) {
	            Rutina objRutinaBD = rutinaRepository.findById(objRutina.getIdrutina()).orElse(null);
	            
	            if (objRutinaBD!=null && !miHorario.getRutina().contains(objRutinaBD)) {
	                //pacienteActual.getDieta().add(objRutinaBD);
	            	miHorario.getRutina().add(objRutinaBD);
	            	//miHorario.setEstado("Activo");
	                horarioRepository.save(miHorario);
	                model.addAttribute("exito", "Se guardó la rutina con éxito");  
	            } 
	            else {
	                model.addAttribute("excepcion", "Ya tienes esta rutina");
	            }

	            Transaccion objTransaccion = new Transaccion();
	            objTransaccion.setFecha(LocalDateTime.now());
	            objTransaccion.setTipo("Seguir Rutina");
	            objTransaccion.setHorario(miHorario);
	            objTransaccion.setUsuario(objUsuario);
	            objTransaccion.setRutina(objRutinaBD);        
	            transaccionRepository.save(objTransaccion);
                
	            List<Rutina> listaRutinas;
	    	    listaRutinas = rutinaRepository.findByEstado("Activo");
	    	    model.addAttribute("listaRutinas", listaRutinas);
	    	    
	    	    InfoPaciente pacienteActual = infoPacienteRepository.findByPaciente(objUsuario);
		        model.addAttribute("pacienteActual", pacienteActual);
	    	    
	    	    if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Deficit")) {
	    	    	listaRutinas = rutinaRepository.findByTipo("Deficit");
	    		    model.addAttribute("listaRutinas", listaRutinas);
	    		    
	    	    }
	    	    else if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Volumen")) {
	    	    	listaRutinas = rutinaRepository.findByTipo("Volumen");
	    	    	model.addAttribute("listaRutinas", listaRutinas);
	    	    }
	            return "rutinas/menu_rutinas";
	        } 
	    }
	    return "index";
	}*/
	
	// ---- VENTANA CREAR RUTINA -------------------------------------------------------------------
	@PostMapping("/crearRutina")
	public String crearRutina(Model model) {
		List<Ejercicio> listaEjercicios = ejercicioRepository.findAll();
		model.addAttribute("listaEjercicios", listaEjercicios);
		Rutina objRutina = new Rutina();
		model.addAttribute("objRutina", objRutina);
		return "rutinas/nueva_rutina";
	}

	@PostMapping("/grabarRutina")
	public String grabarRutina(
			HttpSession sesion, 
			@ModelAttribute("objRutina") Rutina objRutina,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	objRutina.setNutriologo(objUsuario);
    		objRutina.setEstado("Activo");
            rutinaRepository.save(objRutina);
            
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }        
        List<Rutina> listaRutinas = rutinaRepository.findByEstado("Activo");
        model.addAttribute("finalizado", "Bien hecho, ahora los pacientes podrán ver tu rutina");
        model.addAttribute("listaRutinas", listaRutinas);
		return "rutinas/menu_rutinas";
	}
	
	// ---- VER DETALLE RUTINA -------------------------------------------------------------------
	@GetMapping("/verDetalleRutina")
	public String verDetalleRutina(
			HttpSession sesion,
			@RequestParam("idrutina") int idrutina,
			@ModelAttribute("objRutina") Rutina objRutina,
			Model model) {
		
		Rutina detalleRutina = rutinaRepository.findByIdrutina(idrutina);
		model.addAttribute("detalleRutina", detalleRutina);
		
		Usuario nutriActual = (Usuario) sesion.getAttribute("usuario");
		model.addAttribute("esCreador", nutriActual.getIdusuario() == detalleRutina.getNutriologo().getIdusuario());
		return "rutinas/detalle_rutina";
	}
	
	@PostMapping("/editarRutina")
	public String editarRutina(
			@RequestParam("idrutina")int idrutina, 
			Model model){
		Rutina objRutina = rutinaRepository.findByIdrutina(idrutina);
		model.addAttribute("objRutina", objRutina);
		
		List<Ejercicio> listaEjercicios = ejercicioRepository.findAll();
		model.addAttribute("listaEjercicios", listaEjercicios);
		return "rutinas/editar_rutina";			
	}
	
	// ---- VENTANA EDITAR RUTINA -------------------------------------------------------------------
	@PostMapping("/actualizarRutina")
	public String actualizarRutina(
			HttpSession sesion,
			@ModelAttribute("objRutina") Rutina objRutina, 
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	Rutina rutinaActual = rutinaRepository.findByIdrutina(objRutina.getIdrutina());
        	rutinaActual.setNombre(objRutina.getNombre());
			rutinaActual.setTipo(objRutina.getTipo());
			rutinaActual.setNivel(objRutina.getNivel());
			rutinaActual.setDescripcion(objRutina.getDescripcion());
			rutinaActual.setEjercicio(objRutina.getEjercicio());
			rutinaRepository.save(rutinaActual);
            
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }        
        List<Rutina> listaRutinas = rutinaRepository.findByEstado("Activo");
        model.addAttribute("listaRutinas", listaRutinas);
        return "rutinas/menu_rutinas";
	}

}
