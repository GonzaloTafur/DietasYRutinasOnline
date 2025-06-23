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

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.AlimentoRepository;
import com.DietasYRutinasOnline.repository.CondicionRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.AlimentoService;
import com.DietasYRutinasOnline.service.DietaService;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.ObjetivoService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/dieta/")
public class DietaController {
	
	@Autowired
	private AlimentoService alimentoService;;
	
	@Autowired
	CondicionRepository condicionRepository;
	
	@Autowired
	private DietaService dietaService;
	
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
	PacienteRepository pacienteRepository;
	
	@Autowired
	RolRepository rolRepository;
	
	@GetMapping("/")
	public String verDietas(
			HttpSession sesion, 
			@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	Rol vistaUsuario = new Rol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }

	    //HistorialMed pacienteActual = HistorialMedRepository.findByPacienteAndEstado(objUsuario, true);
	    //model.addAttribute("pacienteActual", pacienteActual);
	    
	    List<Dieta> listaDietas = dietaService.getEstado(true);
	    model.addAttribute("listaDietas", listaDietas);
	    
	    /* VISTA PARA LOS PACIENTES*/
	    /*if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Deficit")) {
	    	listaDietas = dietaRepository.findByObjetivo("Deficit");
		    model.addAttribute("listaDietas", listaDietas);
		    
		    if(pacienteActual.getCondicion().equals("Lacteos")) {
		    	Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicion(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Gluten")) {
		    	Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionAndObjetivo(gluten, "Deficit");
		    	model.addAttribute("listaDietas", listaDietas);
		    }    
	    }
	    
	    else if(pacienteActual!=null && pacienteActual.getObjetivo().equals("Volumen")) {
	    	listaDietas = dietaRepository.findByObjetivo("Volumen");
	    	model.addAttribute("listaDietas", listaDietas);
	    	
	    	if(pacienteActual.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	
		    	listaDietas = dietaRepository.findByCondicion(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionAndObjetivo(gluten, "Volumen");
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
	    
	    else if (pacienteActual!=null){
	    	listaDietas = dietaRepository.findByEstado(true);
	    	model.addAttribute("listaDietas", listaDietas);
	    	
	    	if(pacienteActual.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	model.addAttribute("gluten", gluten);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(gluten);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(pacienteActual.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }*/
	    
