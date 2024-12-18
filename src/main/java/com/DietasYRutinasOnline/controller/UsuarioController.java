package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Ejercicio;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.TransaccionUsuario;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.CondicionRepository;
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
	
	@Autowired
	CondicionRepository condicionRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value="/validarUsuario", method= RequestMethod.POST)
	public String validarUsuario(
			HttpServletRequest request, 
			@RequestParam("correo")String correo, 
			@RequestParam("password")String password, 
			Model model) {
		
		//Usuario objUsuario = usuarioRepository.findByCorreoAndPassword(correo, password);
		Usuario objUsuario = usuarioRepository.findByCorreo(correo);
		if (objUsuario!=null && passwordEncoder.matches(password, objUsuario.getPassword())) {
		//if (objUsuario!=null) {
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

		try {
			Usuario correoExiste = usuarioRepository.findByCorreo(correo);
			if (correoExiste!=null) {
				model.addAttribute("error", "Hubo un error al llenar correo electronico, intente de nuevo.");
				
				List<TipoUsuario> listaTiposUsuario = tipoUsuarioRepository.findAll();
				model.addAttribute("listaTiposUsuario", listaTiposUsuario);
				
				model.addAttribute("objUsuario", objUsuario);
				return "registrar";
	        }
			String encryptedPassword = passwordEncoder.encode(objUsuario.getPassword());
		    objUsuario.setPassword(encryptedPassword);
			
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
		}
		catch(Exception ex) {
			return "registrar";
		}
		
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

		try {
			
		}
		catch(Exception ex) {
			return "cuestionario";
		}
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		if (objUsuario!=null) {
			Usuario usuRegistrado = usuarioRepository.findById(objUsuario.getIdusuario()).orElse(null);
			objCuestionario.setPaciente(usuRegistrado);
			objCuestionario.setEstado("Activo");
			objCuestionario.setFecha(LocalDateTime.now());
			infoPacienteRepository.save(objCuestionario);

			TipoUsuario vistaUsuario = tipoUsuarioRepository.findByIdtipousu(objUsuario.getTipousuario().getIdtipousu());
	        boolean esPaciente = vistaUsuario.getNomtipousu().equals("Paciente");
		    model.addAttribute("esPaciente", esPaciente);
		}

		/* RECOMENDACIÓN INICIAL */
		
		/* INFORMACION DE PACIENTE */
		//InfoPaciente miInfo = infoPacienteRepository.findByPacienteAndEstado(objUsuario, "Activo");
	    model.addAttribute("miInfo", objCuestionario);
		
		/* RUTINAS */
		List<Rutina> listaRutinas;
	    listaRutinas = rutinaRepository.findByEstado("Activo");
	    model.addAttribute("listaRutinas", listaRutinas);
	    
	    if(objCuestionario!=null && objCuestionario.getObjetivo().equals("Deficit")) {
	    	listaRutinas = rutinaRepository.findByTipo("Deficit");
	    	model.addAttribute("listaRutinas", listaRutinas);
	    	
		    if(objCuestionario.getFrecEjercicios().equals("A veces o nada") || objCuestionario.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivelAndTipo("Principiante", "Deficit");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(objCuestionario.getFrecEjercicios().equals("30 minutos a diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Deficit");
		    	model.addAttribute("listaRutinas", listaRutinas);
		    }
		    
	    }
	    
	    else if(objCuestionario!=null && objCuestionario.getObjetivo().equals("Volumen")) {
	    	listaRutinas = rutinaRepository.findByTipo("Volumen");
	    	model.addAttribute("listaRutinas", listaRutinas);
	    	
	    	if(objCuestionario.getFrecEjercicios().equals("A veces o nada") || objCuestionario.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivelAndTipo("Principiante", "Volumen");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(objCuestionario.getFrecEjercicios().equals("30 minutos diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Volumen");
		    	model.addAttribute("listaRutinas", listaRutinas);

		    }
	    }
	    
	    else if (objCuestionario!=null){
	    	if(objCuestionario.getFrecEjercicios().equals("A veces o nada") || objCuestionario.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivel("Principiante");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(objCuestionario.getFrecEjercicios().equals("30 minutos a diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Deficit");
		    	model.addAttribute("listaRutinas", listaRutinas);
		    }
	    }
		
	    /* DIETAS */
	    List<Dieta> listaDietas;
	    listaDietas = dietaRepository.findByEstado("Activo");
	    model.addAttribute("listaDietas", listaDietas);
	    
	    if(objCuestionario!=null && objCuestionario.getObjetivo().equals("Deficit")) {
	    	listaDietas = dietaRepository.findByObjetivo("Deficit");
		    model.addAttribute("listaDietas", listaDietas);
		    
		    if(objCuestionario.getCondicion().equals("Lacteos")) {
		    	Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(objCuestionario.getCondicion().equals("Gluten")) {
		    	Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionAndObjetivo(gluten, "Deficit");
		    	model.addAttribute("listaDietas", listaDietas);
		    }
		    
	    	else if(objCuestionario.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
	    
	    else if(objCuestionario!=null && objCuestionario.getObjetivo().equals("Volumen")) {
	    	listaDietas = dietaRepository.findByObjetivo("Volumen");
	    	model.addAttribute("listaDietas", listaDietas);
	    	
	    	if(objCuestionario.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	
		    	listaDietas = dietaRepository.findByCondicion(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(objCuestionario.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionNotAndObjetivo(gluten, "Volumen");
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(objCuestionario.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
	    
	    else if (objCuestionario!=null){
	    	
	    	if(objCuestionario.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(objCuestionario.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	model.addAttribute("gluten", gluten);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(gluten);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(objCuestionario.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
		
		return "recomendacion_inicial";
	}
	
	@GetMapping("/cerrarSesion")
	public String cerrarSesion(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	
		TransaccionUsuario objTransUsuario = new TransaccionUsuario();
        objTransUsuario.setLogout(LocalDateTime.now());
        objTransUsuario.setUsuario(objUsuario);
        objTransUsuario.setTipo(objUsuario.getTipousuario());
        transUsuarioRepository.save(objTransUsuario);
		
        sesion.invalidate();
		return "index";
	}
	
	@RequestMapping(value = "/nuevaContraseña", method = RequestMethod.POST)
	public String nuevaContraseña(
	        HttpSession sesion,
	        @ModelAttribute("objUsuario") Usuario objUsuario,
	        @RequestParam(value="repetirPassword") String repetirPassword,
	        Model model) {

	    Usuario nuevaPassword = (Usuario) sesion.getAttribute("usuario");
	    if (nuevaPassword!=null && objUsuario.getPassword().equals(repetirPassword)) {
	        String encryptedPassword = passwordEncoder.encode(objUsuario.getPassword());
	        nuevaPassword.setPassword(encryptedPassword);
	        usuarioRepository.save(nuevaPassword);

	        sesion.invalidate();
	        return "index";
	    }
	    model.addAttribute("errorcontraseña", "Hubo un error al actualizar la nueva contraseña.");
	    return "seguridad";
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

		TransaccionUsuario objTransUsuario = new TransaccionUsuario();
        objTransUsuario.setRegistro(LocalDateTime.now());
        objTransUsuario.setUsuario(objUsuario);
        transUsuarioRepository.save(objTransUsuario);
        
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
	    
	    InfoPaciente miInfo = infoPacienteRepository.findByPacienteAndEstado(perfilActual, "Activo");
	    model.addAttribute("miInfo", miInfo);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(perfilActual);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(perfilActual);
	    model.addAttribute("misDietas", misDietas);
	    
	    return "menu";
	}
}
