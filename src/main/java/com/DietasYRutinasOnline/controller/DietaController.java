package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.DTO.DietaDTO;
import com.DietasYRutinasOnline.entity.ENUM.ObjetivoEnum;
import com.DietasYRutinasOnline.repository.AlimentoRepository;
import com.DietasYRutinasOnline.repository.CondicionRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.AlimentoService;
import com.DietasYRutinasOnline.service.DietaService;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.ObjetivoService;
import com.DietasYRutinasOnline.service.PacienteService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/dieta")
public class DietaController {
	
	@Autowired
	private AlimentoService alimentoService;;
	
	@Autowired
	CondicionRepository condicionRepository;
	
	@Autowired
	private DietaService dietaService;

	@Autowired
	private DietaRepository dietaRepository;
	
	@Autowired
	private HistorialMedService historialMedService;

	@Autowired
	private ObjetivoService objetivoService;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	@Autowired
	NotificacionRepository notificacionRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PacienteService pacienteService;
	
	@Autowired
	RolRepository rolRepository;

	
	@GetMapping("/")
	public String verDietas(
			HttpSession sesion, 
			//@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
		//Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
		
		if(paciente!=null){
			model.addAttribute("paciente", paciente);
			
			List<Dieta> listaDietas = dietaService.getEstado(true);
	    	model.addAttribute("listaDietas", listaDietas);

			/*List<Dieta> dietasPaciente = dietaRepository.findByPaciente(paciente);
			List<DietaDTO> listaDTO = listaDietas.stream()
				.map(dieta -> {
					// Se verifica si el paciente sigue una dieta de la lista
					boolean sigue = dietasPaciente.contains(dieta); 
					return new DietaDTO(dieta, sigue);
				})
				.toList();
			model.addAttribute("listaDietas", listaDTO);*/
			return "dietas/menu_dietas";
	    }
		else if(nutriologo!=null){
			List<Dieta> listaDietas = dietaService.getEstado(true);
	    	model.addAttribute("listaDietas", listaDietas);

			return "dietas/menu_dietas";
		}
	    return "iniciar_sesion";
	}
	

	@GetMapping("ver_alimentos")
	public String verAlimentos(Model model) {
		List<Alimento> listaAlimentos = alimentoService.getEstado(true);
		model.addAttribute("listaAlimentos", listaAlimentos);
		

		List<String> cbxAlimentos = alimentoService.cbxAlimento();
		if (cbxAlimentos.size() > 4) {
			cbxAlimentos = cbxAlimentos.subList(0, 4);
	    }

		model.addAttribute("cbxAlimentos", cbxAlimentos);
		return "dietas/lista_alimentos";
	}
	
	@GetMapping("/buscarAlimentos")
	public String buscarEjercicios(String tipo, Model model) {
		List<String> cbxAlimentos = alimentoService.cbxAlimento();
		if (cbxAlimentos.size() > 4) {
			cbxAlimentos = cbxAlimentos.subList(0, 4);
	    }
		
		List<Alimento> listaAlimentos = alimentoService.getTipo(tipo);
		model.addAttribute("listaAlimentos", listaAlimentos);
		
		model.addAttribute("cbxAlimentos", cbxAlimentos);
		return "dietas/lista_alimentos";
	}
	
