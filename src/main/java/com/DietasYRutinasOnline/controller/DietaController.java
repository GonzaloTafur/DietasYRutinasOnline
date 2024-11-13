package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Ejercicio;
import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.AlimentoRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;


@Controller
@RequestMapping("/dieta")
public class DietaController {
	
	@Autowired
	AlimentoRepository alimentoRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;

	@GetMapping("/verAlimentos")
	public String verAlimentos(Model model) {
		List<Alimento> listaAlimentos = alimentoRepository.findAll();
		model.addAttribute("listaAlimentos", listaAlimentos);
		
		List<String> cbxAlimentos = alimentoRepository.findDistinctTipos();
		if (cbxAlimentos.size() > 4) {
			cbxAlimentos = cbxAlimentos.subList(0, 4);
	    }
		model.addAttribute("cbxAlimentos", cbxAlimentos);
		return "dietas/lista_alimentos";
	}
	
	@GetMapping("/buscarAlimentos")
	public String buscarEjercicios(String tipo, Model model) {
		List<String> cbxAlimentos = alimentoRepository.findDistinctTipos();
		if (cbxAlimentos.size() > 4) {
			cbxAlimentos = cbxAlimentos.subList(0, 4);
	    }
		
		List<Alimento> listaAlimentos = alimentoRepository.findByTipo(tipo);
		model.addAttribute("listaAlimentos", listaAlimentos);
		
		model.addAttribute("cbxAlimentos", cbxAlimentos);
		return "dietas/lista_alimentos";
	}
	
