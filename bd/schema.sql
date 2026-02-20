-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bddietasyrutinas_copia
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bddietasyrutinas_copia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bddietasyrutinas_copia` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bddietasyrutinas_copia` ;

-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Alimento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Alimento` (
  `id_alimento` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(40) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `nutrientes` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `tipo` VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `descripcion` TEXT NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_alimento`))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Pais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Pais` (
  `id_pais` SMALLINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NOT NULL,
  `prefijo` VARCHAR(10) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY (`id_pais`),
  UNIQUE INDEX `nombre` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(80) NULL DEFAULT NULL,
  `apellidos` VARCHAR(80) NULL DEFAULT NULL,
  `fecha_nacimiento` DATE NOT NULL,
  `nacionalidad` VARCHAR(40) NULL DEFAULT NULL,
  `id_pais` SMALLINT NULL DEFAULT NULL,
  `sexo` ENUM('Masculino', 'Femenino') CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `biografia` TEXT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  `foto` VARBINARY(255) NULL DEFAULT NULL,
  `usuario` VARCHAR(80) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `correo` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `password` VARCHAR(225) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `correo` (`correo` ASC) VISIBLE,
  INDEX `id_pais` (`id_pais` ASC) VISIBLE,
  CONSTRAINT `FK_Usuario_Pais`
    FOREIGN KEY (`id_pais`)
    REFERENCES `bddietasyrutinas_copia`.`Pais` (`id_pais`))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Nutriologo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Nutriologo` (
  `id_usuario` INT NOT NULL,
  `cargo` VARCHAR(255) NULL DEFAULT NULL,
  `CV` VARBINARY(255) NULL DEFAULT NULL,
  `rol` ENUM('SU', 'E') CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL DEFAULT 'E',
  INDEX `id_usuario` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `Nutriologo_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Reunion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Reunion` (
  `id_reunion` INT NOT NULL AUTO_INCREMENT,
  `id_nutriologo` INT NULL DEFAULT NULL,
  `motivo` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `dia_semana` ENUM('LU', 'MA', 'MI', 'JU', 'VI', 'SA', 'DO') CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `hora_inicio` TIME NOT NULL,
  `enlace` VARCHAR(255) NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_reunion`),
  INDEX `FK3malr2wpwncees3sempncxt2e` (`id_nutriologo` ASC) VISIBLE,
  CONSTRAINT `FK3malr2wpwncees3sempncxt2e`
    FOREIGN KEY (`id_nutriologo`)
    REFERENCES `bddietasyrutinas_copia`.`Nutriologo` (`id_usuario`),
  CONSTRAINT `Reunion_ibfk_1`
    FOREIGN KEY (`id_nutriologo`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Condicion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Condicion` (
  `id_condicion` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_condicion`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Objetivo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Objetivo` (
  `id_objetivo` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `descripcion` TEXT NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_objetivo`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`HistorialMedico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`HistorialMedico` (
  `id_historial` INT NOT NULL AUTO_INCREMENT,
  `id_paciente` INT NULL DEFAULT NULL,
  `peso_corporal` DECIMAL(4,2) NULL DEFAULT NULL,
  `estatura` DECIMAL(4,2) NULL DEFAULT NULL,
  `perimetro_cintura` DECIMAL(4,2) NULL DEFAULT NULL,
  `perimetro_cadera` DECIMAL(4,2) NULL DEFAULT NULL,
  `perimetro_muslo` DECIMAL(4,2) NULL DEFAULT NULL,
  `perimetro_brazo` DECIMAL(4,2) NULL DEFAULT NULL,
  `estado` TINYINT(1) NULL DEFAULT NULL,
  `fecha_modif` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id_historial`),
  INDEX `id_paciente` (`id_paciente` ASC) VISIBLE,
  CONSTRAINT `HistorialMedico_ibfk_1`
    FOREIGN KEY (`id_paciente`)
    REFERENCES `bddietasyrutinas_copia`.`Paciente` (`id_usuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Paciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Paciente` (
  `id_usuario` INT NOT NULL,
  `frec_ejercicios` ENUM('UNO', 'DOS', 'TRES', 'CUATRO', 'CINCO') CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  `id_condicion` INT NULL DEFAULT NULL,
  `id_objetivo` INT NULL DEFAULT NULL,
  `id_historial` INT NULL DEFAULT NULL,
  INDEX `id_usuario` (`id_usuario` ASC) VISIBLE,
  INDEX `id_objetivo` (`id_objetivo` ASC) VISIBLE,
  INDEX `id_condicion` (`id_condicion` ASC) VISIBLE,
  INDEX `id_historial` (`id_historial` ASC) VISIBLE,
  CONSTRAINT `FK_Paciente_Condicion`
    FOREIGN KEY (`id_condicion`)
    REFERENCES `bddietasyrutinas_copia`.`Condicion` (`id_condicion`),
  CONSTRAINT `FK_Paciente_Objetivo`
    FOREIGN KEY (`id_objetivo`)
    REFERENCES `bddietasyrutinas_copia`.`Objetivo` (`id_objetivo`),
  CONSTRAINT `FK_PacienteHistorial`
    FOREIGN KEY (`id_historial`)
    REFERENCES `bddietasyrutinas_copia`.`HistorialMedico` (`id_historial`),
  CONSTRAINT `Paciente_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Asistencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Asistencia` (
  `id_asistencia` INT NOT NULL AUTO_INCREMENT,
  `id_reunion` INT NOT NULL,
  `id_paciente` INT NOT NULL,
  `fecha` DATETIME(6) NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_asistencia`),
  INDEX `id_reunion` (`id_reunion` ASC) VISIBLE,
  INDEX `FK7w7pmji8buve1sobcc5hqemuw` (`id_paciente` ASC) VISIBLE,
  CONSTRAINT `Asistencia_ibfk_1`
    FOREIGN KEY (`id_paciente`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`),
  CONSTRAINT `Asistencia_ibfk_2`
    FOREIGN KEY (`id_reunion`)
    REFERENCES `bddietasyrutinas_copia`.`Reunion` (`id_reunion`),
  CONSTRAINT `FK7w7pmji8buve1sobcc5hqemuw`
    FOREIGN KEY (`id_paciente`)
    REFERENCES `bddietasyrutinas_copia`.`Paciente` (`id_usuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Dieta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Dieta` (
  `id_dieta` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `descripcion` TEXT NULL DEFAULT NULL,
  `objetivo` ENUM('V', 'D') NULL DEFAULT NULL,
  `id_nutriologo` INT NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_dieta`),
  INDEX `FK20stwmvdh5g7hdvvwvcv7kc1a` (`id_nutriologo` ASC) VISIBLE,
  INDEX `id_objetivo` USING BTREE (`objetivo`) VISIBLE,
  CONSTRAINT `Dieta_ibfk_2`
    FOREIGN KEY (`id_nutriologo`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`),
  CONSTRAINT `FK20stwmvdh5g7hdvvwvcv7kc1a`
    FOREIGN KEY (`id_nutriologo`)
    REFERENCES `bddietasyrutinas_copia`.`Nutriologo` (`id_usuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = 'V: VOLUMEN\\r\\nD: DEFICIT';


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`DietaAlimento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`DietaAlimento` (
  `id_dieta` INT NULL DEFAULT NULL,
  `id_alimento` INT NULL DEFAULT NULL,
  INDEX `id_dieta` (`id_dieta` ASC) VISIBLE,
  INDEX `id_alimento` (`id_alimento` ASC) VISIBLE,
  CONSTRAINT `DietaAlimento_ibfk_1`
    FOREIGN KEY (`id_dieta`)
    REFERENCES `bddietasyrutinas_copia`.`Dieta` (`id_dieta`),
  CONSTRAINT `DietaAlimento_ibfk_2`
    FOREIGN KEY (`id_alimento`)
    REFERENCES `bddietasyrutinas_copia`.`Alimento` (`id_alimento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`DietaCondicion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`DietaCondicion` (
  `id_dieta` INT NULL DEFAULT NULL,
  `id_condicion` INT NULL DEFAULT NULL,
  INDEX `id_dieta` (`id_dieta` ASC) VISIBLE,
  INDEX `id_condicion` (`id_condicion` ASC) VISIBLE,
  CONSTRAINT `DietaCondicion_ibfk_1`
    FOREIGN KEY (`id_dieta`)
    REFERENCES `bddietasyrutinas_copia`.`Dieta` (`id_dieta`),
  CONSTRAINT `DietaCondicion_ibfk_2`
    FOREIGN KEY (`id_condicion`)
    REFERENCES `bddietasyrutinas_copia`.`Condicion` (`id_condicion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`DietaPaciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`DietaPaciente` (
  `id_usuario` INT NOT NULL,
  `id_dieta` INT NOT NULL,
  INDEX `id_dieta` (`id_dieta` ASC) VISIBLE,
  INDEX `id_usuario` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `FK_DietaPaciente_Dieta`
    FOREIGN KEY (`id_dieta`)
    REFERENCES `bddietasyrutinas_copia`.`Dieta` (`id_dieta`),
  CONSTRAINT `FK_DietaPaciente_Paciente`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `bddietasyrutinas_copia`.`Paciente` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Ejercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Ejercicio` (
  `id_ejercicio` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `grupo_muscular` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `tipo` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  `series` INT NOT NULL,
  `repeticiones` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  `descripcion` TEXT NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_ejercicio`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`HistorialDieta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`HistorialDieta` (
  `id_historial` INT NULL DEFAULT NULL,
  `id_dieta` INT NULL DEFAULT NULL,
  `id_histotial` BIGINT NOT NULL,
  INDEX `id_historial` (`id_historial` ASC) VISIBLE,
  INDEX `id_dieta` (`id_dieta` ASC) VISIBLE,
  CONSTRAINT `HistorialDieta_ibfk_1`
    FOREIGN KEY (`id_historial`)
    REFERENCES `bddietasyrutinas_copia`.`HistorialMedico` (`id_historial`),
  CONSTRAINT `HistorialDieta_ibfk_2`
    FOREIGN KEY (`id_dieta`)
    REFERENCES `bddietasyrutinas_copia`.`Dieta` (`id_dieta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Rutina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Rutina` (
  `id_rutina` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `descripcion` TEXT NULL DEFAULT NULL,
  `objetivo` ENUM('V', 'D') CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `id_nutriologo` INT NULL DEFAULT NULL,
  `nivel` ENUM('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO') CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `parte_cuerpo` VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_rutina`),
  INDEX `FKr0u16fjcbcamwrbx2jrf9h3hv` (`id_nutriologo` ASC) VISIBLE,
  CONSTRAINT `FKr0u16fjcbcamwrbx2jrf9h3hv`
    FOREIGN KEY (`id_nutriologo`)
    REFERENCES `bddietasyrutinas_copia`.`Nutriologo` (`id_usuario`),
  CONSTRAINT `Rutina_ibfk_2`
    FOREIGN KEY (`id_nutriologo`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci
COMMENT = 'V: VOLUMEN\\r\\nD: DEFICIT';


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Horario` (
  `id_horario` INT NOT NULL AUTO_INCREMENT,
  `id_paciente` INT NOT NULL,
  `id_rutina` INT NOT NULL,
  `dia_semana` ENUM('LU', 'MA', 'MI', 'JU', 'VI', 'SA', 'DO') CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `parte_dia` VARCHAR(20) NULL DEFAULT NULL,
  `descanso_serie` VARCHAR(20) NULL DEFAULT NULL,
  `descanso_ejercicio` VARCHAR(20) NULL DEFAULT NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_horario`),
  INDEX `id_paciente` (`id_paciente` ASC) VISIBLE,
  INDEX `id_rutina` (`id_rutina` ASC) VISIBLE,
  CONSTRAINT `Horario_ibfk_1`
    FOREIGN KEY (`id_paciente`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`),
  CONSTRAINT `Horario_ibfk_2`
    FOREIGN KEY (`id_rutina`)
    REFERENCES `bddietasyrutinas_copia`.`Rutina` (`id_rutina`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Transaccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Transaccion` (
  `id_transaccion` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATETIME(6) NULL DEFAULT NULL,
  `tipo` VARCHAR(255) NULL DEFAULT NULL,
  `id_usuario` INT NULL DEFAULT NULL,
  `id_rutina` INT NULL DEFAULT NULL,
  `id_dieta` INT NULL DEFAULT NULL,
  `id_horario` INT NULL DEFAULT NULL,
  `id_historial` INT NULL DEFAULT NULL,
  `id_reunion` INT NULL DEFAULT NULL,
  `id_asistencia` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_transaccion`),
  INDEX `id_usuario` (`id_usuario` ASC) VISIBLE,
  INDEX `id_rutina` (`id_rutina` ASC) VISIBLE,
  INDEX `id_dieta` (`id_dieta` ASC) VISIBLE,
  INDEX `id_horario` (`id_horario` ASC) VISIBLE,
  INDEX `id_historial` (`id_historial` ASC) VISIBLE,
  INDEX `id_reunion` (`id_reunion` ASC) VISIBLE,
  INDEX `id_asistencia` (`id_asistencia` ASC) VISIBLE,
  CONSTRAINT `Transaccion_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`),
  CONSTRAINT `Transaccion_ibfk_2`
    FOREIGN KEY (`id_rutina`)
    REFERENCES `bddietasyrutinas_copia`.`Rutina` (`id_rutina`),
  CONSTRAINT `Transaccion_ibfk_3`
    FOREIGN KEY (`id_dieta`)
    REFERENCES `bddietasyrutinas_copia`.`Dieta` (`id_dieta`),
  CONSTRAINT `Transaccion_ibfk_4`
    FOREIGN KEY (`id_horario`)
    REFERENCES `bddietasyrutinas_copia`.`Horario` (`id_horario`),
  CONSTRAINT `Transaccion_ibfk_5`
    FOREIGN KEY (`id_historial`)
    REFERENCES `bddietasyrutinas_copia`.`HistorialMedico` (`id_historial`),
  CONSTRAINT `Transaccion_ibfk_6`
    FOREIGN KEY (`id_reunion`)
    REFERENCES `bddietasyrutinas_copia`.`Reunion` (`id_reunion`),
  CONSTRAINT `Transaccion_ibfk_7`
    FOREIGN KEY (`id_asistencia`)
    REFERENCES `bddietasyrutinas_copia`.`Asistencia` (`id_asistencia`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Notificacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Notificacion` (
  `id_notificacion` BIGINT NOT NULL AUTO_INCREMENT,
  `id_transaccion` INT NULL DEFAULT NULL,
  `rol_noti` VARCHAR(255) NULL DEFAULT NULL,
  `mensaje` TEXT NULL DEFAULT NULL,
  `estado` TINYINT(1) NULL DEFAULT NULL,
  `fecha` DATETIME NULL DEFAULT NULL,
  `dia_semana` VARCHAR(20) NULL DEFAULT NULL,
  `diaSemana` VARCHAR(255) NULL DEFAULT NULL,
  `estnoti` BIT(1) NULL DEFAULT NULL,
  `timestamp` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id_notificacion`),
  INDEX `FKe8798h1jp1ijr7c9oex0idx0f` (`id_transaccion` ASC) VISIBLE,
  CONSTRAINT `FKe8798h1jp1ijr7c9oex0idx0f`
    FOREIGN KEY (`id_transaccion`)
    REFERENCES `bddietasyrutinas_copia`.`Transaccion` (`id_transaccion`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`Rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`Rol` (
  `id_rol` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NULL DEFAULT NULL,
  `descripcion` TEXT NULL DEFAULT NULL,
  `estado` TINYINT(1) NULL DEFAULT '1',
  PRIMARY KEY (`id_rol`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`RutinaEjercicio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`RutinaEjercicio` (
  `id_rutina` INT NULL DEFAULT NULL,
  `id_ejercicio` INT NULL DEFAULT NULL,
  INDEX `id_rutina` (`id_rutina` ASC) VISIBLE,
  INDEX `id_ejercicio` (`id_ejercicio` ASC) VISIBLE,
  CONSTRAINT `RutinaEjercicio_ibfk_1`
    FOREIGN KEY (`id_rutina`)
    REFERENCES `bddietasyrutinas_copia`.`Rutina` (`id_rutina`),
  CONSTRAINT `RutinaEjercicio_ibfk_2`
    FOREIGN KEY (`id_ejercicio`)
    REFERENCES `bddietasyrutinas_copia`.`Ejercicio` (`id_ejercicio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bddietasyrutinas_copia`.`TransaccionUsuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bddietasyrutinas_copia`.`TransaccionUsuario` (
  `id_transusu` BIGINT NOT NULL AUTO_INCREMENT,
  `fecha` DATETIME(6) NULL DEFAULT NULL,
  `login` DATETIME(6) NULL DEFAULT NULL,
  `logout` DATETIME(6) NULL DEFAULT NULL,
  `cambio_password` DATETIME(6) NULL DEFAULT NULL,
  `cambio_correo` DATETIME(6) NULL DEFAULT NULL,
  `id_usuario` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_transusu`),
  INDEX `id_usuario` (`id_usuario` ASC) VISIBLE,
  CONSTRAINT `TransaccionUsuario_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `bddietasyrutinas_copia`.`Usuario` (`id_usuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 65
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
