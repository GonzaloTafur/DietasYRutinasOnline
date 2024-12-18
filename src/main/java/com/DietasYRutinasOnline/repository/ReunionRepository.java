package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Integer>{
	List<Reunion> findByNutriologoAndEstado(Usuario nutriologo, String estado);
	List<Reunion> findByDiaAndEstado(String dia, String estado);
	Reunion findByIdreunion(int idreunion);
	
	List<Reunion> findByNutriologo(Usuario nutriologo);
	Reunion findByNutriologoAndDiaAndEstado(Usuario nutriologo, String dia, String estado);
}
