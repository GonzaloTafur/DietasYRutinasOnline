
-- Volcando estructura de base de datos para bddietasyrutinas_copia
CREATE DATABASE IF NOT EXISTS `bddietasyrutinas_copia` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bddietasyrutinas_copia`;

-- Volcando estructura para tabla bddietasyrutinas_copia.Alimento
CREATE TABLE IF NOT EXISTS `Alimento` (
  `id_alimento` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nutrientes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tipo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descripcion` text,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_alimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Alimento: ~7 rows (aproximadamente)
INSERT INTO `Alimento` (`id_alimento`, `nombre`, `nutrientes`, `tipo`, `descripcion`, `estado`) VALUES
	(1, 'Batido de Plátano y Avena', '15g de proteína, 70g de carbohidratos, 10g de grasas', 'Desayuno', 'Batido con leche (250 ml), plátano (1 unidad), avena (50g). Aporta energía y favorece el crecimiento muscular.', 1),
	(2, 'Pan con Palta y Huevo', '20g de proteína, 50g de carbohidratos, 18g de grasas', 'Desayuno', 'Pan integral con palta (50g) y huevo frito o cocido (2 unidades). Rico en grasas saludables y proteínas.', 1),
	(3, 'Arroz con Pollo', '30g de proteína, 80g de carbohidratos, 15g de grasas', 'Almuerzo', 'Plato principal. Contiene arroz (200g), pollo (150g), arvejas, zanahorias y aceite vegetal.', 1),
	(4, 'Lentejas con Carne', '30g de proteína, 80g de carbohidratos, 15g de grasas', 'Almuerzo', 'Lentejas (200g) acompañadas con carne de res (150g) y arroz blanco. Rica en fibra, carbohidratos y proteínas para volumen.', 1),
	(5, 'Papa Rellena con Carne', '22g de proteína, 50g de carbohidratos, 12g de grasas', 'Cena', 'Papas rellenas con carne molida de res (100g), con cebolla, ajo, y especias. Aporta calorías para recuperación nocturna.', 1),
	(6, 'Tallarines Verdes con Pollo', '28g de proteína, 90g de carbohidratos, 18g de grasas', 'Cena', 'Pasta (200g) con pesto de albahaca, espinacas y pollo a la plancha (150g). Alto en carbohidratos y proteínas.', 1),
	(7, 'Yogurt con Quinua', '12g de proteína, 40g de carbohidratos, 5g de grasas', 'Merienda', 'Yogurt natural (200 ml) con quinua cocida (50g). Ideal para complementar la ingesta calórica del día.', 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.Asistencia
CREATE TABLE IF NOT EXISTS `Asistencia` (
  `id_asistencia` int NOT NULL AUTO_INCREMENT,
  `id_reunion` int DEFAULT NULL,
  `id_paciente` int DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_asistencia`),
  KEY `id_reunion` (`id_reunion`),
  KEY `FK7w7pmji8buve1sobcc5hqemuw` (`id_paciente`),
  CONSTRAINT `Asistencia_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `Usuario` (`id_usuario`),
  CONSTRAINT `Asistencia_ibfk_2` FOREIGN KEY (`id_reunion`) REFERENCES `Reunion` (`id_reunion`),
  CONSTRAINT `FK7w7pmji8buve1sobcc5hqemuw` FOREIGN KEY (`id_paciente`) REFERENCES `Paciente` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Asistencia: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bddietasyrutinas_copia.Condicion
CREATE TABLE IF NOT EXISTS `Condicion` (
  `id_condicion` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_condicion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Condicion: ~4 rows (aproximadamente)
INSERT INTO `Condicion` (`id_condicion`, `nombre`, `estado`) VALUES
	(1, 'Intolerancia a la lactosa', 1),
	(2, 'Intolerancia al gluten', 1),
	(3, 'Diabetes', 1),
	(4, 'Vegano', 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.Dieta
CREATE TABLE IF NOT EXISTS `Dieta` (
  `id_dieta` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text,
  `objetivo` enum('V','D') DEFAULT NULL,
  `id_nutriologo` int DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_dieta`),
  KEY `FK20stwmvdh5g7hdvvwvcv7kc1a` (`id_nutriologo`),
  KEY `id_objetivo` (`objetivo`) USING BTREE,
  CONSTRAINT `Dieta_ibfk_2` FOREIGN KEY (`id_nutriologo`) REFERENCES `Usuario` (`id_usuario`),
  CONSTRAINT `FK20stwmvdh5g7hdvvwvcv7kc1a` FOREIGN KEY (`id_nutriologo`) REFERENCES `Nutriologo` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='V: VOLUMEN\r\nD: DEFICIT';

-- Volcando datos para la tabla bddietasyrutinas_copia.Dieta: ~3 rows (aproximadamente)
INSERT INTO `Dieta` (`id_dieta`, `nombre`, `descripcion`, `objetivo`, `id_nutriologo`, `estado`) VALUES
	(1, 'Energía completa', 'Esta dieta está diseñada para quienes buscan aumentar su masa muscular de manera efectiva. Comienza el día con un batido de plátano y avena y pan con palta y huevo , que aportan energía y nutrientes esenciales. El batido de proteínas con frutas refuerza la recuperación post-entrenamiento. El almuerzo incluye arroz con pollo , una rica fuente de carbohidratos y proteínas. El yogurt con quinua es una excelente merienda, y para la cena, el pollo a la plancha con camote ofrece proteínas magras. Recuerda que la cena es solo una recomendación; Si te sobra almuerzo, puedes consumirlo para evitar desperdicios. ¡La calidad de los alimentos es clave para tu progreso!', 'V', 1, 1),
	(4, 'Nutrición Vital', 'Esta dieta está pensada para quienes buscan ganar masa muscular de forma saludable. Comienza el día con un batido de plátano y avena y una tortilla de huevo con queso fresco y espinacas , brindando energía y proteínas. Una media mañana, disfruta de yogur con semillas de chía y frutas , que aporta fibra y grasas saludables. El almuerzo incluye lentejas con carne , ricas en proteínas y hierro, y como merienda, huevos cocidos con palta y pan integral , que ofrece una combinación de proteínas y grasas saludables. La ensalada de menestras con atún cierra el día, aportando proteínas magras. Recuerda que la cena es solo una sugerencia; Si sobra comida del almuerzo, también puedes consumirla para evitar el desperdicio. Mantén un enfoque en la calidad de los alimentos para optimizar tu progreso.', 'V', 4, 1),
	(5, 'Fuerza Criolla', 'Esta dieta está diseñada para quienes desean aumentar masa muscular de manera efectiva. Comienza el día con un licuado de quinua y plátano acompañado de pan con palta y huevo , que proporciona energía y nutrientes esenciales. Una media mañana, disfruta de un batido de mango, avena y proteína , que ofrece carbohidratos y proteínas para mantenerte activo. El almuerzo incluye un delicioso lomo saltado con arroz y papas , que es una fuente completa de proteínas y carbohidratos. En la merienda, opta por yogur con quinua , aportando calcio y fibra. Finaliza el día con una tortilla de espinacas, queso y yuca , ideal para reponer energías. Recuerda que la cena puede variar; Si sobra comida del almuerzo, puedes consumirla, optimizando así tu ingesta y evitando el desperdicio. ¡La calidad de los alimentos es clave en tu camino hacia el volumen!', 'V', 6, 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.DietaAlimento
CREATE TABLE IF NOT EXISTS `DietaAlimento` (
  `id_dieta` int DEFAULT NULL,
  `id_alimento` int DEFAULT NULL,
  KEY `id_dieta` (`id_dieta`),
  KEY `id_alimento` (`id_alimento`),
  CONSTRAINT `DietaAlimento_ibfk_1` FOREIGN KEY (`id_dieta`) REFERENCES `Dieta` (`id_dieta`),
  CONSTRAINT `DietaAlimento_ibfk_2` FOREIGN KEY (`id_alimento`) REFERENCES `Alimento` (`id_alimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.DietaAlimento: ~7 rows (aproximadamente)
INSERT INTO `DietaAlimento` (`id_dieta`, `id_alimento`) VALUES
	(1, 1),
	(1, 3),
	(1, 7),
	(4, 1),
	(4, 4),
	(5, 2),
	(5, 7);

-- Volcando estructura para tabla bddietasyrutinas_copia.DietaCondicion
CREATE TABLE IF NOT EXISTS `DietaCondicion` (
  `id_dieta` int DEFAULT NULL,
  `id_condicion` int DEFAULT NULL,
  KEY `id_dieta` (`id_dieta`),
  KEY `id_condicion` (`id_condicion`),
  CONSTRAINT `DietaCondicion_ibfk_1` FOREIGN KEY (`id_dieta`) REFERENCES `Dieta` (`id_dieta`),
  CONSTRAINT `DietaCondicion_ibfk_2` FOREIGN KEY (`id_condicion`) REFERENCES `Condicion` (`id_condicion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.DietaCondicion: ~2 rows (aproximadamente)
INSERT INTO `DietaCondicion` (`id_dieta`, `id_condicion`) VALUES
	(1, 1),
	(4, 4),
	(5, 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.Ejercicio
CREATE TABLE IF NOT EXISTS `Ejercicio` (
  `id_ejercicio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grupo_muscular` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tipo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `series` int NOT NULL,
  `repeticiones` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `descripcion` text,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_ejercicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Ejercicio: ~8 rows (aproximadamente)
INSERT INTO `Ejercicio` (`id_ejercicio`, `nombre`, `grupo_muscular`, `tipo`, `series`, `repeticiones`, `descripcion`, `estado`) VALUES
	(1, 'Press de banca', 'Pecho', 'Tren Superior', 3, '6 a 8', 'Acuéstate en un banco plano con los pies en el suelo. Baja la barra hasta el pecho y empújala hacia arriba hasta estirar los brazos. Mantén control en todo el movimiento.', 1),
	(2, 'Press inclinado', 'Pecho', 'Tren Superior', 3, '6 a 8', 'En un banco inclinado, baja la barra o mancuernas hacia el pecho superior. Empuja hacia arriba contrayendo el pecho. Controla el movimiento para evitar lesiones.', 1),
	(3, 'Remo con barra', 'Espalda', 'Tren Superior', 3, '6 a 8', 'Inclina el torso hacia adelante con la barra en las manos. Sube la barra hacia el abdomen y baja controladamente. Mantén la espalda recta durante el ejercicio.', 1),
	(4, 'Curl con barra', 'Biceps', 'Tren Superior', 3, '6 a 8', 'Sostén la barra con las palmas hacia arriba. Flexiona los codos elevando la barra hasta que los antebrazos toquen los bíceps. Baja lentamente sin bloquear los codos.', 1),
	(5, 'Press frances', 'Triceps', 'Tren Superior', 3, '6 a 8', 'Acuéstate en un banco, sujeta una barra Z. Flexiona los codos bajando la barra hacia la frente y luego extiende los brazos completamente hacia arriba.', 1),
	(6, 'Extensión de tríceps en polea', 'Tríceps', 'Tren Superior', 3, '6 a 8', 'Sujeta la cuerda o barra en una polea alta. Empuja hacia abajo hasta estirar completamente los codos y vuelve a la posición inicial de forma controlada.', 1),
	(7, 'Elevaciones laterales', 'Hombros', 'Tren Superior', 3, '6 a 8', 'De pie, con una mancuerna en cada mano, eleva los brazos hacia los lados hasta que estén paralelos al suelo. Baja lentamente controlando el movimiento.', 1),
	(8, 'Sentadilla con barra', 'Cuádriceps', 'Tren Inferior', 3, '6 a 8', 'Con la barra sobre los hombros, baja flexionando las rodillas hasta que los muslos estén paralelos al suelo. Sube extendiendo las rodillas y caderas.', 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.HistorialDieta
CREATE TABLE IF NOT EXISTS `HistorialDieta` (
  `id_historial` int DEFAULT NULL,
  `id_dieta` int DEFAULT NULL,
  `id_histotial` bigint NOT NULL,
  KEY `id_historial` (`id_historial`),
  KEY `id_dieta` (`id_dieta`),
  CONSTRAINT `HistorialDieta_ibfk_1` FOREIGN KEY (`id_historial`) REFERENCES `HistorialMedico` (`id_historial`),
  CONSTRAINT `HistorialDieta_ibfk_2` FOREIGN KEY (`id_dieta`) REFERENCES `Dieta` (`id_dieta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.HistorialDieta: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bddietasyrutinas_copia.HistorialMedico
CREATE TABLE IF NOT EXISTS `HistorialMedico` (
  `id_historial` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int DEFAULT NULL,
  `peso_corporal` decimal(10,0) DEFAULT NULL,
  `estatura` decimal(10,0) DEFAULT NULL,
  `perimetro_cintura` decimal(10,0) DEFAULT NULL,
  `perimetro_cadera` decimal(10,0) DEFAULT NULL,
  `perimetro_muslo` decimal(10,0) DEFAULT NULL,
  `perimetro_brazo` decimal(10,0) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `fecha_modif` date DEFAULT NULL,
  PRIMARY KEY (`id_historial`),
  KEY `id_paciente` (`id_paciente`),
  CONSTRAINT `HistorialMedico_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `Paciente` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.HistorialMedico: ~4 rows (aproximadamente)
INSERT INTO `HistorialMedico` (`id_historial`, `id_paciente`, `peso_corporal`, `estatura`, `perimetro_cintura`, `perimetro_cadera`, `perimetro_muslo`, `perimetro_brazo`, `estado`, `fecha_modif`) VALUES
	(1, 5, 80, 2, 0, 0, 0, 0, 0, '2025-07-10'),
	(2, 2, 70, 2, 0, 0, 0, 0, 0, '2025-07-10'),
	(4, 2, 70, 2, 40, 40, 20, 10, 1, '2025-11-26'),
	(8, 5, 73, 2, 30, 35, 20, 18, 1, '2025-11-26');

-- Volcando estructura para tabla bddietasyrutinas_copia.Horario
CREATE TABLE IF NOT EXISTS `Horario` (
  `id_horario` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int DEFAULT NULL,
  `id_rutina` int DEFAULT NULL,
  `dia_semana` enum('LU','MA','MI','JU','VI','SA','DO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `parte_dia` varchar(20) DEFAULT NULL,
  `descanso_serie` varchar(20) DEFAULT NULL,
  `descanso_ejercicio` varchar(20) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '1',
  `paciente` bigint DEFAULT NULL,
  `rutina` bigint DEFAULT NULL,
  PRIMARY KEY (`id_horario`),
  UNIQUE KEY `UKpmvca0qcmgc48gq2oja0apvak` (`paciente`),
  KEY `id_paciente` (`id_paciente`),
  KEY `id_rutina` (`id_rutina`),
  CONSTRAINT `Horario_ibfk_1` FOREIGN KEY (`id_paciente`) REFERENCES `Usuario` (`id_usuario`),
  CONSTRAINT `Horario_ibfk_2` FOREIGN KEY (`id_rutina`) REFERENCES `Rutina` (`id_rutina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Horario: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bddietasyrutinas_copia.Notificacion
CREATE TABLE IF NOT EXISTS `Notificacion` (
  `id_notificacion` bigint NOT NULL AUTO_INCREMENT,
  `id_transaccion` int DEFAULT NULL,
  `rol_noti` varchar(255) DEFAULT NULL,
  `mensaje` text,
  `estado` tinyint(1) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `dia_semana` varchar(20) DEFAULT NULL,
  `diaSemana` varchar(255) DEFAULT NULL,
  `estnoti` bit(1) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id_notificacion`),
  KEY `FKe8798h1jp1ijr7c9oex0idx0f` (`id_transaccion`),
  CONSTRAINT `FKe8798h1jp1ijr7c9oex0idx0f` FOREIGN KEY (`id_transaccion`) REFERENCES `Transaccion` (`id_transaccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Notificacion: ~0 rows (aproximadamente)
INSERT INTO `Notificacion` (`id_notificacion`, `id_transaccion`, `rol_noti`, `mensaje`, `estado`, `fecha`, `dia_semana`, `diaSemana`, `estnoti`, `timestamp`) VALUES
	(1, 1, 'Paciente', 'Hoy tienes una cita programada.', NULL, NULL, NULL, 'VIERNES', b'1', '2025-11-14 08:00:00.328252');

-- Volcando estructura para tabla bddietasyrutinas_copia.Nutriologo
CREATE TABLE IF NOT EXISTS `Nutriologo` (
  `id_usuario` int NOT NULL,
  `cargo` varchar(255) DEFAULT NULL,
  `CV` varbinary(255) DEFAULT NULL,
  `rol` enum('SU','E') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'E',
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `Nutriologo_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Nutriologo: ~5 rows (aproximadamente)
INSERT INTO `Nutriologo` (`id_usuario`, `cargo`, `CV`, `rol`) VALUES
	(1, NULL, NULL, 'SU'),
	(3, '', NULL, 'E'),
	(4, NULL, NULL, 'E'),
	(6, NULL, NULL, 'E'),
	(7, NULL, NULL, 'E');

-- Volcando estructura para tabla bddietasyrutinas_copia.Objetivo
CREATE TABLE IF NOT EXISTS `Objetivo` (
  `id_objetivo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descripcion` text,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_objetivo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Objetivo: ~3 rows (aproximadamente)
INSERT INTO `Objetivo` (`id_objetivo`, `nombre`, `descripcion`, `estado`) VALUES
	(1, 'Volumen', NULL, 1),
	(2, 'Deficit', NULL, 1),
	(3, 'Ninguno', 'No tiene ningun objetivo además de mejorar sus hábitos', 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.Paciente
CREATE TABLE IF NOT EXISTS `Paciente` (
  `id_usuario` int NOT NULL,
  `frec_ejercicios` enum('MP','P','N','M','S') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id_condicion` int NOT NULL,
  `id_objetivo` int NOT NULL,
  `id_historial` int DEFAULT NULL,
  KEY `id_usuario` (`id_usuario`),
  KEY `id_objetivo` (`id_objetivo`),
  KEY `id_condicion` (`id_condicion`),
  KEY `id_historial` (`id_historial`),
  CONSTRAINT `FK_Paciente_Condicion` FOREIGN KEY (`id_condicion`) REFERENCES `Condicion` (`id_condicion`),
  CONSTRAINT `FK_Paciente_Objetivo` FOREIGN KEY (`id_objetivo`) REFERENCES `Objetivo` (`id_objetivo`),
  CONSTRAINT `FK_PacienteHistorial` FOREIGN KEY (`id_historial`) REFERENCES `HistorialMedico` (`id_historial`),
  CONSTRAINT `Paciente_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Paciente: ~2 rows (aproximadamente)
INSERT INTO `Paciente` (`id_usuario`, `frec_ejercicios`, `id_condicion`, `id_objetivo`, `id_historial`) VALUES
	(2, 'MP', 1, 1, 4),
	(5, 'P', 1, 3, 8);

-- Volcando estructura para tabla bddietasyrutinas_copia.Reunion
CREATE TABLE IF NOT EXISTS `Reunion` (
  `id_reunion` int NOT NULL AUTO_INCREMENT,
  `id_nutriologo` int DEFAULT NULL,
  `motivo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dia_semana` enum('LU','MA','MI','JU','VI','SA','DO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `hora_inicio` time NOT NULL,
  `enlace` varchar(255) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_reunion`),
  KEY `FK3malr2wpwncees3sempncxt2e` (`id_nutriologo`),
  CONSTRAINT `FK3malr2wpwncees3sempncxt2e` FOREIGN KEY (`id_nutriologo`) REFERENCES `Nutriologo` (`id_usuario`),
  CONSTRAINT `Reunion_ibfk_1` FOREIGN KEY (`id_nutriologo`) REFERENCES `Usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Reunion: ~3 rows (aproximadamente)
INSERT INTO `Reunion` (`id_reunion`, `id_nutriologo`, `motivo`, `dia_semana`, `hora_inicio`, `enlace`, `estado`) VALUES
	(1, 3, 'Presentación y bienvenida', 'MA', '15:00:00', 'https://meet.google.com/landing?pli=1', 1),
	(2, 6, 'Presentación y bienvenida', 'LU', '12:00:00', 'https://meet.google.com/landing', 1),
	(3, 6, 'Día 1', 'MI', '09:00:00', 'https://meet.google.com/landing?pli=1', 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.Rol
CREATE TABLE IF NOT EXISTS `Rol` (
  `id_rol` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `descripcion` text,
  `estado` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_rol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Rol: ~2 rows (aproximadamente)
INSERT INTO `Rol` (`id_rol`, `nombre`, `descripcion`, `estado`) VALUES
	(1, 'Superusuario', NULL, 1),
	(2, 'Estandar', NULL, 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.Rutina
CREATE TABLE IF NOT EXISTS `Rutina` (
  `id_rutina` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tipo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `descripcion` text,
  `objetivo` enum('V','D') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `id_nutriologo` int DEFAULT NULL,
  `nivel` enum('PRINCIPIANTE','INTERMEDIO','AVANZADO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `parte_cuerpo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_rutina`),
  KEY `FKr0u16fjcbcamwrbx2jrf9h3hv` (`id_nutriologo`),
  KEY `id_objetivo` (`objetivo`) USING BTREE,
  CONSTRAINT `FKr0u16fjcbcamwrbx2jrf9h3hv` FOREIGN KEY (`id_nutriologo`) REFERENCES `Nutriologo` (`id_usuario`),
  CONSTRAINT `Rutina_ibfk_2` FOREIGN KEY (`id_nutriologo`) REFERENCES `Usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='V: VOLUMEN\r\nD: DEFICIT';

-- Volcando datos para la tabla bddietasyrutinas_copia.Rutina: ~3 rows (aproximadamente)
INSERT INTO `Rutina` (`id_rutina`, `nombre`, `tipo`, `descripcion`, `objetivo`, `id_nutriologo`, `nivel`, `parte_cuerpo`, `estado`) VALUES
	(1, 'Fuerza Superior 1', 'Volumen', 'Esta rutina está diseñada para principiantes en fase de volumen. Comienza con la prensa de banca para activar los músculos del pecho. Se recomienda un descanso de 3 a 4 minutos entre series. Utilice pesos ligeros para asegurar una técnica adecuada. La rutina trabaja los principales músculos del tren superior, favoreciendo el desarrollo de la fuerza y ​​la masa muscular. Concéntrate en mantener una buena forma en cada ejercicio.', 'V', 4, 'PRINCIPIANTE', 'Tren Superior', 1),
	(2, 'Fuerza Superior 2', 'Volumen', 'Esta rutina se enfoca en construir masa muscular en el tren superior. Empieza con la prensa inclinada para activar los pectorales. Realiza de 8 a 12 repeticiones por serie, descansando de 3 a 4 minutos entre ellas. Es crucial mantener un peso ligero y prestar atención a la técnica para evitar lesiones. Las elevaciones laterales ayudan a desarrollar los hombros, mientras que los fondos activan los tríceps. Asegúrese de calentar adecuadamente antes de comenzar.', 'V', 1, 'PRINCIPIANTE', 'Tren Superior', 1),
	(3, 'Fuerza Superior 2', NULL, 'En esta rutina, se utilizan máquinas y ejercicios con poco peso, ideales para principiantes. Comienza con la prensa de pecho en máquina para facilitar el movimiento. Descansa de 3 a 4 minutos entre series. Focaliza tu atención en la técnica, especialmente en el curl de bíceps. Esta combinación de ejercicios ayudará a aumentar la fuerza y ​​preparar los músculos para cargas más pesadas en el futuro.', 'V', 6, 'PRINCIPIANTE', 'Tren Superior', 1);

-- Volcando estructura para tabla bddietasyrutinas_copia.RutinaEjercicio
CREATE TABLE IF NOT EXISTS `RutinaEjercicio` (
  `id_rutina` int DEFAULT NULL,
  `id_ejercicio` int DEFAULT NULL,
  KEY `id_rutina` (`id_rutina`),
  KEY `id_ejercicio` (`id_ejercicio`),
  CONSTRAINT `RutinaEjercicio_ibfk_1` FOREIGN KEY (`id_rutina`) REFERENCES `Rutina` (`id_rutina`),
  CONSTRAINT `RutinaEjercicio_ibfk_2` FOREIGN KEY (`id_ejercicio`) REFERENCES `Ejercicio` (`id_ejercicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.RutinaEjercicio: ~6 rows (aproximadamente)
INSERT INTO `RutinaEjercicio` (`id_rutina`, `id_ejercicio`) VALUES
	(1, 1),
	(1, 3),
	(1, 4),
	(1, 5),
	(3, 1),
	(3, 7);

-- Volcando estructura para tabla bddietasyrutinas_copia.Transaccion
CREATE TABLE IF NOT EXISTS `Transaccion` (
  `id_transaccion` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `id_rutina` int DEFAULT NULL,
  `id_dieta` int DEFAULT NULL,
  `id_horario` int DEFAULT NULL,
  `id_historial` int DEFAULT NULL,
  `id_reunion` int DEFAULT NULL,
  `id_asistencia` int DEFAULT NULL,
  PRIMARY KEY (`id_transaccion`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_rutina` (`id_rutina`),
  KEY `id_dieta` (`id_dieta`),
  KEY `id_horario` (`id_horario`),
  KEY `id_historial` (`id_historial`),
  KEY `id_reunion` (`id_reunion`),
  KEY `id_asistencia` (`id_asistencia`),
  CONSTRAINT `Transaccion_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`),
  CONSTRAINT `Transaccion_ibfk_2` FOREIGN KEY (`id_rutina`) REFERENCES `Rutina` (`id_rutina`),
  CONSTRAINT `Transaccion_ibfk_3` FOREIGN KEY (`id_dieta`) REFERENCES `Dieta` (`id_dieta`),
  CONSTRAINT `Transaccion_ibfk_4` FOREIGN KEY (`id_horario`) REFERENCES `Horario` (`id_horario`),
  CONSTRAINT `Transaccion_ibfk_5` FOREIGN KEY (`id_historial`) REFERENCES `HistorialMedico` (`id_historial`),
  CONSTRAINT `Transaccion_ibfk_6` FOREIGN KEY (`id_reunion`) REFERENCES `Reunion` (`id_reunion`),
  CONSTRAINT `Transaccion_ibfk_7` FOREIGN KEY (`id_asistencia`) REFERENCES `Asistencia` (`id_asistencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Transaccion: ~1 rows (aproximadamente)
INSERT INTO `Transaccion` (`id_transaccion`, `fecha`, `tipo`, `id_usuario`, `id_rutina`, `id_dieta`, `id_horario`, `id_historial`, `id_reunion`, `id_asistencia`) VALUES
	(1, '2025-11-14 08:00:00.046275', 'Recordatiorio de reunión', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- Volcando estructura para tabla bddietasyrutinas_copia.TransaccionUsuario
CREATE TABLE IF NOT EXISTS `TransaccionUsuario` (
  `id_transusu` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) DEFAULT NULL,
  `login` datetime(6) DEFAULT NULL,
  `logout` datetime(6) DEFAULT NULL,
  `cambio_password` datetime(6) DEFAULT NULL,
  `cambio_correo` datetime(6) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_transusu`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `TransaccionUsuario_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.TransaccionUsuario: ~0 rows (aproximadamente)

-- Volcando estructura para tabla bddietasyrutinas_copia.Usuario
CREATE TABLE IF NOT EXISTS `Usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(80) DEFAULT NULL,
  `apellidos` varchar(80) DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `nacionalidad` varchar(40) DEFAULT NULL,
  `sexo` enum('Masculino','Femenino') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `biografia` text,
  `usuario` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `correo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla bddietasyrutinas_copia.Usuario: ~7 rows (aproximadamente)
INSERT INTO `Usuario` (`id_usuario`, `nombres`, `apellidos`, `fecha_nacimiento`, `nacionalidad`, `sexo`, `biografia`, `usuario`, `correo`, `password`, `estado`) VALUES
	(1, 'Gonzalo', 'Tafur', '2003-10-28', 'Perú', 'Masculino', NULL, 'Anonprueba', 'prueba@mail.com', '$2a$10$yeqxuNM0IpXKYcYAmZu19eJq72MFmlem4A.jJqd3eu.FSd8advzD6', 1),
	(2, 'Jose', 'Rodrigez', '2005-12-08', 'Perú', 'Masculino', '', 'PacienteTest2', 'test2@mail.com', '$2a$10$HnY0PxFL8..9Hr//3em2iefzx42arShgBfi8PWNcOu2grxtelHc0S', 1),
	(3, 'Jose', 'Flores', '2001-09-02', 'Argentina', 'Masculino', '', 'NutriTest', 'testn@mail', '$2a$10$7WfP555LsZLt46BOA1v9EOi2FSzRJG8BppEIvyGtTYcbAK5i10NPm', 1),
	(4, 'Carlos', 'Gonzales', '1999-03-06', 'Colombia', 'Masculino', '', 'NutriTest3', 'testn1@mail', '$2a$10$HQ9mCL43spBhTpaM.tTce.lILkjMU.FueBcg3.DUnVGoHTC9AT0/K', 1),
	(5, 'Juan', 'Rodrigez', '2007-03-15', 'Perú', 'Masculino', '', 'PacienteTest3', 'test3@mail', '$2a$10$gBlKkOz/ETNdn6dlRaeL6.XTkXzvXUoKSMljZSTQ91cYTtlySYaW2', 1),
	(6, 'John', 'Doe', '1997-07-16', 'Perú', 'Masculino', NULL, 'Johndoe1', 'johndoe1234@mail.com', '$2a$10$Jeh4CCeBALVI2VLC6xeiL.uUVcjQS3PTAWNQyQgbtck0zWvabxAZq', 1),
	(7, 'Jane', 'Doe', '2001-11-07', 'Perú', 'Femenino', NULL, 'JANEDOE20', 'janedoe20@mail.com', '$2a$10$2FbmDTQuxJNiCXgJ333Kbuycc/48YrzNw1OOiFqdiEgm.vghQ8KIy', 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
