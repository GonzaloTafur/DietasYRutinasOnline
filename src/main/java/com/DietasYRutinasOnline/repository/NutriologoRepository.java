package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Rol;

@Repository
public interface NutriologoRepository extends JpaRepository<Nutriologo, Long>{

    Nutriologo findByCorreo(String correo);

    Nutriologo findByCodigo(Long codigo);

    //@Query("SELECT id_rol FROM Usuario u INNER JOIN Nutriologo n ON u.id_usuario = n.id_usuario WHERE id_rol = 1")
    Nutriologo findByRol(Rol rol);
    
}
