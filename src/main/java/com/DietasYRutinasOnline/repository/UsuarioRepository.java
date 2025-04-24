package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.DietasYRutinasOnline.entity.Rol;
import com.DietasYRutinasOnline.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByCorreoAndPassword(String correo, String password);
	Usuario findByCorreo(String correo);
	Usuario findByCodigo(Long codigo);
	//Usuario findByRol(Rol rol);

	//List<Usuario> findByRolAndEstado(Rol rol, Boolean estado);
	Usuario findByEstado(Boolean estado);
	
}
