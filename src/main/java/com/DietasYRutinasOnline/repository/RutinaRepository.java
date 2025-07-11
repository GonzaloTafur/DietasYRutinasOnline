package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Usuario;

public interface RutinaRepository extends JpaRepository<Rutina, Long>{
	List<Rutina> findByEstado(Boolean estado);
	
	List<Rutina> findByNutriologo(Nutriologo nutriologo);
	Rutina findByCodigo(Long codigo);
	List<Rutina> findByObjetivo(Objetivo objetivo);
	List<Rutina> findByNivel(String nivel);
	List<Rutina> findByNivelAndObjetivo(String nivel, Objetivo objetivo);
	
	@Query("SELECT r FROM Rutina r WHERE r.nivel IN :niveles AND r.objetivo = :objetivo")
	List<Rutina> findByNivelesAndObjetivo(@Param("niveles") List<String> niveles, Objetivo objetivo);
}
