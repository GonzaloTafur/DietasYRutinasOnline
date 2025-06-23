package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>{

	List<Horario> findByPacienteAndEstado(Usuario paciente, Boolean estado);
	Horario findByPaciente(Usuario paciente);
	Horario findByPacienteAndDiaAndEstado(Usuario paciente, String dia, Boolean estado);
	Horario findByPacienteAndDiaAndParteAndEstado(Usuario paciente, String dia, String parte, Boolean estado);
	Horario findByCodigo(Long codigo);

	List<Horario> findByDiaAndEstado(String dia, Boolean estado);
}
