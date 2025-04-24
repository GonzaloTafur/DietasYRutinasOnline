package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Asistencia;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Reunion;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long>{

	List<Asistencia> findByReunionAndEstado(Reunion reunion, Boolean estado);
	
	Asistencia findByPacienteAndReunion(Paciente paciente, Reunion reunion);
	Asistencia findByPacienteAndReunionAndEstado(Paciente paciente, Reunion reunion, Boolean estado);
	
	Asistencia findByPaciente(Paciente paciente);
	List<Asistencia> findByPacienteAndEstado(Paciente paciente, Boolean estado);
	List<Asistencia> findByPacienteAndReunionInAndEstado(Paciente paciente, List<Reunion> reuniones, Boolean estado);
	
	Long countByPacienteAndEstado(Paciente paciente, Boolean estado);
}
