package com.DietasYRutinasOnline.repository;

import java.util.List;

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
	//List<Rutina> findByNivel(String nivel);
	List<Rutina> findByNivelAndTipo(String nivel, String tipo);
	
	//@Query(value = "Select r from Rutina r where r.nivel = :nivel1 OR r.nivel :nivel2")
	//List<Rutina> findByNiveles(@Param("nivel1") String nivel1, @Param("nivel1") String nivel2);
	
	@Query("SELECT r FROM Rutina r WHERE r.nivel IN :niveles AND r.tipo = :tipo")
	//List<Rutina> findByNiveles(@Param("niveles") List<String> niveles);
	List<Rutina> findByNivelesAndTipo(@Param("niveles") List<String> niveles, String tipo);
	
	//Usuario findByNutriologo(Usuario nutriologo);
	//Rutina findByEjercicio(List<Ejercicio> ejercicio);
}
