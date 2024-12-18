package com.DietasYRutinasOnline.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.TipoUsuario;
import com.DietasYRutinasOnline.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Usuario findByCorreoAndPassword(String correo, String password);
	Usuario findByCorreo(String correo);
	Usuario findByIdusuario(int idusuario);
	Usuario findByTipousuario(TipoUsuario tipousuario);

	List<Usuario> findByTipousuarioAndEstado(TipoUsuario tipousuario, String estado);
	Usuario findByEstado(String estado);
	
}
