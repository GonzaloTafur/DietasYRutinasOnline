package com.DietasYRutinasOnline.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Notificacion;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.ENUM.Dia;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.NutriologoRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.AsistenciaService;
import com.DietasYRutinasOnline.service.DietaService;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.HorarioService;
import com.DietasYRutinasOnline.service.ReunionService;
import com.DietasYRutinasOnline.service.RutinaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reunion")
public class ReunionController {
	
	@Autowired
	private ReunionService reunionService;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	private AsistenciaService asistenciaService;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	HistorialMedRepository historialMedRepository;
	
	@Autowired
	private HorarioService horarioService;
	
	@Autowired
	private RutinaService rutinaService;
	
	@Autowired
	private DietaService dietaService;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	@Autowired
	NotificacionRepository notificacionRepository;

	@Autowired
	private ReunionRepository reunionRepository;

	@Autowired
	private AsistenciaRepository asistenciaRepository;

	@Autowired
	private NutriologoRepository nutriologoRepository;

	@Autowired
	private HistorialMedService historialMedService;

	
	@GetMapping("/ver_reuniones")
	public String verReuniones(
	        HttpSession sesion,
	        @RequestParam("codigo") Long codigo,
	        Model model) {

	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");

	    if (paciente!=null) {
			Nutriologo nutriologoReunion = nutriologoRepository.findByCodigo(codigo);
			model.addAttribute("nutriologoReunion", nutriologoReunion);

			List<Reunion> listaReuniones = reunionRepository.findByNutriologoAndEstado(nutriologoReunion, true);
			model.addAttribute("listaReuniones", listaReuniones);

			Map<Long, Boolean> estadoAsistencia = listaReuniones.stream().collect(
				Collectors.toMap(
					Reunion::getCodigo, objReunion -> {
						Asistencia asistencia = asistenciaRepository.findByPacienteAndReunionAndEstado(paciente, objReunion, true);
						return asistencia!=null ? asistencia.getEstado() : false;
					}
				));
			model.addAttribute("estadoAsistencia", estadoAsistencia);

			//List<Reunion> listaReuniones = reunionService.verReuniones(model, codigo, paciente);
			//model.addAttribute("listaReuniones", listaReuniones);

			return "reuniones/reuniones";
		}
		return "/iniciar_sesion";
	}
	
	@GetMapping("/confirmarAsistencia")
	public String confirmarAsistencia(HttpSession sesion,@RequestParam("codigo") Long codigo, Model model) {
		try{ 
	    	Paciente paciente = (Paciente) sesion.getAttribute("paciente");
	    	if (paciente!=null) {
			
				Reunion reunion = reunionService.getCodigo(codigo);
				Asistencia asistenciaExistente = asistenciaRepository.findByPacienteAndReunionAndEstado(paciente, reunion, true);
				if (asistenciaExistente==null) {

					Asistencia asistencia = new Asistencia();
					asistencia.setPaciente(paciente);
					asistencia.setReunion(reunion);
					asistencia.setEstado(true);
					asistencia.setFecha(LocalDate.now());
					asistenciaRepository.save(asistencia);
					//asistenciaService.confirmarAsistencia(sesion, codigo, asistencia);
					
					/*Transaccion objTransaccion = new Transaccion();
					objTransaccion.setFecha(LocalDateTime.now());
					objTransaccion.setTipo("Acceso a reunión");
					objTransaccion.setReunion(objReunion);
					//objTransaccion.setPaciente(objUsuario);
					objTransaccion.setAsistencia(asistencia);
					transaccionRepository.save(objTransaccion);
				
					Notificacion objNotificacion = new Notificacion();
					objNotificacion.setTransaccion(objTransaccion);
					objNotificacion.setRol("Nutriologo");
					objNotificacion.setMensaje("El paciente " + paciente.getNombres() + " confirmó unirse a la reunión " + objReunion.getMotivo());
					objNotificacion.setTimestamp(LocalDateTime.now());
					objNotificacion.setEstado(true);
					notificacionRepository.save(objNotificacion);*/
					return "redirect:/reunion/ver_reuniones";
				}
				else{
					System.out.println("No se pudo confirmar asistencia");
					return "redirect:/home/ver_nutriologos";
				}
			}
			else{
				return "iniciar_sesion";
			}
			
		}
		catch(Exception e){
			System.out.println("Error al confirmar asistencia: " + e.getMessage());
			return "redirect:/home/ver_nutriologos";
		}

	}

