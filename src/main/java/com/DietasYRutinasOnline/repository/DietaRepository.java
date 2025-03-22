package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DietasYRutinasOnline.entity.Condicion;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Usuario;


public interface DietaRepository extends JpaRepository<Dieta, Long>{
	List<Dieta> findByEstado(Boolean estado);
	Dieta findByIddieta(Long iddieta);
	List<Dieta> findByNutriologo(Usuario nutriologo);
	List<Dieta> findByObjetivo(String objetivo);
	
	List<Dieta> findByCondicion(Condicion condicion);
	List<Dieta> findByCondicionNot(Condicion condicion);
	List<Dieta> findByCondicionAndObjetivo(Condicion condicion, String objetivo);
	List<Dieta> findByCondicionNotAndObjetivo(Condicion condicion, String objetivo);
}
