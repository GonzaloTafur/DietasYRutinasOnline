package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Usuario;


public interface DietaRepository extends JpaRepository<Dieta, Integer>{
	List<Dieta> findByEstado(String estado);
	Dieta findByIddieta(int iddieta);
	List<Dieta> findByNutriologo(Usuario nutriologo);
	List<Dieta> findByObjetivo(String objetivo);
	List<Dieta> findByAlimento(String alimento);
}