	/*@GetMapping("/confirmarAsistencia")
	public String confirmarAsistencia(HttpSession sesion, Long codigo){
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		asistenciaService.confirmarAsistencia(paciente, codigo);
		return "redirect:/home/ver_nutriologos";
	}*/

	@GetMapping("/cancelarAsistencia")
	public String cancelarAsistencia(@RequestParam("codigo") Long codigo, HttpSession sesion) {

	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		if (paciente!=null) {
			/*Reunion objReunion = reunionService.getCodigo(codigo);
			Asistencia as = asistenciaRepository.findByPacienteAndReunionAndEstado(paciente, objReunion, true);
			if (as!=null) {
				as.setEstado(false);
				asistenciaRepository.save(as);
			}*/
			asistenciaService.cancelarAsistencia(paciente, codigo);
			return "redirect:/reunion/ver_reuniones"; 
		}
		else {
			return "iniciar_sesion";
		}
	}

	@GetMapping("/verSuPerfil")
	public String perfilNutriologo(
	        HttpSession sesion,
	        @RequestParam("codigo") Long codigo,
	        Model model) {

		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");

		if(paciente!=null){
			model.addAttribute("paciente", paciente);

			Nutriologo perfNutriologo = nutriologoRepository.findByCodigo(codigo);
			model.addAttribute("pnutriologo", perfNutriologo);

			List<Reunion> re = reunionService.getNutriologo(perfNutriologo, true);
			model.addAttribute("objReunion", re);
			//model.addAttribute("dias", dias);
				
			List<Rutina> susRutinas = rutinaService.getNutriologo(perfNutriologo);
			model.addAttribute("misRutinas", susRutinas);
				
			List<Dieta> susDietas = dietaService.getNutriologo(perfNutriologo);
			model.addAttribute("misDietas", susDietas);
			
			return "perfil/perfil_nutriologo";
		}
		else if(nutriologo!=null){
			model.addAttribute("nutriologo", nutriologo);

			Paciente perfilPaciente = pacienteRepository.findByCodigo(codigo);
			model.addAttribute("ppaciente", perfilPaciente);

			List<HistorialMed> listaSeguimiento = historialMedService.getSegumiento(perfilPaciente);
			model.addAttribute("listaSeguimiento", listaSeguimiento);
			Collections.reverse(listaSeguimiento);

			return "perfil/perfil_paciente";
		}
		return "iniciar_sesion";
	}
	
	
	/* NUTRIOLOGOS */
	
	@GetMapping("/nueva_reunion")
	public String nuevaReunion(HttpSession sesion, Model model) {
		Reunion re = new Reunion();
		model.addAttribute("re", re);
		model.addAttribute("cbxDias", Dia.values());
		return "reuniones/programar_reunion";
	}
	
	/*@PostMapping("/grabarReunion")
	public String grabarReunion(
			HttpSession sesion, 
			@ModelAttribute("objReunion") Reunion objReunion,
			@RequestParam("dia") String dia,
			Model model) {
		
		Nutriologo objUsuario = (Nutriologo) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	//Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	    	//boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);
	        
	        Reunion reunionUsuario = reunionRepository.findByNutriologoAndDiaAndEstado(objUsuario, dia, true);
	        
	        if(reunionUsuario==null) {
	        	objReunion.setNutriologo(objUsuario);
	        	objReunion.setEstado(true);
	        	reunionRepository.save(objReunion);
            
	        	Transaccion objTransaccion = new Transaccion();
	        	objTransaccion.setFecha(LocalDateTime.now());
	        	objTransaccion.setTipo("Creación");
	        	//objTransaccion.setNutriologo(objUsuario);
	        	objTransaccion.setReunion(objReunion);
	        	transaccionRepository.save(objTransaccion); 
            
	        	Notificacion objNotificacion = new Notificacion();
	        	objNotificacion.setTransaccion(objTransaccion);
	        	objNotificacion.setRol("Paciente");
	        	objNotificacion.setMensaje("Un nutriologo a programado una nueva reunión.");
	        	objNotificacion.setTimestamp(LocalDateTime.now());
	        	objNotificacion.setEstado(true);
	        	notificacionRepository.save(objNotificacion);
	        }
	        else {
	        	model.addAttribute("error", "No se pudo programar la reunión.");
	        	return "usuario/programar_reunion";
	        }
	    }
	    model.addAttribute("objUsuario", objUsuario);
	    
		//List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    //model.addAttribute("misRutinas", misRutinas);
	    
	    //List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    //model.addAttribute("misDietas", misDietas);
	    
		return "perfil";
	}
	*/

