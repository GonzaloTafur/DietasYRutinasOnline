package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{
	Rol findByCodigo(long codigo);
	Rol findByNombre(String nombre);
}
