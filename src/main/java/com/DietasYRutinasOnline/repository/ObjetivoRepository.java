package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Objetivo;


@Repository
public interface ObjetivoRepository extends JpaRepository<Objetivo, Long>{
    
    Objetivo findByCodigo(Long codigo);

    Objetivo findByNombre(String nombre);

}
