package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long>{
	List<Reunion> findByNutriologoAndEstado(Usuario nutriologo, Boolean estado);
	List<Reunion> findByDiaAndEstado(String dia, Boolean estado);
	Reunion findByCodigo(Long codigo);
	
	List<Reunion> findByNutriologo(Usuario nutriologo);
	Reunion findByNutriologoAndDiaAndEstado(Nutriologo nutriologo, String dia, Boolean estado);
}
