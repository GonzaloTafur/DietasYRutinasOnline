package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

	List<Transaccion> findByTipoAndReunionIn(String tipo, List<Reunion> reunion);
	List<Transaccion> findByTipoAndAsistenciaIn(String tipo, List<Asistencia> asistencia);
	
}
