package com.DietasYRutinasOnline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DietasYRutinasOnline.entity.Alimento;
import com.DietasYRutinasOnline.entity.Nutriologo;
import com.DietasYRutinasOnline.entity.Objetivo;
import com.DietasYRutinasOnline.entity.Paciente;
import com.DietasYRutinasOnline.entity.Rutina;
import com.DietasYRutinasOnline.entity.Usuario;
import com.DietasYRutinasOnline.entity.ENUM.FrecEjercicios;
import com.DietasYRutinasOnline.entity.ENUM.Nivel;
import com.DietasYRutinasOnline.entity.ENUM.ObjetivoEnum;
import com.DietasYRutinasOnline.repository.NutriologoRepository;
import com.DietasYRutinasOnline.repository.ObjetivoRepository;
import com.DietasYRutinasOnline.repository.PacienteRepository;
import com.DietasYRutinasOnline.repository.RutinaRepository;

@Service
public class RutinaService {
    
    @Autowired
    RutinaRepository rutinaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    ObjetivoRepository objetivoRepository;


    public List<Rutina> getEstado(Boolean estado){
        return rutinaRepository.findAll();
    }

    /*public List<Rutina> getTipo(String tipo){
        return rutinaRepository.findAll();
    }*/

    public Rutina getCodigo(Long codigo){
        return rutinaRepository.findByCodigo(codigo);
    }

    public Rutina grabarRutina(Rutina ru){
        //e = new Ejercicio();
        ru.setEstado(true);
        return rutinaRepository.save(ru);
    }

    public Rutina actualizarRutina(Rutina ru){
        ru.setNombre(ru.getNombre());
		ru.setObjetivo(ru.getObjetivo());
		ru.setNivel(ru.getNivel());
		ru.setDescripcion(ru.getDescripcion());
		ru.setEjercicio(ru.getEjercicio());
        return rutinaRepository.save(ru);
    }

    public List<Rutina> getNutriologo(Nutriologo nutriologo) {
        return rutinaRepository.findByNutriologo(nutriologo);
    }

    public List<Rutina> filtroRutina(Paciente paciente) {

        Objetivo volumen = objetivoRepository.findByNombre("Volumen");
        Objetivo deficit = objetivoRepository.findByNombre("Deficit");

        if (pacienteRepository.findByObjetivo(volumen)!=null){ 
            return rutinaRepository.findByObjetivoAndEstado(ObjetivoEnum.V, true);
        }
        else if(pacienteRepository.findByObjetivo(deficit)!=null){
            return rutinaRepository.findByObjetivoAndEstado(ObjetivoEnum.D, true);
        }
        return rutinaRepository.findByEstado(true);
    }

    public List<Rutina> filtroNivelRutina(Paciente paciente, Nivel nivel, FrecEjercicios frecEjercicios) {

        Paciente nivel1 = pacienteRepository.findByFrecEjercicios(frecEjercicios.UNO);
        Paciente nivel2 = pacienteRepository.findByFrecEjercicios(frecEjercicios.DOS);
        Paciente nivel3 = pacienteRepository.findByFrecEjercicios(frecEjercicios.TRES);
        Paciente nivel4 = pacienteRepository.findByFrecEjercicios(frecEjercicios.CUATRO);

        if(nivel1!=null && nivel2!=null){
            return rutinaRepository.findByNivel(nivel.PRINCIPIANTE);
        }
        else if(nivel3!=null && nivel4!=null){
            return rutinaRepository.findByNivel(nivel.INTERMEDIO);
        }
        else{
            return rutinaRepository.findByEstado(true);
        }
    }

}