	@PostMapping("/seguirDieta")
	public String seguirDieta(HttpSession sesion, 
		@RequestParam("codigo") Long codigo, 
		Model model) {
	    
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
	    if (paciente!=null) {

			Dieta objDieta = dietaRepository.findByCodigoAndPaciente(codigo, paciente);

			if(objDieta==null){

				Dieta dieta = dietaRepository.findByCodigo(codigo);

				//paciente.getDieta().add(dieta);
				//pacienteService.guardarPaciente(paciente);
				pacienteService.seguirDieta(paciente, dieta);
				return "redirect:/perfil/";
			
				//paciente.setDieta(codigo);
				//pacienteService.guardarPaciente(paciente);

				//pacienteService.seguirDieta(paciente, objDieta);
	        
				/*Transaccion objTransaccion = new Transaccion();
	            objTransaccion.setFecha(LocalDateTime.now());
	            objTransaccion.setTipo("Seguir Dieta");
	            objTransaccion.setHistorialMedico(miHistorial);
	            //objTransaccion.setUsuario(objUsuario);
	            objTransaccion.setDieta(dietaPaciente);
	        	transaccionRepository.save(objTransaccion);*/
			}
			else{
				return "redirect:/dieta/";
			}
	        
	    }
	    return "index";
	}

	
	@PostMapping("/dejarSeguirDieta")
	public String dejarSeguirDieta(
			HttpSession sesion,
			@RequestParam("codigo") Long codigo,
			Model model) {
	    
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
	    if (paciente!=null) {

			Dieta dieta = dietaRepository.findByCodigo(codigo);
	        pacienteService.dejarSeguirDieta(paciente, dieta);
		    
	        /*Transaccion objTransaccion = new Transaccion();
	        objTransaccion.setFecha(LocalDateTime.now());
	        objTransaccion.setTipo("Dejar seguir Dieta");
	        //objTransaccion.setHistorialMedico(miInfo);
	        objTransaccion.setUsuario(paciente);
	        objTransaccion.setDieta(dieta);
	        transaccionRepository.save(objTransaccion);*/

	        //model.addAttribute("paciente", paciente);
		    
			System.out.println("=================================== Has dejado de seguir la dieta " + dieta.getNombre() + " ===================================");
	        return "redirect:/perfil/";
	    }
		return "index";
	}

	
	// ---- VENTANA CREAR DIETA -------------------------------------------------------------------
	@GetMapping("/crear_dieta")
	public String crearDieta(HttpSession session, Model model) {
		Nutriologo nutriologo = (Nutriologo) session.getAttribute("nutriologo");
	    if (nutriologo!=null) {
			//List<Alimento> listaAlimentos = alimentoRepository.findAll();
			List<Alimento> listaAlimentos = alimentoService.getEstado(true);
			model.addAttribute("listaAlimentos", listaAlimentos);

			List<Condicion> listaCondiciones = condicionRepository.findAll();
			model.addAttribute("listaCondiciones", listaCondiciones);

			/*List<Objetivo> lstObjetivo = objetivoService.getEstado(true);
			model.addAttribute("lstObjetivo", lstObjetivo);*/
			model.addAttribute("rbtObjetivo", ObjetivoEnum.values());
			
			//List<String> cbxAlimentos = alimentoRepository.findDistinctTipos();
			List<String> cbxAlimentos = alimentoService.cbxAlimento();
			if (cbxAlimentos.size() > 4) {
				cbxAlimentos = cbxAlimentos.subList(0, 4);
			}
			model.addAttribute("cbxAlimentos", cbxAlimentos);
			
			Dieta d = new Dieta();
			model.addAttribute("d", d);
			return "dietas/nueva_dieta";
		}
		else{
			return "iniciar_sesion";
		}
	}
	
	//@PostMapping("grabar_dieta")
	/*public String grabarDieta(
			HttpSession sesion, 
			@ModelAttribute("d") Dieta d,
			Model model) {
		
		try{
			Nutriologo objUsuario = (Nutriologo) sesion.getAttribute("usuario");
	        if (objUsuario!=null) {
	        	//d.setNutriologo(objUsuario);
	    		//d.setEstado(true);
	            dietaRepository.save(d);
	            
	        	Transaccion objTransaccion = new Transaccion();
	        	objTransaccion.setFecha(LocalDateTime.now());
	            objTransaccion.setTipo("Creación");
	            objTransaccion.setUsuario(objUsuario);
	            objTransaccion.setDieta(d);
	            transaccionRepository.save(objTransaccion);
	            
		        Rol vistaUsuario = objUsuario.getRol();
		        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
		        model.addAttribute("esPaciente", esPaciente);
		    }        
	        List<Dieta> listaDietas = dietaRepository.findByEstado(true);
	        model.addAttribute("listaDietas", listaDietas);
			return "redirect:/home/ver_dietas";
		}
		catch(Exception ex) {
			return "dietas/nueva_dieta";
		}
		
	}*/

