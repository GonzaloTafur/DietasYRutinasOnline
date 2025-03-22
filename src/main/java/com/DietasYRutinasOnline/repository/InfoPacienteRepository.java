package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Dieta;
import com.DietasYRutinasOnline.entity.InfoPaciente;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface InfoPacienteRepository extends JpaRepository<InfoPaciente, Long>{
	InfoPaciente findByIdinfopaciente(Long idinfopaciente);
	
	List<InfoPaciente> findByPaciente(Usuario paciente);
	
	InfoPaciente findByPacienteAndEstado(Usuario paciente, Boolean estado);
	List<InfoPaciente> findByEstado(Boolean estado);
	InfoPaciente findByDietaAndPaciente(Dieta dieta, Usuario paciente);
}