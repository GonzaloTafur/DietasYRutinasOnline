package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.ENUM.ObjetivoEnum;


public interface DietaRepository extends JpaRepository<Dieta, Long>{

	List<Dieta> findByEstado(Boolean estado);

	Dieta findByCodigo(Long codigo);

	Dieta findByCodigoAndPaciente(Long codigo, Paciente paciente);

	List<Dieta> findByNutriologo(Nutriologo nutriologo);
	
	List<Dieta> findByObjetivo(ObjetivoEnum objetivo);

	List<Dieta> findByPaciente(Paciente paciente);
	
	/*List<Dieta> findByCondicion(Condicion condicion);
	List<Dieta> findByCondicionNot(Condicion condicion);
	List<Dieta> findByCondicionAndObjetivo(Condicion condicion, String objetivo);
	List<Dieta> findByCondicionNotAndObjetivo(Condicion condicion, String objetivo);*/

	//@Query("SELECT d FROM Dieta d WHERE d.alimento.condicion == 1")
	//List<Dieta> findByDietas();
	
}
