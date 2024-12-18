package com.DietasYRutinasOnline.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DietasYRutinasOnline.entity.Ejercicio;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Usuario;

public interface RutinaRepository extends JpaRepository<Rutina, Integer>{
	List<Rutina> findByEstado(String estado);
	
	List<Rutina> findByNutriologo(Usuario nutriologo);
	Rutina findByIdrutina(int idrutina);
	List<Rutina> findByTipo(String tipo);
	List<Rutina> findByNivel(String nivel);
	List<Rutina> findByNivelAndTipo(String nivel, String tipo);
	
	@Query("SELECT r FROM Rutina r WHERE r.nivel IN :niveles AND r.tipo = :tipo")
	List<Rutina> findByNivelesAndTipo(@Param("niveles") List<String> niveles, String tipo);
}
