package com.DietasYRutinasOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.ENUM.Dia;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>{

	List<Horario> findByPacienteAndEstado(Paciente paciente, Boolean estado);
	Horario findByPaciente(Paciente paciente);
	Horario findByPacienteAndDiaAndEstado(Paciente paciente, Dia dia, Boolean estado);
	Horario findByPacienteAndDiaAndParteAndEstado(Paciente paciente, Dia dia, String parte, Boolean estado);
	Horario findByCodigo(Long codigo);

	List<Horario> findByDiaAndEstado(String dia, Boolean estado);
}