	@PostMapping("grabar_dieta")
	public String grabarDieta(HttpSession session, Dieta d, Model model) {
		Nutriologo nutriologo = (Nutriologo) session.getAttribute("nutriologo");
	    if (nutriologo!=null) {
			d.setNutriologo(nutriologo);
			//d.setEstado(true);
			dietaService.grabarDieta(d);
			return "redirect:/perfil/";
		}
		else{
			return "iniciar_sesion";
		}
	}

	// ---- VER DETALLE DIETA -------------------------------------------------------------------
	@GetMapping("/verDetalleDieta/{codigo}")
	public String verDetalleDieta(
			HttpSession sesion,
			//@RequestParam("codigo") Long codigo,
			//@ModelAttribute("objDieta") Dieta objDieta,
			@PathVariable Long codigo,
			Model model) {
		
		try {
			Paciente paciente = (Paciente) sesion.getAttribute("paciente");
			Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
			if(paciente!=null) {
				Dieta detDieta = dietaService.getCodigo(codigo);
				model.addAttribute("detDieta", detDieta);
				
				Dieta dietaPaciente = dietaRepository.findByCodigoAndPaciente(codigo, paciente);
				boolean seguida = (dietaPaciente != null);
				model.addAttribute("seguida", seguida);
				
				model.addAttribute("paciente", paciente);

				return "dietas/detalle_dieta";
			}
			else if(nutriologo!=null){
				Dieta detDieta = dietaService.getCodigo(codigo);
				model.addAttribute("detDieta", detDieta);
				return "dietas/detalle_dieta";
			}
			else{
				return "iniciar_sesion";
			}
		} 
		catch (Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return "redirect:/dieta";
		}
		
	}	
	
	// ---- EDITAR DIETA -------------------------------------------------------------------
	@GetMapping("/editar_dieta/{codigo}")
	public String editarDieta(
			//@RequestParam("id_dieta") Long codigo, 
			@PathVariable Long codigo, Model model){
		Dieta objDieta = dietaRepository.findByCodigo(codigo);
		model.addAttribute("d", objDieta);
		
		List<Alimento> listaAlimentos = alimentoService.getEstado(true);
		model.addAttribute("listaAlimentos", listaAlimentos);

		List<Condicion> listaCondiciones = condicionRepository.findByEstado(true);
		model.addAttribute("listaCondiciones", listaCondiciones);

		model.addAttribute("rbtObjetivo", ObjetivoEnum.values());
		
		return "dietas/editar_dieta";			
	}
	
	@PostMapping("/actualizar_dieta")
	public String actualizarDieta(
			HttpSession sesion,
			@ModelAttribute("d") Dieta d,
			Model model) {
		
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
        if (nutriologo!=null) {
        	/*Dieta dietaActual = dietaRepository.findByCodigo(objDieta.getCodigo());
        	dietaActual.setNombre(objDieta.getNombre());
        	dietaActual.setObjetivo(objDieta.getObjetivo());
        	dietaActual.setDescripcion(objDieta.getDescripcion());
        	dietaActual.setAlimento(objDieta.getAlimento());
        	//dietaActual.setCondicion(objDieta.getCondicion());
            dietaRepository.save(dietaActual);
            
	        /*Rol vistaUsuario = objUsuario.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);*/
			dietaService.actualizarDieta(d);
			return "redirect:/perfil/";
	    }        
		else{
			return "iniciar_sesion";
		}
	}

}
