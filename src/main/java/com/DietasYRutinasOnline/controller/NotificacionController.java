package com.DietasYRutinasOnline.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Notificacion;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Transaccion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.repository.HorarioRepository;
import com.DietasYRutinasOnline.repository.InfoPacienteRepository;
import com.DietasYRutinasOnline.repository.NotificacionRepository;
import com.DietasYRutinasOnline.repository.ReunionRepository;
import com.DietasYRutinasOnline.repository.TipoUsuarioRepository;
import com.DietasYRutinasOnline.repository.TransaccionRepository;
import com.DietasYRutinasOnline.repository.UsuarioRepository;


@Controller
@RequestMapping("/notificacion")
public class NotificacionController {
	
	@Autowired
	NotificacionRepository notificacionRepository;
	
	@Autowired
	TransaccionRepository transaccionRepository;
	
	@Autowired
	HorarioRepository horarioRepository;
	
	@Autowired
	ReunionRepository reunionRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	InfoPacienteRepository infoPacienteRepository;
	
	
	private final List<String> mensajesAleatorios = Arrays.asList(
	        "¡Empieza un nuevo día! No olvides revisar tu horario y las rutinas programadas",
	        "¿Ocupado y full esta semana? nunca esta demás hacer un par de ejercicios para ganar energías",
	        "¿Que piensas comer hoy? Recuerda que puedes decidir dentro de nuestro listado de dietas, que filtra según tu información de paciente",
	        "¡Recuerda hacer ejercicio y beber mucha agua!",
	        "¡Una mente sana comienza con un cuerpo sano!",
	        "No olvides siempre hacer una serie de estiramientos físicos aún si no estás haciendo ejercicios."
	    );
	
	private final Random random = new Random();
	
	@Scheduled(cron = "0 0 0 * * SUN")
	public void notisAutomaticas() {
		
		String mensaje = mensajesAleatorios.get(random.nextInt(mensajesAleatorios.size()));
		
		Notificacion objNotificacion = new Notificacion();
		objNotificacion.setMensaje(mensaje);
		objNotificacion.setRol("Paciente");
		objNotificacion.setTimestamp(LocalDateTime.now());
        objNotificacion.setEstado("Activo");
        notificacionRepository.save(objNotificacion);
        
        System.out.println("Nueva notificación creada: Todo funciona con exito :)");
    }
	
	@Scheduled(cron = "0 0 7 * * ?")
	public void notisRutinas() {
	    DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
	    String diaSemana = diaActual.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

	    Notificacion notificacion = new Notificacion();
	    notificacion.setRol("Paciente");
	    notificacion.setMensaje("¡Hoy tienes una actividad programada! Ve a tu horario de Rutinas");
	    notificacion.setDia(diaSemana.toUpperCase());
	    notificacion.setTimestamp(LocalDateTime.now());
	    notificacion.setEstado("Activo");

	    notificacionRepository.save(notificacion);
	    System.out.println("Notificación automática generada para el día " + diaSemana);
	}

	//@Scheduled(cron = "*/40 * * * * *")
	@Scheduled(cron = "0 0 8 * * ?")
	public void notisReunion() {
	    DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
	    String diaSemana = diaActual.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
	    
	    Transaccion objTransaccion = new Transaccion();
    	objTransaccion.setFecha(LocalDateTime.now());
    	objTransaccion.setTipo("Recordatiorio de reunión");
    	transaccionRepository.save(objTransaccion);

	    Notificacion notificacion = new Notificacion();
	    notificacion.setTransaccion(objTransaccion);
	    notificacion.setRol("Paciente");
	    notificacion.setMensaje("Hoy tienes una cita programada.");
	    notificacion.setDia(diaSemana.toUpperCase());
	    notificacion.setTimestamp(LocalDateTime.now());
	    notificacion.setEstado("Activo");

	    notificacionRepository.save(notificacion);
	    System.out.println("Notificación automática generada para el día " + diaSemana);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void notisActualizarMedidas() {
        List<InfoPaciente> registrosActivos = infoPacienteRepository.findByEstado("Activo");

        for (InfoPaciente info : registrosActivos) {
            LocalDateTime fechaActual = LocalDateTime.now();
            LocalDateTime fechaModificacion = info.getFecha();

            if (ChronoUnit.DAYS.between(fechaModificacion, fechaActual) >= 7) {
                // Crear la notificación
                Notificacion notificacion = new Notificacion();
                //notificacion.setTransaccion(info.getIdinfopaciente());
                notificacion.setRol("Paciente");
                notificacion.setMensaje("Han pasado 7 días desde tu última edición, aprovecha en editarlas si lo necesitas.");
                notificacion.setEstado("Activo");
                notificacion.setDia(fechaActual.getDayOfWeek().name().toUpperCase());
                notificacion.setTimestamp(fechaActual);

                notificacionRepository.save(notificacion);
                System.out.println("Notificación automática generada para el día " + fechaActual);
            }
        }
    }
	
	/*@Scheduled(cron = "0 0 7 * * ?") // Ejemplo: Cada día a las 7:00 AM
	public void generarNotificaciones() {
	    DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
	    String diaSemana = diaActual.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

	    // Buscar actividades activas del día
	    //List<Horario> actividadesHoy = horarioRepository.findByDiaAndEstado(diaSemana, "Activo");
	    
	    Notificacion notificacion = new Notificacion();
        notificacion.setRol("Paciente");
        notificacion.setMensaje("¡Hoy tienes rutinas programadas!");
        notificacion.setDia(diaSemana);
        notificacion.setTimestamp(LocalDateTime.now());
        notificacion.setEstado("Activo");
        notificacionRepository.save(notificacion);
        
	    System.out.println("Notificaciones generadas para el día " + diaSemana);
	}*/
}
