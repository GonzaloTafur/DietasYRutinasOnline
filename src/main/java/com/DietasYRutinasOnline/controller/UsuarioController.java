package com.DietasYRutinasOnline.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Ejercicio;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.TransaccionUsuario;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TipoUsuarioRepository;
import com.DietasYRutinasOnline.repository.TransUsuarioRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	@Autowired
	TransUsuarioRepository transUsuarioRepository;
	
	@RequestMapping(value="/validarUsuario", method= RequestMethod.POST)
	public String validarUsuario(
			HttpServletRequest request, 
			@RequestParam("correo")String correo, 
			@RequestParam("password")String password, 
			Model model) {
		
		Usuario objUsuario = usuarioRepository.findByCorreoAndPassword(correo, password);
		if (objUsuario!=null) {
			HttpSession sesion = request.getSession();
			sesion.setAttribute("usuario", objUsuario);
			
			TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        if(vistaUsuario!=null && vistaUsuario.getNomtipousu().equals("Paciente")) {
	        	model.addAttribute("esPaciente", true);
	            
	        	TransaccionUsuario objTransUsuario = new TransaccionUsuario();
		        objTransUsuario.setLogin(LocalDateTime.now());
		        objTransUsuario.setUsuario(objUsuario);
		        objTransUsuario.setTipo(vistaUsuario);
	            transUsuarioRepository.save(objTransUsuario);
	        	
	            return "menu";
	        }
	        //model.addAttribute("esPaciente", false);
	        
	        TransaccionUsuario objTransUsuario = new TransaccionUsuario();
	        objTransUsuario.setLogin(LocalDateTime.now());
	        objTransUsuario.setUsuario(objUsuario);
	        objTransUsuario.setTipo(vistaUsuario);
            transUsuarioRepository.save(objTransUsuario);
	        
			return "menu";
		}
		model.addAttribute("error", "El correo electronico y la contraseña no coinciden. Intenta de nuevo.");
		return "index";
	}
	
	@PostMapping("/registrarCuenta")
	public String registraCuenta(HttpServletRequest request, Model model) {
		List<TipoUsuario> listaTiposUsuario = tipoUsuarioRepository.findAll();
		model.addAttribute("listaTiposUsuario", listaTiposUsuario);
		
		Usuario objUsuario = new Usuario();
		model.addAttribute("objUsuario", objUsuario);
		
		//List<String> listaPaises = nacionalidadService.obtenerTodosLosPaises();
        //model.addAttribute("listaPaises", listaPaises);
		return "registrar";
	}
	
	// ---- VENTANA REGISTRAR -------------------------------------------------------------------
	@PostMapping("/grabarUsuario")
    public String grabarUsuario(
    		HttpServletRequest request,
    		@RequestParam("correo") String correo,
    		@ModelAttribute("objUsuario") Usuario objUsuario,
    		@ModelAttribute("nomtipousu") String nomtipousu,
    		Model model) {

		Usuario correoExiste = usuarioRepository.findByCorreo(correo);
		if (correoExiste!=null) {
			model.addAttribute("error", "Hubo un error al llenar correo electronico, intente de nuevo.");
			return "registrar";
        }
		objUsuario.setEstado("Activo");
        usuarioRepository.save(objUsuario);
        
        /*Transaccion objTransaccion = new Transaccion();
        objTransaccion.setFecha(LocalDateTime.now());
        objTransaccion.setTipo("CREACIÓN");
        objTransaccion.setUsuario(objUsuario);
        transaccionRepository.save(objTransaccion);*/
        
        HttpSession sesion = request.getSession();
		sesion.setAttribute("usuario", objUsuario);
		
		TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
        model.addAttribute("esPaciente", esPaciente);
        
        if(esPaciente) {
        	InfoPaciente objCuestionario = new InfoPaciente();
            model.addAttribute("objCuestionario", objCuestionario);
            //model.addAttribute("usuRegistrado", objUsuario.getIdusuario());
            
            TransaccionUsuario objTransUsuario = new TransaccionUsuario();
            //objTransUsuario.setRegistro(LocalDateTime.now());
            objTransUsuario.setUsuario(objUsuario);
            objTransUsuario.setTipo(vistaUsuario);
            transUsuarioRepository.save(objTransUsuario);
            
            return "cuestionario";
        }
        
        TransaccionUsuario objTransUsuario = new TransaccionUsuario();
        objTransUsuario.setRegistro(LocalDateTime.now());
        objTransUsuario.setUsuario(objUsuario);
        objTransUsuario.setTipo(vistaUsuario);
        transUsuarioRepository.save(objTransUsuario);
        
        return "menu";
		/*
        TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
        if(vistaUsuario != null && vistaUsuario.getNomtipousu().equals("Paciente")) {
        	model.addAttribute("esPaciente", true);
            Cuestionario objCuestionario = new Cuestionario();
            model.addAttribute("objCuestionario", objCuestionario);
            model.addAttribute("usuRegistrado", objUsuario.getIdusuario());
            return "cuestionario";
        }
        model.addAttribute("esPaciente", false);
        return "menu";
        */
    }
	
	// ---- VENTANA CUESTIONARIO -------------------------------------------------------------
	//@RequestMapping(value="/grabarCuestionario", method=RequestMethod.POST)
	@PostMapping("/grabarCuestionario")
	public String grabarCuestionario(
			HttpSession sesion,
	        @ModelAttribute("objCuestionario") InfoPaciente objCuestionario,
	        //@RequestParam("idusuario") int idusuario,
	        //@ModelAttribute("usuRegistrado") int idusuario,
	        Model model) {

		//Usuario usuRegistrado = usuarioRepository.findByIdusuario(idusuario);
		//objCuestionario.setPaciente(usuRegistrado);
		
		//Usuario usuarioActualizado = usuarioRepository.findById(objUsuario.getIdusuario()).orElse(null);
		//objCuestionario.setPaciente(usuarioActualizado);
		//cuestionarioRepository.save(objCuestionario);

		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		if (objUsuario!=null) {
			Usuario usuRegistrado = usuarioRepository.findById(objUsuario.getIdusuario()).orElse(null);
			objCuestionario.setPaciente(usuRegistrado);
			infoPacienteRepository.save(objCuestionario);

			Transaccion objTransaccion = new Transaccion();
            objTransaccion.setFecha(LocalDateTime.now());
            objTransaccion.setTipo("CREACION");
            objTransaccion.setInfopaciente(objCuestionario);
            objTransaccion.setUsuario(objUsuario);
            transaccionRepository.save(objTransaccion);
			
			TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
		    model.addAttribute("esPaciente", esPaciente);
		}
		//objCuestionario.setPaciente(objUsuario);
	    //cuestionarioRepository.save(objCuestionario);
	    //model.addAttribute("esPaciente", true);
	    return "menu";
	}
	
	/*@PostMapping("/grabarCuestionario")
	public String grabarCuestionario(
			HttpSession sesion, 
			@ModelAttribute("objCuestionario") Cuestionario objCuestionario,
			Model model) {
		
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
        if (objUsuario!=null) {
        	objCuestionario.setPaciente(objUsuario);
            cuestionarioRepository.save(objCuestionario);
            
	        TipoUsuario vistaUsuario = objUsuario.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }        
        //List<Dieta> listaDietas = dietaRepository.findByEstado("Activo");
        //model.addAttribute("listaDietas", listaDietas);
		return "menu";
	}*/

	@GetMapping("/omitir")
	public String omitir(Model model) {
		model.addAttribute("esPaciente", true);
		return "menu";
	}
	
	@GetMapping("/cerrarSesion")
	public String cerrarSesion(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		/*Transaccion objTransaccion = new Transaccion();
        objTransaccion.setFecha(LocalDateTime.now());
        objTransaccion.setTipo("Logout");
        objTransaccion.setUsuario(objUsuario);
        transaccionRepository.save(objTransaccion);*/
	
		TransaccionUsuario objTransUsuario = new TransaccionUsuario();
        objTransUsuario.setLogout(LocalDateTime.now());
        objTransUsuario.setUsuario(objUsuario);
        objTransUsuario.setTipo(objUsuario.getTipousuario());
        transUsuarioRepository.save(objTransUsuario);
		
        sesion.invalidate();
		return "index";
	}
	
	@RequestMapping(value="/nuevaContraseña", method=RequestMethod.POST)
	public String nuevaContraseña(
			HttpSession sesion, 
			@ModelAttribute("objUsuario") Usuario objUsuario,
			@RequestParam(value="repetirPassword") String repetirPassword, 
			Model model) {
		
		Usuario nuevaPassword = (Usuario) sesion.getAttribute("usuario");
		if (objUsuario.getPassword().equals(repetirPassword)) {
			//Usuario usuPassword = usuarioController.findByIdusuario();
			nuevaPassword.setPassword(objUsuario.getPassword());
			usuarioRepository.save(nuevaPassword);

			sesion.invalidate();
			return "index";
		}
		model.addAttribute("errorcontraseña", "Hubo un error al actualizar la nueva contraseña.");
		return "contraseña";
	}
	
	@RequestMapping(value="/cambiarCorreo", method=RequestMethod.POST)
	public String cambiarCorreo(
			HttpSession sesion, 
			@ModelAttribute("objUsuario") Usuario objUsuario,
			@RequestParam("correo")String correo,
			Model model) {
		
		Usuario nuevoCorreo = (Usuario) sesion.getAttribute("usuario");
		
		Usuario correoExiste = usuarioRepository.findByCorreo(correo);
		if (correoExiste!=null) {
			model.addAttribute("errorcorreo", "Hubo un error al cambiar el correo electronico, intente de nuevo.");
			return "usuario/contraseña";
        }
		
		nuevoCorreo.setCorreo(objUsuario.getCorreo());
		usuarioRepository.save(nuevoCorreo);

		Transaccion objTransaccion = new Transaccion();
        objTransaccion.setFecha(LocalDateTime.now());
        objTransaccion.setTipo("Act. de Correo electronico");
        objTransaccion.setUsuario(nuevoCorreo);
        transaccionRepository.save(objTransaccion);
        
		sesion.invalidate();
		return "index";
	}
	
	@PostMapping("/actualizarPerfil")
	public String actualizarPerfil(
			HttpSession sesion, 
			@ModelAttribute("objUsuario") Usuario objUsuario,
			Model model) {
		
		Usuario perfilActual = (Usuario) sesion.getAttribute("usuario");
	    if (objUsuario!=null) {
	    	perfilActual.setNombres(objUsuario.getNombres());
	    	//perfilActual.setApellidos(objUsuario.getApellidos());
	    	//perfilActual.setFechanacimiento(objUsuario.getFechanacimiento());
	    	perfilActual.setNacionalidad(objUsuario.getNacionalidad());
	    	perfilActual.setBiografia(objUsuario.getBiografia());
	    	usuarioRepository.save(perfilActual);
	    	
	        TipoUsuario vistaUsuario = perfilActual.getTipousuario();
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);
	    }
	    model.addAttribute("actualizado", "Su perfil se actualizó con exito");
	    model.addAttribute("objUsuario", perfilActual);
	    
	    InfoPaciente miInfo = infoPacienteRepository.findByPaciente(perfilActual);
	    model.addAttribute("miInfo", miInfo);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(perfilActual);
	    model.addAttribute("misRutinas", misRutinas);
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(perfilActual);
	    model.addAttribute("misDietas", misDietas);
	    
	    return "menu";
	}
}
