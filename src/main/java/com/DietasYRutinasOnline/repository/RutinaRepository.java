package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Usuario;

public interface RutinaRepository extends JpaRepository<Rutina, Long>{
	List<Rutina> findByEstado(Boolean estado);
	
	List<Rutina> findByNutriologo(Usuario nutriologo);
	Rutina findByIdrutina(Long idrutina);
	List<Rutina> findByTipo(String tipo);
	List<Rutina> findByNivel(String nivel);
	List<Rutina> findByNivelAndTipo(String nivel, String tipo);
	
	@Query("SELECT r FROM Rutina r WHERE r.nivel IN :niveles AND r.tipo = :tipo")
	List<Rutina> findByNivelesAndTipo(@Param("niveles") List<String> niveles, String tipo);
}
