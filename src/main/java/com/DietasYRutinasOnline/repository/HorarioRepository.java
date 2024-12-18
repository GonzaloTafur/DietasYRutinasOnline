package com.DietasYRutinasOnline.repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.DietasYRutinasOnline.entity.Horario;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Usuario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer>{
	List<Horario> findByPacienteAndEstado(Usuario paciente, String estado);
	Horario findByPaciente(Usuario paciente);
	Horario findByPacienteAndDiaAndEstado(Usuario paciente, String dia, String estado);
	Horario findByPacienteAndDiaAndPeriodoAndEstado(Usuario paciente, String dia, String periodo, String estado);
	Horario findByIdhorario(int idhorario);

	List<Horario> findByDiaAndEstado(String dia, String estado);
}
