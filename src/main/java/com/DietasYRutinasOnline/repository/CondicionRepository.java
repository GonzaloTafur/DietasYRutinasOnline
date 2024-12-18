package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Condicion;

@Repository
public interface CondicionRepository extends JpaRepository<Condicion, Integer>{
	List<Condicion> findByEstado(String estado);
	Condicion findByNombre(String nombre);
}
