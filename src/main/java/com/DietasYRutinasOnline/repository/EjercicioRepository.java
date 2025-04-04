package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Ejercicio;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long>{
	List<Ejercicio> findByGrupomuscular(String grupomuscular);

	@Query("SELECT DISTINCT e.grupomuscular FROM Ejercicio e")
	List<String> findDistinctGrupomuscular();
}
