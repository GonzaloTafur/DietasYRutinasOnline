package com.DietasYRutinasOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface InfoPacienteRepository extends JpaRepository<InfoPaciente, Integer>{
	InfoPaciente findByIdinfopaciente(int idinfopaciente);
	InfoPaciente findByPaciente(Usuario paciente);
	InfoPaciente findByDietaAndPaciente(Dieta dieta, Usuario paciente);
	//void merge(Cuestionario objCuestionario);
}