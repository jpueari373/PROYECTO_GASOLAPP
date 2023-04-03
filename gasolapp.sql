-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 03-04-2023 a las 16:00:39
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gasolapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marcadores`
--

CREATE TABLE `marcadores` (
  `id` varchar(5) NOT NULL,
  `latitud` int(50) NOT NULL,
  `longitud` int(50) NOT NULL,
  `rotulo` int(120) NOT NULL,
  `direccion` varchar(120) NOT NULL,
  `localidad` varchar(120) NOT NULL,
  `municipio` varchar(120) NOT NULL,
  `provincia` varchar(120) NOT NULL,
  `codigo_postal` int(5) NOT NULL,
  `horario` varchar(20) NOT NULL,
  `id_usuario` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` varchar(5) NOT NULL,
  `Nombre_Completo` varchar(24) NOT NULL,
  `Nombre_Usuario` varchar(24) NOT NULL,
  `contraseña` varchar(12) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telefono` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `Nombre_Completo`, `Nombre_Usuario`, `contraseña`, `email`, `telefono`) VALUES
('us001', 'admin', 'admin', 'admin', 'admin@localhost.com', 667002526);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `marcadores`
--
ALTER TABLE `marcadores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_marcadores_usuarios` (`id_usuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `marcadores`
--
ALTER TABLE `marcadores`
  ADD CONSTRAINT `fk_marcadores_usuarios` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
