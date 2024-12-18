package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Notificacion;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TipoUsuarioRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;

@Controller
@RequestMapping("/reunion")
public class ReunionController {
	
	@Autowired
	ReunionRepository reunionRepository;
	
	@Autowired
	TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	AsistenciaRepository asistenciaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	@Autowired
	NotificacionRepository notificacionRepository;

	
	@GetMapping("/verReuniones")
	public String verReuniones(
	        HttpSession sesion,
	        @RequestParam("idusuario") int idusuario,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    Usuario nutriologoReunion = usuarioRepository.findByIdusuario(idusuario);
	    model.addAttribute("nutriologoReunion", nutriologoReunion);

	    List<Reunion> listaReuniones = reunionRepository.findByNutriologoAndEstado(nutriologoReunion, "Activo");
	    model.addAttribute("listaReuniones", listaReuniones);

	    Map<Integer, String> estadoAsistencia = listaReuniones.stream()
	        .collect(Collectors.toMap(
	            Reunion::getIdreunion,
	            objReunion -> {
	            	Asistencia asistencia = asistenciaRepository.findByPacienteAndReunionAndEstado(objUsuario, objReunion, "Activo");
	            	return asistencia!=null ? asistencia.getEstado() : "Inactivo";
	            }
	        ));
	    model.addAttribute("estadoAsistencia", estadoAsistencia);

	    return "usuario/reuniones";
	}
	
	@GetMapping("/confirmarAsistencia")
	public String confirmarAsistencia(
			HttpSession sesion,
	        @RequestParam("idreunion") int idreunion,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario==null) {
	        return "redirect:/index";
	    }

	    TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
        model.addAttribute("esPaciente", esPaciente);
        
	    Usuario paciente = usuarioRepository.findById(objUsuario.getIdusuario())
	            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
	    
	    Reunion objReunion = reunionRepository.findById(idreunion)
	            .orElseThrow(() -> new IllegalArgumentException("Reunión no encontrada"));

	    Asistencia asistencia = asistenciaRepository.findByPacienteAndReunionAndEstado(paciente, objReunion, "Activo");
	    if (asistencia==null) {

	        asistencia = new Asistencia();
	        asistencia.setPaciente(paciente);
	        asistencia.setReunion(objReunion);
	        asistencia.setEstado("Activo");
	        asistencia.setFecha(LocalDateTime.now());
	        asistenciaRepository.save(asistencia);
	        
	        Transaccion objTransaccion = new Transaccion();
	    	objTransaccion.setFecha(LocalDateTime.now());
	    	objTransaccion.setTipo("Acceso a reunión");
	    	objTransaccion.setReunion(objReunion);
	    	objTransaccion.setUsuario(objUsuario);
	    	objTransaccion.setAsistencia(asistencia);
	    	transaccionRepository.save(objTransaccion);
        
	    	Notificacion objNotificacion = new Notificacion();
	    	objNotificacion.setTransaccion(objTransaccion);
	    	objNotificacion.setRol("Nutriologo");
	    	objNotificacion.setMensaje("El paciente " + paciente.getNombres() + " confirmó unirse a la reunión " + objReunion.getMotivo());
	    	objNotificacion.setTimestamp(LocalDateTime.now());
	    	objNotificacion.setEstado("Activo");
	    	notificacionRepository.save(objNotificacion);
	    }

	    TipoUsuario rolNutriologo = tipoUsuarioRepository.findByNomtipousu("Nutriologo");
		   
	    List<Usuario> listaNutriologos = usuarioRepository.findByTipousuarioAndEstado(rolNutriologo, "Activo");
	    model.addAttribute("listaNutriologos", listaNutriologos);
	    
