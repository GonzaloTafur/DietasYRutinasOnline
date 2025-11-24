package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.ENUM.Dia;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long>{

	List<Reunion> findByNutriologoAndEstado(Nutriologo nutriologo, Boolean estado);
	
	List<Reunion> findByDiaAndEstado(Dia dia, Boolean estado);

	Reunion findByCodigo(Long codigo);
	
	List<Reunion> findByNutriologo(Nutriologo nutriologo);
	
	Reunion findByNutriologoAndDiaAndEstado(Nutriologo nutriologo, Dia dia, Boolean estado);

	List<Dia> findByDia(Dia dia);
}
