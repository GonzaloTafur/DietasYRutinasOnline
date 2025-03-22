package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.DietasYRutinasOnline.entity.Notificacion;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.EjercicioRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

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
	
	@Autowired
	NotificacionRepository notificacionRepository;
	
	@Autowired
	RolRepository rolRepository;
	
	
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
    		objRutina.setEstado(true);
            rutinaRepository.save(objRutina);
            
            Transaccion objTransaccion = new Transaccion();
            objTransaccion.setFecha(LocalDateTime.now());
            objTransaccion.setTipo("Creación");
            objTransaccion.setUsuario(objUsuario);
            objTransaccion.setRutina(objRutina);
            transaccionRepository.save(objTransaccion);
           
	        Rol vistaUsuario = objUsuario.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }        
        List<Rutina> listaRutinas = rutinaRepository.findByEstado(true);
        model.addAttribute("finalizado", "Bien hecho, ahora los pacientes podrán ver tu rutina");
        model.addAttribute("listaRutinas", listaRutinas);
		return "rutinas/menu_rutinas";
	}
	
	// ---- VER DETALLE RUTINA -------------------------------------------------------------------
	@GetMapping("/verDetalleRutina")
	public String verDetalleRutina(
			HttpSession sesion,
			@RequestParam("idrutina") Long idrutina,
			@ModelAttribute("objRutina") Rutina objRutina,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    } 
		
		Rutina detalleRutina = rutinaRepository.findByIdrutina(idrutina);
		model.addAttribute("detalleRutina", detalleRutina);
		
		Usuario nutriActual = (Usuario) sesion.getAttribute("usuario");
		model.addAttribute("esCreador", nutriActual.getIdusuario() == detalleRutina.getNutriologo().getIdusuario());
		return "rutinas/detalle_rutina";
	}
	
	// ---- EDITAR RUTINA -------------------------------------------------------------------
	@PostMapping("/editarRutina")
	public String editarRutina(
			@RequestParam("idrutina")Long idrutina, 
			Model model){
		Rutina objRutina = rutinaRepository.findByIdrutina(idrutina);
		model.addAttribute("objRutina", objRutina);
		
		List<Ejercicio> listaEjercicios = ejercicioRepository.findAll();
		model.addAttribute("listaEjercicios", listaEjercicios);
		return "rutinas/editar_rutina";			
	}
	
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
            
	        Rol vistaUsuario = objUsuario.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }        
        List<Rutina> listaRutinas = rutinaRepository.findByEstado(true);
        model.addAttribute("listaRutinas", listaRutinas);
        return "rutinas/menu_rutinas";
	}

}