	@PostMapping("/seguirDieta")
	public String seguirDieta(
	        HttpSession sesion,
	        @ModelAttribute("objDieta") Dieta objDieta,
	        Model model) {
	    
	    Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario != null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        
	        InfoPaciente pacienteActual = infoPacienteRepository.findByPaciente(objUsuario);
	        model.addAttribute("pacienteActual", pacienteActual);
	        
	        if (pacienteActual!=null) {
	            // Recuperar la dieta completa de la base de datos para evitar valores nulos
	            Dieta dietaPaciente = dietaRepository.findById(objDieta.getIddieta()).orElse(null);
	            
	            if (dietaPaciente!=null && !pacienteActual.getDieta().contains(dietaPaciente)) {
	                pacienteActual.getDieta().add(dietaPaciente);
	                infoPacienteRepository.save(pacienteActual);
	                model.addAttribute("exito", "Se guardó a tu perfil con éxito");  
	            } 
	            else {
	                model.addAttribute("excepcion", "Ya estás haciendo esta dieta");
	            }

	            Transaccion objTransaccion = new Transaccion();
	            objTransaccion.setFecha(LocalDateTime.now());
	            objTransaccion.setTipo("Seguir Dieta");
	            objTransaccion.setInfopaciente(pacienteActual);
	            objTransaccion.setUsuario(objUsuario);
	            objTransaccion.setDieta(dietaPaciente); // Usar dietaCompleta aquí también
	            transaccionRepository.save(objTransaccion);
                     
	            List<Dieta> listaDietas;
	            listaDietas = dietaRepository.findByEstado("Activo");
	            model.addAttribute("listaDietas", listaDietas);
	            
	            if (pacienteActual != null && "Deficit".equals(pacienteActual.getObjetivo())) {
	                listaDietas = dietaRepository.findByObjetivo("Deficit");
	                model.addAttribute("listaDietas", listaDietas);
	            } 
	            else if (pacienteActual != null && "Volumen".equals(pacienteActual.getObjetivo())) {
	                listaDietas = dietaRepository.findByObjetivo("Volumen");
	                model.addAttribute("listaDietas", listaDietas);
	            }
	            return "dietas/menu_dietas";
	        }
	    }
	    return "index";
	}

	
	@PostMapping("/dejarSeguirDieta")
	public String dejarSeguirDieta(
			HttpSession sesion,
			@ModelAttribute("objDieta") Dieta objDieta,
			//@ModelAttribute("miInfo") InfoPaciente miInfo,
			Model model) {
	    
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        
	        InfoPaciente miInfo = infoPacienteRepository.findByPaciente(objUsuario);
	        //infoPacienteRepository.findByPaciente(objUsuario);
		    model.addAttribute("miInfo", miInfo);
		    
		    if(miInfo!=null) {
		    	/*Dieta dietaCompleta = dietaRepository.findById(objDieta.getIddieta()).orElse(null);
	            
	            if (dietaCompleta!=null && !pacienteActual.getDieta().contains(dietaCompleta)) {
	                miInfo.getDieta().add(dietaCompleta);
	                infoPacienteRepository.save(pacienteActual);
	                model.addAttribute("exito", "Se guardó a tu perfil con éxito");  
	            }*/
		    	Dieta dietaPaciente = dietaRepository.findById(objDieta.getIddieta()).orElse(null);
		    	
		    	miInfo.getDieta().remove(dietaPaciente);		    
		    	infoPacienteRepository.save(miInfo);
		    	model.addAttribute("mensaje", "Dieta eliminada de tu perfil.");
		    }
		    
	        Transaccion objTransaccion = new Transaccion();
	        objTransaccion.setFecha(LocalDateTime.now());
	        objTransaccion.setTipo("Dejar seguir Dieta");
	        objTransaccion.setInfopaciente(miInfo);
	        objTransaccion.setUsuario(objUsuario);
	        objTransaccion.setDieta(objDieta);
	        transaccionRepository.save(objTransaccion);
	        
	        /*if(pacienteActual!=null && pacienteActual.getDieta().contains(objDieta)) {
	        	
	        }*/
	        model.addAttribute("objUsuario", objUsuario);
		    
		    //InfoPaciente miInfo = infoPacienteRepository.findByPaciente(objUsuario);
		    //model.addAttribute("miInfo", miInfo);
	        return "usuario/paciente";
	    }
		return "index";
	}

	
	// ---- VENTANA CREAR DIETA -------------------------------------------------------------------
	@PostMapping("/crearDieta")
	public String crearDieta(Model model) {
		List<Alimento> listaAlimentos = alimentoRepository.findAll();
		model.addAttribute("listaAlimentos", listaAlimentos);
		Dieta objDieta = new Dieta();
		model.addAttribute("objDieta", objDieta);
		return "dietas/nueva_dieta";
	}
	
	@PostMapping("/grabarDieta")
	public String grabarDieta(
			HttpSession sesion, 
			@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	objDieta.setNutriologo(objUsuario);
    		objDieta.setEstado("Activo");
            dietaRepository.save(objDieta);
            
            Transaccion objTransaccion = new Transaccion();
            objTransaccion.setFecha(LocalDateTime.now());
            objTransaccion.setTipo("Creación");
            objTransaccion.setUsuario(objUsuario);
            objTransaccion.setDieta(objDieta);
            transaccionRepository.save(objTransaccion);
            
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }        
        List<Dieta> listaDietas = dietaRepository.findByEstado("Activo");
        model.addAttribute("listaDietas", listaDietas);
		return "dietas/menu_dietas";
	}

	// ---- VER DETALLE DIETA -------------------------------------------------------------------
	@GetMapping("/verDetalleDieta")
	public String verDetalleDieta(
			HttpSession sesion,
			@RequestParam("iddieta") int iddieta,
			@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    } 
        
		Dieta detalleDieta = dietaRepository.findByIddieta(iddieta);
		model.addAttribute("detalleDieta", detalleDieta);
		
		Usuario nutriActual = (Usuario) sesion.getAttribute("usuario");
		model.addAttribute("esCreador", nutriActual.getIdusuario() == detalleDieta.getNutriologo().getIdusuario());
		
		InfoPaciente pacienteActual = infoPacienteRepository.findByPaciente(objUsuario);
		model.addAttribute("pacienteActual", pacienteActual);
		
		return "dietas/detalle_dieta";
	}
	
	@PostMapping("/seguirDieta2")
	public String seguirDieta2(
			HttpSession sesion,
			@ModelAttribute("objDieta") Dieta objDieta,
			@ModelAttribute("miInfo") InfoPaciente objInfoPaciente,
			//@RequestParam("idinfopaciente")int idinfopaciente, 
			Model model) {
	    
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	        
	        InfoPaciente dietaPaciente = infoPacienteRepository.findByPaciente(objUsuario);
	        
	        InfoPaciente pacienteActual = infoPacienteRepository.findByPaciente(objUsuario);
		    model.addAttribute("pacienteActual", pacienteActual);
		    
		    /*if(pacienteActual.getObjetivo().equals("Volumen") && objDieta.getObjetivo().equals("Volumen")) {
		    	
		    }*/
	        if(pacienteActual!=null) {
	        	dietaPaciente.getDieta().add(objDieta);
	        	infoPacienteRepository.save(dietaPaciente);
	        
	        	Transaccion objTransaccion = new Transaccion();
	        	objTransaccion.setFecha(LocalDateTime.now());
	        	objTransaccion.setTipo("Seguir Dieta");
	        	objTransaccion.setInfopaciente(dietaPaciente);
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
	        List<Dieta> listaDietas = dietaRepository.findByEstado("Activo");
		    model.addAttribute("listaDietas", listaDietas);
	    	return "dietas/menu_dietas";
	    }
		return "index";
	}
	
	// ---- VENTANA EDITAR DIETA -------------------------------------------------------------------
	@PostMapping("/editarDieta")
	public String editarDieta(
			@RequestParam("iddieta")int iddieta, 
			Model model){
		Dieta objDieta = dietaRepository.findByIddieta(iddieta);
		model.addAttribute("objDieta", objDieta);
		
		List<Alimento> listaAlimentos = alimentoRepository.findAll();
		model.addAttribute("listaAlimentos", listaAlimentos);
		return "dietas/editar_dieta";			
	}
	
	@PostMapping("/actualizarDieta")
	public String actualizarDieta(
			HttpSession sesion,
			@ModelAttribute("objDieta") Dieta objDieta,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	Dieta dietaActual = dietaRepository.findByIddieta(objDieta.getIddieta());
        	dietaActual.setNombre(objDieta.getNombre());
        	dietaActual.setObjetivo(objDieta.getObjetivo());
        	dietaActual.setDescripcion(objDieta.getDescripcion());
        	dietaActual.setAlimento(objDieta.getAlimento());
            dietaRepository.save(dietaActual);
            
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }        
        List<Dieta> listaDietas = dietaRepository.findByEstado("Activo");
        model.addAttribute("listaDietas", listaDietas);
		return "dietas/menu_dietas";
	}

}
