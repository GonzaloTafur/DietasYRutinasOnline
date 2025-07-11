package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Usuario;


public interface DietaRepository extends JpaRepository<Dieta, Long>{

	List<Dieta> findByEstado(Boolean estado);

	Dieta findByCodigo(Long codigo);

	List<Dieta> findByNutriologo(Nutriologo nutriologo);
	
	List<Dieta> findByObjetivo(Objetivo objetivo);
	
	/*List<Dieta> findByCondicion(Condicion condicion);
	List<Dieta> findByCondicionNot(Condicion condicion);
	List<Dieta> findByCondicionAndObjetivo(Condicion condicion, String objetivo);
	List<Dieta> findByCondicionNotAndObjetivo(Condicion condicion, String objetivo);*/

	//@Query("SELECT d FROM Dieta d WHERE d.alimento.condicion == 1")
	//List<Dieta> findByDietas();
}
