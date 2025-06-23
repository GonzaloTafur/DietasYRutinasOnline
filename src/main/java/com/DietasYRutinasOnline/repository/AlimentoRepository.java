package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.DietasYRutinasOnline.entity.Alimento;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long>{
	List<Alimento> findByTipo(String tipo);
	
	@Query("SELECT DISTINCT a.tipo FROM Alimento a")
	List<String> findDistinctTipos();
    //Alimento findByCodigo(Long codigo);

}
