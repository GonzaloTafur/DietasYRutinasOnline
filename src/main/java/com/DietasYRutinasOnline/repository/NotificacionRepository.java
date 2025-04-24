package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Notificacion;
import com.DietasYRutinasOnline.entity.Transaccion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{

	//List<Notificacion> findByEstado(String estado);
	List<Notificacion> findByRol(String rol);
	List<Notificacion> findByRolAndTransaccionIn(String rol, List<Transaccion> transaccion);
	
	List<Notificacion> findByRolAndDia(String rol, String dia);
	
	//List<Notificacion> findByRolAndDiaAndUsuario(String rol, String dia, Usuario usuario);
	//List<Notificacion> findByRolAndUsuario(String rol, Usuario usuario);
}
