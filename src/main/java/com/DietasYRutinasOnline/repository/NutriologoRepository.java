package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Nutriologo;

@Repository
public interface NutriologoRepository extends JpaRepository<Nutriologo, Long>{

    Nutriologo findByCorreo(String correo);
    
}
