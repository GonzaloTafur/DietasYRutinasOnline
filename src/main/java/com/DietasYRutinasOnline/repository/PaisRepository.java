package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Pais;


@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    
}