	    return "usuario/lista_nutriologos";
	}

	@GetMapping("/cancelarAsistencia")
	public String cancelarAsistencia(
	        @RequestParam("idreunion") int idreunion,
	        HttpSession sesion) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    Reunion objReunion = reunionRepository.findByIdreunion(idreunion);

	    Asistencia objAsistencia = asistenciaRepository.findByPacienteAndReunionAndEstado(objUsuario, objReunion, "Activo");
	    if (objAsistencia!=null) {
	    	objAsistencia.setEstado("Inactivo");
	        asistenciaRepository.save(objAsistencia);
	    }

	    return "menu"; 
	}
	
	@GetMapping("/perfilNutriologo")
	public String perfilNutriologo(
	        HttpSession sesion,
	        @RequestParam("idusuario") int idusuario,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario != null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    Usuario suPerfil = usuarioRepository.findByIdusuario(idusuario);
	    model.addAttribute("suPerfil", suPerfil);

	    List<Reunion> objReunion = reunionRepository.findByNutriologoAndEstado(suPerfil, "Activo");
	    model.addAttribute("objReunion", objReunion);
	    
	    List<Rutina> susRutinas = rutinaRepository.findByNutriologo(suPerfil);
	    model.addAttribute("susRutinas", susRutinas);

	    List<Dieta> susDietas = dietaRepository.findByNutriologo(suPerfil);
	    model.addAttribute("susDietas", susDietas);

	    return "usuario/nutriologo";
	}
	
	
	/* NUTRIOLOGOS */
	
	@GetMapping("/crearReunion")
	public String crearReunion(HttpSession sesion, Model model) {
		Reunion objReunion = new Reunion();
		model.addAttribute("objReunion", objReunion);
		return "usuario/programar_reunion";
	}
	
	@PostMapping("/grabarReunion")
	public String grabarReunion(
			HttpSession sesion, 
			@ModelAttribute("objReunion") Reunion objReunion,
			@RequestParam("dia") String dia,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	    	boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        
	        Reunion reunionUsuario = reunionRepository.findByNutriologoAndDiaAndEstado(objUsuario, dia, "Activo");
	        
	        if(reunionUsuario==null) {
	        	objReunion.setNutriologo(objUsuario);
	        	objReunion.setEstado("Activo");
	        	reunionRepository.save(objReunion);
            
	        	Transaccion objTransaccion = new Transaccion();
	        	objTransaccion.setFecha(LocalDateTime.now());
	        	objTransaccion.setTipo("Creación");
	        	objTransaccion.setUsuario(objUsuario);
	        	objTransaccion.setReunion(objReunion);
	        	transaccionRepository.save(objTransaccion); 
            
	        	Notificacion objNotificacion = new Notificacion();
	        	objNotificacion.setTransaccion(objTransaccion);
	        	objNotificacion.setRol("Paciente");
	        	objNotificacion.setMensaje("Un nutriologo a programado una nueva reunión.");
	        	objNotificacion.setTimestamp(LocalDateTime.now());
	        	objNotificacion.setEstado("Activo");
	        	notificacionRepository.save(objNotificacion);
	        }
	        else {
	        	model.addAttribute("error", "No se pudo programar la reunión.");
	        	return "usuario/programar_reunion";
	        }
	    }
	    model.addAttribute("objUsuario", objUsuario);
	    
		List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misDietas", misDietas);
	    
		return "perfil";
	}
	
	@GetMapping("/desactivarReunion/{idreunion}")
	public String eliminarHora(
			HttpSession sesion, 
			@ModelAttribute ("idreunion") int idreunion, 
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Reunion reunionDesac = reunionRepository.findByIdreunion(idreunion);
	    	reunionDesac.setEstado("Inactivo");
	    	reunionRepository.save(reunionDesac);
	    	model.addAttribute("eliminado", "El horario ha eliminado con exito");
	    	
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
	    model.addAttribute("objUsuario", objUsuario);
	    
	    List<Reunion> objReunion = reunionRepository.findByNutriologoAndEstado(objUsuario, "Activo");
	    model.addAttribute("objReunion", objReunion);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misDietas", misDietas);
	    
		return "perfil";
	}
	
	@GetMapping("/pacientesAsistidos")
	public String pacientesAsistidos(
	        HttpSession sesion,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	        TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    List<Reunion> listaReuniones = reunionRepository.findByNutriologo(objUsuario);
	    model.addAttribute("listaReuniones", listaReuniones);

	    List<Asistencia> listaPacientes = listaReuniones.stream()
	        .flatMap(reunion -> asistenciaRepository.findByReunionAndEstado(reunion, "Activo").stream())
	        .collect(Collectors.toList());
	    Collections.reverse(listaPacientes);
	    model.addAttribute("listaPacientes", listaPacientes);

	    return "usuario/lista_pacientes2";
	}
	
	@GetMapping("/perfilPaciente")
	public String perfilPaciente(
	        HttpSession sesion,
	        @RequestParam("idusuario") int idusuario,
	        Model model) {

	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario != null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    Usuario suPerfil = usuarioRepository.findByIdusuario(idusuario);
	    model.addAttribute("suPerfil", suPerfil);
	    
	    InfoPaciente suInfo = infoPacienteRepository.findByPacienteAndEstado(suPerfil, "Activo");
	    model.addAttribute("suInfo", suInfo);
	    
	    Long asistencias = asistenciaRepository.countByPacienteAndEstado(suPerfil, "Activo");
	    model.addAttribute("asistencias", asistencias);
	
	    return "usuario/paciente";
	}
	
	@GetMapping("/verSuSeguimiento")
	public String verSuSeguimiento(
	        HttpSession sesion, 
	        @RequestParam("idusuario") int idusuario, 
	        Model model) {

	    Usuario suPerfil = usuarioRepository.findByIdusuario(idusuario);
	    if(suPerfil!=null) {
	    	List<InfoPaciente> listaSeguimiento = infoPacienteRepository.findByPaciente(suPerfil);
	    	model.addAttribute("listaSeguimiento", listaSeguimiento);
	    	model.addAttribute("suPerfil", suPerfil);

	    	return "usuario/seguimiento";
	    }
	    model.addAttribute("error", "El usuario no existe.");
	    return "error";
	}

	@GetMapping("/verSuHorario")
	public String verSuHorario(
	        HttpSession sesion, 
	        @RequestParam("idusuario") int idusuario, 
	        Model model) {

	    Usuario suPerfil = usuarioRepository.findByIdusuario(idusuario);
	    if(suPerfil!=null) {
	    	List<Horario> suHorario = horarioRepository.findByPacienteAndEstado(suPerfil, "Activo");
	    	model.addAttribute("suHorario", suHorario);

	    	return "usuario/vista_horario";
	    }
	    model.addAttribute("error", "El usuario no existe.");
	    return "error";
	}
	
}
