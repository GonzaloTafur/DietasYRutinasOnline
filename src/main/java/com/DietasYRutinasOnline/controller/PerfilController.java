package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TipoUsuarioRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

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
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	
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
	
	public boolean puedeEditar(InfoPaciente infoPaciente) {
	    LocalDateTime fechaHoy = LocalDateTime.now();
	    LocalDateTime fechaModificacion = infoPaciente.getFecha();
	    return ChronoUnit.DAYS.between(fechaModificacion, fechaHoy) >= 7;
	}
	
	@GetMapping("/editarInfo")
	public String editarInfo(HttpSession sesion, Model model) {
	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario != null) {
	        TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);

	        InfoPaciente infoActiva = infoPacienteRepository.findByPacienteAndEstado(objUsuario, "Activo");

	        if (infoActiva!=null && puedeEditar(infoActiva)) {
	        	infoActiva.setEstado("Inactivo");
	            infoPacienteRepository.save(infoActiva);
	        	
	            InfoPaciente nuevaInfo = new InfoPaciente();
	            nuevaInfo.setFrecEjercicios(infoActiva.getFrecEjercicios());
	            nuevaInfo.setCondicion(infoActiva.getCondicion());
	            nuevaInfo.setObjetivo(infoActiva.getObjetivo());
	            nuevaInfo.setPesoCorporal(infoActiva.getPesoCorporal());
	            nuevaInfo.setEstatura(infoActiva.getEstatura());
	            nuevaInfo.setPerimCintura(infoActiva.getPerimCintura());
	            nuevaInfo.setPerimCadera(infoActiva.getPerimCadera());
	            nuevaInfo.setPerimMuslo(infoActiva.getPerimMuslo());
	            nuevaInfo.setPerimBrazo(infoActiva.getPerimBrazo());

	            model.addAttribute("infoActiva", infoActiva);
	            model.addAttribute("nuevaInfo", nuevaInfo);
	            return "usuario/editar_infopaciente";
	        } 
	        else {
	        	model.addAttribute("objUsuario", objUsuario);
	    	    
	    	    InfoPaciente miInfo = infoPacienteRepository.findByPacienteAndEstado(objUsuario, "Activo");
	    	    model.addAttribute("miInfo", miInfo);
	        	
	            model.addAttribute("inactivo", "Debe esperar al menos 7 días antes de poder realizar otra modificación.");
	            return "perfil";
	        }
	    }
	    return "redirect:/index";
	}

	@PostMapping("/actualizarInfo")
	public String actualizarInfo(
	        HttpSession sesion,
	        @ModelAttribute("nuevaInfo") InfoPaciente nuevaInfo,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario != null) {
	        Usuario usuRegistrado = usuarioRepository.findById(objUsuario.getIdusuario()).orElse(null);
	        nuevaInfo.setPaciente(usuRegistrado);
	        nuevaInfo.setEstado("Activo");
	        nuevaInfo.setFecha(LocalDateTime.now());

	        infoPacienteRepository.save(nuevaInfo);

	        TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    model.addAttribute("exito", "Su información se actualizó con éxito");
	    model.addAttribute("objUsuario", objUsuario);

	    InfoPaciente miInfo = infoPacienteRepository.findByPacienteAndEstado(objUsuario, "Activo");
	    model.addAttribute("miInfo", miInfo);

	    return "menu";
	}
	
	@GetMapping("/verSeguimiento")
	public String verSeguimiento(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		
		List<InfoPaciente> listaSeguimiento = infoPacienteRepository.findByPaciente(objUsuario);
	    model.addAttribute("listaSeguimiento", listaSeguimiento);
	    Collections.reverse(listaSeguimiento);
	    
		return "usuario/seguimiento";
	}
	
	@GetMapping("/verSuPerfil")
	public String verSuPerfil(
			HttpSession sesion,
			@RequestParam("idusuario") int idusuario,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	
	    	if(objUsuario.getIdusuario() == idusuario) {
	    		TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	    		boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	    		model.addAttribute("esPaciente", esPaciente);
	    		
	    		List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    		model.addAttribute("misRutinas", misRutinas);
	    
	    		List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    		model.addAttribute("misDietas", misDietas);
	    		
	    		model.addAttribute("objUsuario", objUsuario);
	    		return "perfil";
	    	}
	    	else {
	    		Usuario suPerfil = usuarioRepository.findByIdusuario(idusuario);
	    		model.addAttribute("suPerfil", suPerfil);
		
	    		List<Rutina> susRutinas = rutinaRepository.findByNutriologo(suPerfil);
	    		model.addAttribute("susRutinas", susRutinas);
	    
	    		List<Dieta> susDietas = dietaRepository.findByNutriologo(suPerfil);
	    		model.addAttribute("susDietas", susDietas);
	    		return "usuario/nutriologo";
	    	}
	        
	    }
	    return "redirect:/login";
	}
	
}
