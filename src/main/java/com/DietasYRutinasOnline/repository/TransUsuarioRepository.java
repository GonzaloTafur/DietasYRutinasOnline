package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DietasYRutinasOnline.entity.TransaccionUsuario;

public interface TransUsuarioRepository extends JpaRepository<TransaccionUsuario, Integer> {

}