	    return "dietas/menu_dietas";
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
	public String seguirDieta(
	        HttpSession sesion,
	        @ModelAttribute("objDieta") Dieta objDieta,
	        Model model) {
	    
	    //Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		Paciente paciente = (Paciente) sesion.getAttribute("usuario");
	    if (paciente!=null) {
	        //Rol vistaUsuario = objUsuario.getRol();
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);
	        
			HistorialMed miHistorial = historialMedService.getEstado(true);
			Paciente pacienteActual = pacienteRepository.findByHistorialMedico(miHistorial);
	        //HistorialMed pacienteActual = historialMedRepository.findByPacienteAndEstado(objUsuario, true);
	        model.addAttribute("pacienteActual", pacienteActual);
	        
	        if (pacienteActual!=null) {
	            /*Dieta dietaPaciente = dietaRepository.findById(objDieta.getCodigo()).orElse(null);
	            
	            if (dietaPaciente!=null && !miHistorial.getDieta().contains(dietaPaciente)) {
	                miHistorial.getDieta().add(dietaPaciente);
	                historialMedService.guardar(miHistorial);
	                model.addAttribute("exito", "Se guardó a tu perfil con éxito");  
	            } 
	            else {
	                model.addAttribute("excepcion", "Ya estás haciendo esta dieta");
	            }

	            Transaccion objTransaccion = new Transaccion();
	            objTransaccion.setFecha(LocalDateTime.now());
	            objTransaccion.setTipo("Seguir Dieta");
	            objTransaccion.setHistorialMedico(miHistorial);
	            //objTransaccion.setUsuario(objUsuario);
	            objTransaccion.setDieta(dietaPaciente);
	            transaccionRepository.save(objTransaccion);
                
	            
	            /* RETORNAR */
	            /*List<Dieta> listaDietas;
	            listaDietas = dietaService.getEstado(true);
	            model.addAttribute("listaDietas", listaDietas);
	            
	            if (pacienteActual != null && "Deficit".equals(miHistorial.getObjetivo())) {
	                listaDietas = dietaRepository.findByObjetivo("Deficit");
	                model.addAttribute("listaDietas", listaDietas);
	            } 
	            else if (pacienteActual != null && "Volumen".equals(miHistorial.getObjetivo())) {
	                listaDietas = dietaRepository.findByObjetivo("Volumen");
	                model.addAttribute("listaDietas", listaDietas);
	            }*/
	            return "dietas/menu_dietas";
	        }
	    }
	    return "index";
	}

	
	@PostMapping("/dejarSeguirDieta")
	public String dejarSeguirDieta(
			HttpSession sesion,
			@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
	    
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	//Rol vistaUsuario = objUsuario.getRol();
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);
	        
	        HistorialMed miInfo = historialMedService.getEstado(true);
			Paciente pacienteActual = pacienteRepository.findByHistorialMedico(miInfo);
		    model.addAttribute("miInfo", miInfo);
		    
		    if(miInfo!=null) {
		    	//Dieta dietaPaciente = dietaRepository.findById(objDieta.getCodigo()).orElse(null);
		    	
		    	//miInfo.getDieta().remove(dietaPaciente);		    
		    	historialMedService.guardarHistorial(miInfo);
		    	model.addAttribute("mensaje", "Dieta eliminada de tu perfil.");
		    }
		    
	        Transaccion objTransaccion = new Transaccion();
	        objTransaccion.setFecha(LocalDateTime.now());
	        objTransaccion.setTipo("Dejar seguir Dieta");
	        objTransaccion.setHistorialMedico(miInfo);
	        objTransaccion.setUsuario(objUsuario);
	        objTransaccion.setDieta(objDieta);
	        transaccionRepository.save(objTransaccion);

	        model.addAttribute("objUsuario", objUsuario);
		    
		    //HistorialMed miInfo1 = historialMedRepository.findByPacienteAndEstado(objUsuario, true);
		    model.addAttribute("miInfo", miInfo);
	        return "perfil";
	    }
		return "index";
	}

	
	// ---- VENTANA CREAR DIETA -------------------------------------------------------------------
	@GetMapping("crear_dieta")
	public String crearDieta(Model model) {
		//List<Alimento> listaAlimentos = alimentoRepository.findAll();
		List<Alimento> listaAlimentos = alimentoService.getEstado(true);
		model.addAttribute("listaAlimentos", listaAlimentos);

		List<Condicion> listaCondiciones = condicionRepository.findAll();
		model.addAttribute("listaCondiciones", listaCondiciones);
		List<Objetivo> lstObjetivo = objetivoService.getEstado(true);
		model.addAttribute("lstObjetivo", lstObjetivo);
		
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
	public String grabarDieta(HttpSession sesion, Dieta d, Model model) {	
	    //d.setNutriologo(objUsuario);
	    //d.setEstado(true);
	    dietaService.grabarDieta(d);
		return "redirect:/dieta/";
	
	}

	// ---- VER DETALLE DIETA -------------------------------------------------------------------
	/*@GetMapping("/verDetalleDieta/{codigo}")
	public String verDetalleDieta(
			HttpSession sesion,
			//@RequestParam("iddieta") Long codigo,
			//@ModelAttribute("objDieta") Dieta objDieta,
			@PathVariable Long codigo, Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	//Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);

			//Dieta detalleDieta = dietaRepository.findByCodigo(codigo);
			//model.addAttribute("detalleDieta", detalleDieta);
			Dieta detDieta = dietaService.getCodigo(codigo);
			model.addAttribute("detDieta", detDieta);

			//Usuario nutriActual = (Usuario) sesion.getAttribute("usuario");
			//model.addAttribute("esCreador", nutriActual.getCodigo() == detalleDieta.getNutriologo().getCodigo());
			
			/*HistorialMed hm = historialMedService.getEstado(true);
			Paciente pacienteActual = pacienteRepository.findByHistorialMedico(hm);
			model.addAttribute("pacienteActual", pacienteActual);
			
			return "dietas/detalle_dieta";
	    }
        return "iniciar_sesion";
	}*/

	@GetMapping("/verDetalleDieta/{codigo}")
	public String verDetalleDieta(
			//HttpSession sesion,
			//@RequestParam("iddieta") Long codigo,
			//@ModelAttribute("objDieta") Dieta objDieta,
			@PathVariable Long codigo, Model model) {
		
		try {
			Dieta detDieta = dietaService.getCodigo(codigo);
			model.addAttribute("detDieta", detDieta);
			return "dietas/detalle_dieta";
		} 
		catch (Exception e) {
			System.out.println("No se ha podido realizar la petición");
			return "redirect:/dieta/";
		}
		
	}	
	
	@PostMapping("/seguirDieta2")
	public String seguirDieta2(
			HttpSession sesion,
			@ModelAttribute("objDieta") Dieta objDieta,
			@ModelAttribute("miInfo") HistorialMed objHistorialMed,
			//@RequestParam("idHistorialMed")int idHistorialMed, 
			Model model) {
	    
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	/*Rol vistaUsuario = objUsuario.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        */
	        
	        HistorialMed miHistorial = historialMedService.getEstado(true);
			Paciente pacienteActual = pacienteRepository.findByHistorialMedico(miHistorial);
		    model.addAttribute("pacienteActual", pacienteActual);
		    
		    /*if(pacienteActual.getObjetivo().equals("Volumen") && objDieta.getObjetivo().equals("Volumen")) {
		    	
		    }*/
	        if(pacienteActual!=null) {

				//HistorialMed dietaPaciente = historialMedRepository.findByPacienteAndEstado(objUsuario, true);
	        	miHistorial.getDieta().add(objDieta);
	        	historialMedService.guardarHistorial(miHistorial);
	        
	        	Transaccion objTransaccion = new Transaccion();
	        	objTransaccion.setFecha(LocalDateTime.now());
	        	objTransaccion.setTipo("Seguir Dieta");
	        	objTransaccion.setHistorialMedico(miHistorial);
	        	objTransaccion.setUsuario(objUsuario);
	        	objTransaccion.setDieta(objDieta);
	        	transaccionRepository.save(objTransaccion);
	        }
	        //dietaPaciente.setDieta(objDieta);
	        
	    	/*Horario conflictoHorario = horarioRepository.findByPacienteAndDiaAndPeriodo(objUsuario, dia, periodo);
	    	if(conflictoHorario!=null) {	    		
	    		model.addAttribute("error", "El horario está en conflicto con otro existente");
	    	}
	    	else {
	    		horarioRepository.save(objHorario);
	    		model.addAttribute("exito", "Se añadió con éxito");
	    	}*/
	    	//model.addAttribute("exito", "Se ha guardado la dieta a tu perfil");
	        List<Dieta> listaDietas = dietaService.getEstado(true);
		    model.addAttribute("listaDietas", listaDietas);
	    	return "dietas/menu_dietas";
	    }
		return "index";
	}
	
	// ---- EDITAR DIETA -------------------------------------------------------------------
	@PostMapping("editar_dieta/{codigo}")
	public String editarDieta(
			//@RequestParam("id_dieta") Long codigo, 
			@PathVariable Long codigo, Model model){
		//Dieta objDieta = dietaRepository.findByCodigo(codigo);
		//model.addAttribute("objDieta", objDieta);
		
		List<Alimento> listaAlimentos = alimentoService.getEstado(true);
		model.addAttribute("listaAlimentos", listaAlimentos);

		List<Condicion> listaCondiciones = condicionRepository.findByEstado(true);
		model.addAttribute("listaCondiciones", listaCondiciones);
		
		return "dietas/editar_dieta";			
	}
	
	@PostMapping("actualizar_dieta")
	public String actualizarDieta(
			HttpSession sesion,
			@ModelAttribute("objDieta") Dieta d,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
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
	    }        
        List<Dieta> listaDietas = dietaService.getEstado(true);
        model.addAttribute("listaDietas", listaDietas);
		return "dietas/menu_dietas";
	}

}
