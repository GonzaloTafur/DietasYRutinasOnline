package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DietasYRutinasOnline.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Usuario findByCorreoAndPassword(String correo, String password);
	Usuario findByCorreo(String correo);
	Usuario findByIdusuario(int idusuario);
}
