package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DietasYRutinasOnline.entity.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

}