	@PostMapping("grabar_reunion")
	public String grabarReunion(HttpSession sesion, Model model, Dia dia, Reunion re){
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
		if (nutriologo!=null){
			//re.setNutriologo(nutriologo);
			reunionService.grabarReunion(sesion, dia, re);
			return "redirect:/perfil/";
		}
		else{
			return "iniciar_sesion";
		}
	}

	@GetMapping("/desactivarReunion/{codigo}")
	public String desactivarReunion(
			HttpSession sesion, 
			@ModelAttribute ("id_re") Long codigo, 
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Reunion re = reunionService.getCodigo(codigo);
	    	//reunionDesac.setEstado(true);
	    	//reunionService.save(reunionDesac);
	    	
			reunionService.eliminarReunion(re);
			model.addAttribute("eliminado", "El horario ha eliminado con exito");
	    	
	        /*Rol vistaUsuario = objUsuario.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);*/
			model.addAttribute("objUsuario", objUsuario);
			return "redirect:/home/ver_nutriologos";
	    }
	    
	    
	    /*List<Reunion> objReunion = reunionRepository.findByNutriologoAndEstado(objUsuario, true);
	    model.addAttribute("objReunion", objReunion);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(objUsuario);
	    model.addAttribute("misDietas", misDietas);
	    
		return "perfil";*/
		return "iniciar_sesion";
	}
	
	@GetMapping("/pacientesAsistidos")
	public String pacientesAsistidos(HttpSession sesion, Model model) {

	    Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
	    if (nutriologo!=null) {
			List<Reunion> listaReuniones = reunionService.getNutriologo(nutriologo, true);
			model.addAttribute("listaReuniones", listaReuniones);

			List<Asistencia> listaPacientes = listaReuniones.stream()
				//.flatMap(reunion -> asistenciaRepository.findByReunionAndEstado(reunion, true).stream())
				.flatMap(reunion -> asistenciaService.getReunion(reunion, true).stream())
				.collect(Collectors.toList());
			Collections.reverse(listaPacientes);
			model.addAttribute("listaPacientes", listaPacientes);

			return "reuniones/pacientes_asistidos";
		}
		else {
			return "iniciar_sesion";	
		}
	}
	
	@GetMapping("/verSuSeguimiento")
	public String verSuSeguimiento(HttpSession sesion, @RequestParam("codigo") Long codigo, Model model) {

	    Paciente suPerfil = pacienteRepository.findByCodigo(codigo);
	    if(suPerfil!=null) {
	    	List<HistorialMed> listaSeguimiento = historialMedRepository.findByPaciente(suPerfil);
	    	//model.addAttribute("listaSeguimiento", listaSeguimiento);
	    	model.addAttribute("listaSeguimiento", listaSeguimiento);
			Collections.reverse(listaSeguimiento);
			
	    	return "usuario/seguimiento";
	    }
	    model.addAttribute("error", "El usuario no existe.");
	    return "error";
	}

	@GetMapping("/verSuHorario")
	public String verSuHorario(HttpSession sesion, 
	        @RequestParam("codigo") Long codigo, 
	        Model model) {

	    /*Usuario suPerfil = usuarioRepository.findByCodigo(codigo);
	    if(suPerfil!=null) {
	    	List<Horario> suHorario = horarioRepository.findByPacienteAndEstado(suPerfil, true);
	    	model.addAttribute("suHorario", suHorario);

	    	return "usuario/vista_horario";
	    }

	    model.addAttribute("error", "El usuario no existe.");
	    return "error";*/

		horarioService.horarioPerfil(codigo);
		return "usuario/vista_horario";
	}
	
}
