package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.TipoUsuario;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer>{
	//List<TipoUsuario> findAll();
	//List<TipoUsuario> findByEsttipousu(String esttipousu);
	//TipoUsuario findByNomtipousu(String nomtipousu);
	TipoUsuario findByIdtipousu(int idtipousu);
	TipoUsuario findByNomtipousu(String nomtipousu);
}
