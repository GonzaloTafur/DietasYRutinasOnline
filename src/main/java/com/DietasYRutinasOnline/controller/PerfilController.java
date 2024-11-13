package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
	
	@PostMapping("/llenarCuestionario")
	public String verCuestionario(HttpSession sesion, Model model) {
	    InfoPaciente objCuestionario = new InfoPaciente();
        model.addAttribute("objCuestionario", objCuestionario);
	    return "cuestionario";
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
	
	@PostMapping("/editarInfo")
	public String editarInfo(
			HttpSession sesion, 
			@RequestParam("idinfopaciente") int idinfopaciente, 
			Model model) {
		
		InfoPaciente objInfo = infoPacienteRepository.findByIdinfopaciente(idinfopaciente);
	    model.addAttribute("objInfo", objInfo);
	    return "usuario/editar_infopaciente";
	}
	
	@PostMapping("/actualizarInfo")
	public String actualizarInfo(
			HttpSession sesion, 
			@ModelAttribute("miInfo") InfoPaciente objInfo,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	InfoPaciente miInfoActual = infoPacienteRepository.findByIdinfopaciente(objInfo.getIdinfopaciente());
	    	miInfoActual.setFrecEjercicios(objInfo.getFrecEjercicios());
	    	miInfoActual.setCondicion(objInfo.getCondicion());
	    	miInfoActual.setPesoCorporal(objInfo.getPesoCorporal());
	    	miInfoActual.setEstatura(objInfo.getEstatura());
	    	miInfoActual.setPerimCintura(objInfo.getPerimCintura());
	    	miInfoActual.setPerimCadera(objInfo.getPerimCadera());
	    	miInfoActual.setPerimMuslo(objInfo.getPerimMuslo());
	    	miInfoActual.setPerimBrazo(objInfo.getPerimBrazo());
	    	miInfoActual.setObjetivo(objInfo.getObjetivo());
	    	infoPacienteRepository.save(miInfoActual);
	    	
	    	Transaccion objTransaccion = new Transaccion();
            objTransaccion.setFecha(LocalDateTime.now());
            objTransaccion.setTipo("Actualización");
            objTransaccion.setInfopaciente(miInfoActual);
            objTransaccion.setUsuario(objUsuario);
            transaccionRepository.save(objTransaccion);
            
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
	    model.addAttribute("exito", "Su información se actualizó con exito");
	   
	    model.addAttribute("objUsuario", objUsuario);
	    InfoPaciente miInfo = infoPacienteRepository.findByPaciente(objUsuario);
	    model.addAttribute("miInfo", miInfo);
	    return "perfil";
	}

	@PostMapping("/grabarHorario")
	public String grabarHorario(
	        HttpSession sesion, 
	        @ModelAttribute("objHorario") Horario objHorario,
	        @RequestParam("dia") String dia,
	        @RequestParam("periodo") String periodo,
	        //@RequestParam("estado") String estado,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	objHorario.setPaciente(objUsuario);
	    	objHorario.setEstado("Activo");
	    	
	    	//Horario conflictoHorario = horarioRepository.findByPacienteAndDiaAndPeriodo(objUsuario, dia, periodo);
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
	    	
	        //objHorario.setPaciente(objUsuario);
	        //horarioRepository.save(objHorario);
            //model.addAttribute("exito", "Se añadió con éxito");
	        
	        // Agrega logs para depurar los valores de los parámetros
	        /*System.out.println("Usuario ID: " + objUsuario.getIdusuario());
	        System.out.println("Día: " + objHorario.getDia());
	        System.out.println("Hora de Inicio: " + objHorario.getHoraInicio());
	        System.out.println("Hora de Fin: " + objHorario.getHoraFin());

	        /*try {
	            Horario horariosConflictivos = (Horario) horarioRepository.findConflictingHorarios(
	            		objUsuario.getIdusuario(), 
	            		objHorario.getDia(), 
	            		objHorario.getHoraInicio(), 
	            		objHorario.getHoraFin()
	            		);

	            if (horariosConflictivos==null) {
	                horarioRepository.save(objHorario);
	                model.addAttribute("exito", "Se añadió con éxito");
	            } 
	            else {
	                model.addAttribute("error", "El horario está en conflicto con otro existente");
	            }

	        } 
	        catch (Exception e) {
	            e.printStackTrace(); // Log para ver el detalle de la excepción
	            model.addAttribute("error", "Ocurrió un error en la base de datos. Intenta de nuevo.");
	        }*/

	    }
	    List<Rutina> listaRutinas = rutinaRepository.findAll();
	    model.addAttribute("listaRutinas", listaRutinas);
	        
	    List<Horario> miHorario = horarioRepository.findByPacienteAndEstado(objUsuario, "Activo");
	    model.addAttribute("miHorario", miHorario);
	        
	    model.addAttribute("objHorario", new Horario());
	    return "usuario/horario";
	    //return "redirect:/login";
	}

	
	/*@GetMapping("/eliminarHora/{idhorario}")
	public String eliminarHora(
			HttpSession sesion, 
			//@ModelAttribute ("idhorario") int idhorario, 
			@PathVariable int idhorario,
			Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	//Horario horaEliminada = horarioRepository.findByIdhorario(idhorario);
	    	horarioRepository.deleteByIdhorario(idhorario);
	    	//horarioRepository.save(horaEliminada);
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
	}*/
	
	@GetMapping("/eliminarHora/{idhorario}")
	public String eliminarHora(
			HttpSession sesion, 
			@ModelAttribute ("idhorario") int idhorario, 
			//@PathVariable int idhorario,
			//@ModelAttribute("miHorario") Horario objHorario,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Horario horaEliminada = horarioRepository.findByIdhorario(idhorario);
	    	//Horario horaEliminada = horarioRepository.findByIdhorario(objHorario.getIdhorario());
	    	//horarioRepository.deleteByIdhorario(idhorario);
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
	
	@GetMapping("/verSuPerfil")
	public String verSuPerfil(
			HttpSession sesion,
			@RequestParam("idusuario") int idusuario,
			//@ModelAttribute("objUsuario") Usuario suPerfil,
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
