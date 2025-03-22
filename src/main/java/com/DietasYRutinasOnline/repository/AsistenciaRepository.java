package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long>{

	List<Asistencia> findByReunionAndEstado(Reunion reunion, Boolean estado);
	
	Asistencia findByPacienteAndReunion(Usuario paciente, Reunion reunion);
	Asistencia findByPacienteAndReunionAndEstado(Usuario paciente, Reunion reunion, Boolean estado);
	
	Asistencia findByPaciente(Usuario paciente);
	List<Asistencia> findByPacienteAndEstado(Usuario paciente, Boolean estado);
	List<Asistencia> findByPacienteAndReunionInAndEstado(Usuario paciente, List<Reunion> reuniones, Boolean estado);
	
	Long countByPacienteAndEstado(Usuario paciente, Boolean estado);
}
