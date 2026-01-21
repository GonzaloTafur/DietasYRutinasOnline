package com.DietasYRutinasOnline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import javax.annotation.processing.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.HistorialMed;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.DTO.UsuarioDTO;
import com.DietasYRutinasOnline.repository.AsistenciaRepository;
import com.DietasYRutinasOnline.repository.DietaRepository;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.HistorialMedRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.RolRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;
import com.DietasYRutinasOnline.service.DietaService;
import com.DietasYRutinasOnline.service.HistorialMedService;
import com.DietasYRutinasOnline.service.NutriologoService;
import com.DietasYRutinasOnline.service.PacienteService;
import com.DietasYRutinasOnline.service.ReunionService;
import com.DietasYRutinasOnline.service.RutinaService;
import com.DietasYRutinasOnline.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Autowired
	RutinaService rutinaService;
	
	@Autowired
	DietaService dietaService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private NutriologoService nutriologoService;

	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	HistorialMedService historialMedService;
	
	@Autowired
	TransaccionRepository transaccionRepository;

	@Autowired
	private ReunionService reunionService;

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";


	@GetMapping("/")
	public String verPerfil(HttpSession sesion, Long codigo, Model model) {
		try {
			Paciente paciente = (Paciente) sesion.getAttribute("paciente");
			Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
			//Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
			if (paciente!=null) {

				model.addAttribute("ppaciente", paciente);
				List<HistorialMed> listaSeguimiento = historialMedService.getSegumiento(paciente);
				model.addAttribute("listaSeguimiento", listaSeguimiento);
				Collections.reverse(listaSeguimiento);

				return "perfil/perfil_paciente";
			}
			//model.addAttribute("objUsuario", objUsuario);
			else if(nutriologo!=null){
				model.addAttribute("pnutriologo", nutriologo);
				//List<Reunion> objReunion = reunionRepository.findByNutriologoAndEstado(objUsuario, true);
				List<Reunion> re = reunionService.getNutriologo(nutriologo, true);
				model.addAttribute("objReunion", re);
				//model.addAttribute("dias", dias);
				
				List<Rutina> misRutinas = rutinaService.getNutriologo(nutriologo);
				model.addAttribute("misRutinas", misRutinas);
				
				List<Dieta> misDietas = dietaService.getNutriologo(nutriologo);
				model.addAttribute("misDietas", misDietas);
			
				return "perfil/perfil_nutriologo";
			}
			return "iniciar_sesion";
		} 
		catch (Exception e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return "redirect:/home/";
		}

	}
	
	/*@GetMapping("/editarPerfil")
	public String editarPerfil(HttpSession sesion, Model model) {
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");
		if (paciente!=null) {
			model.addAttribute("u", paciente);
			return "perfil/editar_perfil";
		}
		else if(nutriologo!=null){
			model.addAttribute("u", nutriologo);
			return "perfil/editar_perfil";
		}
		else {
			return "iniciar_sesion";
		}  
	}*/

	/*@PostMapping("actualizarPerfil")
	public String actualizarPerfil(
			HttpSession sesion, 
			@ModelAttribute("u") UsuarioDTO usuarioDTO,
			MultipartFile multipartFile,
			Model model) throws IOException {
		
	    UsuarioDTO usuario = (UsuarioDTO) sesion.getAttribute("usuarioDTO");
		if (usuario!=null) {
			usuario.setUsuario(usuarioDTO.getUsuario());
			usuario.setBiografia(usuarioDTO.getBiografia());
			String nombreArchivo = multipartFile.getOriginalFilename();
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, nombreArchivo);
			Files.write(fileNameAndPath, multipartFile.getBytes());
			usuario.setFoto(nombreArchivo);
				
			usuarioService.guardarUsuario(usuarioService.getCodigo(usuario.getCodigo()));

			return "redirect:/perfil/";
		}
		else{
			return "iniciar_sesion";
		}
	}*/

	@GetMapping("/editarPerfil")
	public String editarPerfil(HttpSession sesion, Model model) {

		Usuario usuario = null;

		if (sesion.getAttribute("paciente") != null) {
			usuario = (Paciente) sesion.getAttribute("paciente");
		} else if (sesion.getAttribute("nutriologo") != null) {
			usuario = (Nutriologo) sesion.getAttribute("nutriologo");
		}

		if (usuario == null) {
			return "iniciar_sesion";
		}

		UsuarioDTO dto = new UsuarioDTO();
		dto.setUsuario(usuario.getUsuario());
		dto.setBiografia(usuario.getBiografia());

		model.addAttribute("u", dto);
		return "perfil/editar_perfil";
	}


	@PostMapping("/actualizarPerfil")
	public String actualizarPerfil(
			HttpSession sesion,
			@ModelAttribute("u") UsuarioDTO dto,
			@RequestParam("foto") MultipartFile multipartFile
	) throws IOException {

		Usuario usuario = null;

		if (sesion.getAttribute("paciente") != null) {
			usuario = (Paciente) sesion.getAttribute("paciente");
		} 
		else if (sesion.getAttribute("nutriologo") != null) {
			usuario = (Nutriologo) sesion.getAttribute("nutriologo");
		}

		if (usuario==null) {
			return "iniciar_sesion";
		}

		usuario.setUsuario(dto.getUsuario());
		usuario.setBiografia(dto.getBiografia());

		if (!multipartFile.isEmpty()) {
			String nombreArchivo = multipartFile.getOriginalFilename();
			Path ruta = Paths.get(UPLOAD_DIRECTORY, nombreArchivo);
			Files.write(ruta, multipartFile.getBytes());
			usuario.setFoto(nombreArchivo);
		}

		usuarioService.guardarUsuario(usuario);
		return "redirect:/perfil/";
	}


	/* EDICIÓN DE HISTORIAL MEDICO */	
	public boolean puedeEditar(HistorialMed HistorialMed) {
	    LocalDateTime fechaHoy = LocalDateTime.now();
	    LocalDate fechaModificacion = HistorialMed.getFecha();
	    return ChronoUnit.DAYS.between(fechaModificacion, fechaHoy) >= 7;
	}

	@GetMapping("/editarMedidas")
	public String editarMedidas(HttpSession sesion, Model model) {
	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");
	    if (paciente!=null) {
			HistorialMed miHistorial = historialMedService.getEstadoAndPaciente(paciente, true);
			//HistorialMed miHistorial = historialMedService.getPaciente(paciente, true);

	        if (miHistorial!=null && puedeEditar(miHistorial)) {

	            HistorialMed nuevaInfo = new HistorialMed();
	            //nuevaInfo.setFrecEjercicios(miHistorial.getFrecEjercicios());
	            nuevaInfo.setPesoCorporal(miHistorial.getPesoCorporal());
	            nuevaInfo.setEstatura(miHistorial.getEstatura());
	            nuevaInfo.setPerimCintura(miHistorial.getPerimCintura());
	            nuevaInfo.setPerimCadera(miHistorial.getPerimCadera());
	            nuevaInfo.setPerimMuslo(miHistorial.getPerimMuslo());
	            nuevaInfo.setPerimBrazo(miHistorial.getPerimBrazo());

	            model.addAttribute("medidas", nuevaInfo);
	            return "perfil/editar_medidas";
	        } 
	        else {
	            model.addAttribute("inactivo", "Debe esperar al menos 7 días antes de poder realizar otra modificación.");
	            return "redirect:/perfil/";
	        }
	    }
	    return "redirect:/index";
	}

	@PostMapping("/actualizarMedidas")
	public String actualizarMedidas(
	        HttpSession sesion,
			@ModelAttribute("infoActiva") HistorialMed miHistorial,
	        @ModelAttribute("medidas") HistorialMed nuevaInfo,
	        Model model) {

	    Paciente paciente = (Paciente) sesion.getAttribute("paciente");
	    if (paciente!=null) {
	        //Paciente usuRegistrado = usuarioService.findById(paciente.getIdusuario()).orElse(null);
	        nuevaInfo.setPaciente(paciente);
	        nuevaInfo.setEstado(true);
	        nuevaInfo.setFecha(LocalDate.now());
	        historialMedService.guardarHistorial(nuevaInfo);

			paciente.setHistorial(nuevaInfo);
			pacienteService.guardarPaciente(paciente);

			return "redirect:/perfil/";
	    }
	    return "iniciar_sesion";
	}
	
	@GetMapping("/verSeguimiento")
	public String verSeguimiento(HttpSession sesion, Model model) {
		//Usuario objUsuario = (Usuario) sesion.getAttribute("usuario");
		Paciente paciente = (Paciente) sesion.getAttribute("paciente");
		Nutriologo nutriologo = (Nutriologo) sesion.getAttribute("nutriologo");

		if(paciente!=null){
			//List<HistorialMed> listaSeguimiento = historialMedService.getEstadoAll(true);
			List<HistorialMed> listaSeguimiento = historialMedService.getSegumiento(paciente);
			model.addAttribute("listaSeguimiento", listaSeguimiento);
			Collections.reverse(listaSeguimiento);
			
			return "usuario/seguimiento";
		}
		else if(nutriologo!=null){
			//Paciente medidasPaciente = pacienteService.getCodigo(codigo);
			List<HistorialMed> listaSeguimiento = historialMedService.getSegumiento(paciente);
			model.addAttribute("listaSeguimiento", listaSeguimiento);
			return "usuario/seguimiento";
		}
		else{
			return "iniciar_sesion";
		}
	}

}
