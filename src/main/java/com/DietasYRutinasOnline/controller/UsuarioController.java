package com.DietasYRutinasOnline.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.TransaccionUsuario;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.CondicionRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransUsuarioRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/usuario/")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	RutinaRepository rutinaRepository;
	
	@Autowired
	DietaRepository dietaRepository;
	
	@Autowired
	HistorialMedRepository historialMedRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	@Autowired
	TransUsuarioRepository transUsuarioRepository;
	
	@Autowired
	CondicionRepository condicionRepository;

	@Autowired
	public UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@RequestMapping(value="/validarUsuario", method= RequestMethod.POST)
	public String validarUsuario(
			HttpServletRequest request, 
			@RequestParam("correo")String correo, 
			@RequestParam("password")String password, 
			Model model) {
		
		//Usuario objUsuario = usuarioRepository.findByCorreoAndPassword(correo, password);
		Nutriologo nutriologo = usuarioService.getCorreoNutriologos(correo);
		Paciente paciente = usuarioService.getCorreoPaciente(correo);
		if (nutriologo!=null && passwordEncoder.matches(password, nutriologo.getPassword())) {
		//if (objUsuario!=null) {
			HttpSession sesion = request.getSession();
			sesion.setAttribute("nutriologo", nutriologo);
			
			//Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        
	        TransaccionUsuario objTransUsuario = new TransaccionUsuario();
	        objTransUsuario.setLogin(LocalDateTime.now());
	        //objTransUsuario.setUsuario(objUsuario);
	        //objTransUsuario.setRol(vistaUsuario);
            transUsuarioRepository.save(objTransUsuario);
	        
			return "menu";
		}
		else if (paciente!=null && passwordEncoder.matches(password, paciente.getPassword())) {
			//if (objUsuario!=null) {
				HttpSession sesion = request.getSession();
				sesion.setAttribute("paciente", paciente);
				
				//Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
				/*if(vistaUsuario!=null && vistaUsuario.getNombre().equals("Paciente")) {
					
					TransaccionUsuario objTransUsuario = new TransaccionUsuario();
					objTransUsuario.setLogin(LocalDateTime.now());
					//objTransUsuario.setUsuario(objUsuario);
					//objTransUsuario.setRol(vistaUsuario);
					transUsuarioRepository.save(objTransUsuario);
					
					return "menu";
				}*/
				
				TransaccionUsuario objTransUsuario = new TransaccionUsuario();
				objTransUsuario.setLogin(LocalDateTime.now());
				//objTransUsuario.setUsuario(objUsuario);
				//objTransUsuario.setRol(vistaUsuario);
				transUsuarioRepository.save(objTransUsuario);
				
				return "menu";
			}
		model.addAttribute("error", "El correo electronico y la contraseña no coinciden. Intenta de nuevo.");
		return "index";
	}
	
	@GetMapping("registrar_usuario")
	public String registraCuenta(HttpServletRequest request, Model model) {
		//List<Rol> listaTiposUsuario = rolRepository.findAll();
		//model.addAttribute("listaTiposUsuario", listaTiposUsuario);
		//List<String> listaPaises = nacionalidadService.obtenerTodosLosPaises();
        //model.addAttribute("listaPaises", listaPaises);
		return "registrar_como";
	}


	@GetMapping("registrar_usuario_nutriologo")
	public String registraCuentaNutri(HttpServletRequest request, Model model) {
		//List<Rol> listaTiposUsuario = rolRepository.findAll();
		//model.addAttribute("listaTiposUsuario", listaTiposUsuario);
		
		Nutriologo nutriologo = new Nutriologo();
		model.addAttribute("nutriologo", nutriologo);
		
		//List<String> listaPaises = nacionalidadService.obtenerTodosLosPaises();
        //model.addAttribute("listaPaises", listaPaises);
		return "registrar";
	}

	@GetMapping("registrar_usuario_paciente")
	public String registraCuentaPac(HttpServletRequest request, Model model) {
		//List<Rol> listaTiposUsuario = rolRepository.findAll();
		//model.addAttribute("listaTiposUsuario", listaTiposUsuario);
		
		Paciente paciente = new Paciente();
		model.addAttribute("paciente", paciente);
		
		//List<String> listaPaises = nacionalidadService.obtenerTodosLosPaises();
        //model.addAttribute("listaPaises", listaPaises);
		return "registrar";
	}
	
	// ---- VENTANA REGISTRAR -------------------------------------------------------------------
	@PostMapping("grabar_usuario")
    public String grabarUsuario(
    		HttpServletRequest request,
    		@RequestParam("correo") String correo,
    		@ModelAttribute("objUsuario") Usuario objUsuario,
    		//@ModelAttribute("nombre") String nombre,
    		Model model) {

		try {
			Usuario correoExiste = usuarioRepository.findByCorreo(correo);
			if (correoExiste!=null) {
				model.addAttribute("error", "Hubo un error al llenar correo electronico, intente de nuevo.");
				
				model.addAttribute("objUsuario", objUsuario);
				return "registrar";
	        }
			String encryptedPassword = passwordEncoder.encode(objUsuario.getPassword());
		    objUsuario.setPassword(encryptedPassword);
			
			objUsuario.setEstado(true);
	        usuarioRepository.save(objUsuario);
	        
	        /*Transaccion objTransaccion = new Transaccion();
	        objTransaccion.setFecha(LocalDateTime.now());
	        objTransaccion.setTipo("CREACIÓN");
	        objTransaccion.setUsuario(objUsuario);
	        transaccionRepository.save(objTransaccion);*/
	        
	        HttpSession sesion = request.getSession();
			sesion.setAttribute("usuario", objUsuario);
			
			/*Rol vistaUsuario = rolRepository.findByCodigo(objUsuario.getRol().getCodigo());
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);*/
	        
	        //if(esPaciente) {
	        	HistorialMed cuestionario = new HistorialMed();
	            model.addAttribute("cuestionario", cuestionario);
	            //model.addAttribute("usuRegistrado", objUsuario.getIdusuario());
	            
	            TransaccionUsuario objTransUsuario = new TransaccionUsuario();
	            //objTransUsuario.setRegistro(LocalDateTime.now());
	            objTransUsuario.setUsuario(objUsuario);
	            //objTransUsuario.setRol(vistaUsuario);
	            transUsuarioRepository.save(objTransUsuario);
	            
	            return "cuestionario";
	        //}
	        
	        /*TransaccionUsuario objTransUsuario = new TransaccionUsuario();
	        objTransUsuario.setRegistro(LocalDateTime.now());
	        objTransUsuario.setUsuario(objUsuario);
	        objTransUsuario.setRol(vistaUsuario);
	        transUsuarioRepository.save(objTransUsuario);*/

		}
		catch(Exception ex) {
			return "registrar";
		}
		
    }


	@PostMapping("grabar_paciente")
    public String grabarPaciente(
    		HttpServletRequest request,
    		@RequestParam("correo") String correo,
    		@ModelAttribute("paciente") Paciente paciente,
    		@ModelAttribute("nomrol") String nomrol,
    		Model model) {

		try {
			Usuario correoExiste = usuarioRepository.findByCorreo(correo);
			if (correoExiste!=null) {
				model.addAttribute("error", "Hubo un error al llenar correo electronico, intente de nuevo.");
				
				model.addAttribute("paciente", paciente);
				return "registrar";
	        }
			String encryptedPassword = passwordEncoder.encode(paciente.getPassword());
		    paciente.setPassword(encryptedPassword);
			
			paciente.setEstado(true);
	        usuarioService.guardarPaciente(paciente);
	        
	        /*Transaccion objTransaccion = new Transaccion();
	        objTransaccion.setFecha(LocalDateTime.now());
	        objTransaccion.setTipo("CREACIÓN");
	        objTransaccion.setUsuario(objUsuario);
	        transaccionRepository.save(objTransaccion);*/
	        
	        HttpSession sesion = request.getSession();
			sesion.setAttribute("paciente", paciente);
			
			//Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
	        //boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        //model.addAttribute("esPaciente", esPaciente);
	        
	       
	        HistorialMed cuestionario = new HistorialMed();
	        model.addAttribute("cuestionario", cuestionario);
	        //model.addAttribute("usuRegistrado", objUsuario.getIdusuario());
	            
	        TransaccionUsuario objTransUsuario = new TransaccionUsuario();
	        objTransUsuario.setRegistro(LocalDateTime.now());
	        //objTransUsuario.setUsuario(objUsuario);
	        //objTransUsuario.setRol(vistaUsuario);
	        transUsuarioRepository.save(objTransUsuario);
	            
	         return "cuestionario";
	    }
	        
		catch(Exception ex) {
			return "registrar";
		}
		
    }
	
	// ---- VENTANA CUESTIONARIO -------------------------------------------------------------
	//@RequestMapping(value="/grabarCuestionario", method=RequestMethod.POST)
	@PostMapping("grabarCuestionario")
	public String grabarCuestionario(
			HttpSession sesion,
	        @ModelAttribute("cuestionario") HistorialMed cuestionario,
	        //@RequestParam("idusuario") int idusuario,
	        //@ModelAttribute("usuRegistrado") int idusuario,
	        Model model) {

		try {
			Paciente paciente = (Paciente) sesion.getAttribute("paciente");
			if (paciente!=null) {
				//Paciente usuRegistrado = pacienteRepository.findById(paciente.getIdusuario()).orElse(null);
				//cuestionario.setPaciente(usuRegistrado);
				cuestionario.setEstado(true);
				cuestionario.setFecha(LocalDateTime.now());
				historialMedRepository.save(cuestionario);

				paciente.setHistorialMedico(cuestionario);
				usuarioService.guardarPaciente(paciente);
				//Rol vistaUsuario = rolRepository.findByIdrol(objUsuario.getRol().getIdrol());
				//boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
				//model.addAttribute("esPaciente", esPaciente);
			}

		}
		catch(Exception ex) {
			return "cuestionario";
		}
		
		/* RECOMENDACIÓN INICIAL */
		
		/* INFORMACION DE PACIENTE */
		//HistorialMed miInfo = HistorialMedRepository.findByPacienteAndEstado(paciente, true);
	    //model.addAttribute("miInfo", cuestionario);
		
		/* RUTINAS */
		/*List<Rutina> listaRutinas;
	    listaRutinas = rutinaRepository.findByEstado(true);
	    model.addAttribute("listaRutinas", listaRutinas);
	    
	    if(cuestionario!=null && cuestionario.getObjetivo().equals("Deficit")) {
	    	listaRutinas = rutinaRepository.findByTipo("Deficit");
	    	model.addAttribute("listaRutinas", listaRutinas);
	    	
		    if(cuestionario.getFrecEjercicios().equals("A veces o nada") || cuestionario.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivelAndTipo("Principiante", "Deficit");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(cuestionario.getFrecEjercicios().equals("30 minutos a diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Deficit");
		    	model.addAttribute("listaRutinas", listaRutinas);
		    }
		    
	    }
	    
	    else if(cuestionario!=null && cuestionario.getObjetivo().equals("Volumen")) {
	    	listaRutinas = rutinaRepository.findByTipo("Volumen");
	    	model.addAttribute("listaRutinas", listaRutinas);
	    	
	    	if(cuestionario.getFrecEjercicios().equals("A veces o nada") || cuestionario.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivelAndTipo("Principiante", "Volumen");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(cuestionario.getFrecEjercicios().equals("30 minutos diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Volumen");
		    	model.addAttribute("listaRutinas", listaRutinas);

		    }
	    }
	    
	    else if (cuestionario!=null){
	    	if(cuestionario.getFrecEjercicios().equals("A veces o nada") || cuestionario.getFrecEjercicios().equals("Semanalmente")) {
		    	
		    	listaRutinas = rutinaRepository.findByNivel("Principiante");
			    model.addAttribute("listaRutinas", listaRutinas);
		    }
		    else if(cuestionario.getFrecEjercicios().equals("30 minutos a diario")) {
		    	
		    	List<String> niveles = Arrays.asList("Principiante", "Intermedio");
		    	listaRutinas = rutinaRepository.findByNivelesAndTipo(niveles, "Deficit");
		    	model.addAttribute("listaRutinas", listaRutinas);
		    }
	    }*/
		
	    /* DIETAS */
	    /*List<Dieta> listaDietas;
	    listaDietas = dietaRepository.findByEstado(true);
	    model.addAttribute("listaDietas", listaDietas);
	    
	    if(cuestionario!=null && cuestionario.getObjetivo().equals("Deficit")) {
	    	listaDietas = dietaRepository.findByObjetivo("Deficit");
		    model.addAttribute("listaDietas", listaDietas);
		    
		    if(cuestionario.getCondicion().equals("Lacteos")) {
		    	Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(cuestionario.getCondicion().equals("Gluten")) {
		    	Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionAndObjetivo(gluten, "Deficit");
		    	model.addAttribute("listaDietas", listaDietas);
		    }
		    
	    	else if(cuestionario.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
	    
	    else if(cuestionario!=null && cuestionario.getObjetivo().equals("Volumen")) {
	    	listaDietas = dietaRepository.findByObjetivo("Volumen");
	    	model.addAttribute("listaDietas", listaDietas);
	    	
	    	if(cuestionario.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	
		    	listaDietas = dietaRepository.findByCondicion(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(cuestionario.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	
		    	listaDietas = dietaRepository.findByCondicionNotAndObjetivo(gluten, "Volumen");
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(cuestionario.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }
	    
	    else if (cuestionario!=null){
	    	
	    	if(cuestionario.getCondicion().equals("Lacteos")) {
	    		Condicion lacteos = condicionRepository.findByNombre("Lacteos");
		    	model.addAttribute("lacteos", lacteos);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(lacteos);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(cuestionario.getCondicion().equals("Gluten")) {
	    		Condicion gluten = condicionRepository.findByNombre("Gluten");
		    	model.addAttribute("gluten", gluten);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(gluten);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    	
	    	else if(cuestionario.getCondicion().equals("Vegano")) {
	    		Condicion carne = condicionRepository.findByNombre("Carne");
		    	model.addAttribute("carne", carne);
		    	
		    	listaDietas = dietaRepository.findByCondicionNot(carne);
		    	model.addAttribute("listaDietas", listaDietas);
		    }
	    }*/
		
		return "recomendacion_inicial";
	}
	
	@GetMapping("cerrarSesion")
	public String cerrarSesion(HttpSession sesion, Model model) {
		Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
	
		TransaccionUsuario objTransUsuario = new TransaccionUsuario();
        objTransUsuario.setLogout(LocalDateTime.now());
        objTransUsuario.setUsuario(objUsuario);
        //objTransUsuario.setRol(objUsuario.getRol());
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
	
	@PostMapping("actualizarPerfil")
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
	    	//perfilActual.setBiografia(objUsuario.getBiografia());
	    	usuarioRepository.save(perfilActual);
	    	
	        /*Rol vistaUsuario = perfilActual.getRol();
	        boolean esPaciente = vistaUsuario.getNombre().equals("Paciente");
	        model.addAttribute("esPaciente", esPaciente);*/
	    }
	    model.addAttribute("actualizado", "Su perfil se actualizó con exito");
	    model.addAttribute("objUsuario", perfilActual);
	    
	    //HistorialMed miInfo = historialMedRepository.findByPacienteAndEstado(perfilActual, true);
	    //model.addAttribute("miInfo", miInfo);
	    
	    List<Rutina> misRutinas = rutinaRepository.findByNutriologo(perfilActual);
	    model.addAttribute("misRutinas", misRutinas);
	    
	    List<Dieta> misDietas = dietaRepository.findByNutriologo(perfilActual);
	    model.addAttribute("misDietas", misDietas);
	    
	    return "menu";
	}
}
