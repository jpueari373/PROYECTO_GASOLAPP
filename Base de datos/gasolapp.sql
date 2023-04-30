-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 04-04-2023 a las 19:57:11
-- Versión del servidor: 10.5.16-MariaDB
-- Versión de PHP: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `id20557596_gasolapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marcadores`
--

CREATE TABLE `marcadores` (
  `id` int(11) NOT NULL,
  `latitud` int(11) NOT NULL,
  `longitud` int(11) NOT NULL,
  `rotulo` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `direccion` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `localidad` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `municipio` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `provincia` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `codigo_postal` int(5) NOT NULL,
  `horario` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(24) COLLATE utf8_unicode_ci NOT NULL,
  `usuario` varchar(24) COLLATE utf8_unicode_ci NOT NULL,
  `contrasena` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `telefono` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `usuario`, `contrasena`, `email`, `telefono`) VALUES
(1, 'admin', 'admin', 'admin', 'admin@localhost.com', 667002526),
(4, 'prueba', 'fdgdf', 'fdg', 'ghdh', 123),
(5, 'hkk\n', 'yjk', 'jjhk', 'hjki', 6699),
(6, 'jaime', 'jaime', 'jaime', 'jaime.puerta@gmil.com', 123456),
(7, 'antonioManco', 'franMoney', '123456', 'raulito', 123456);

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
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `marcadores`
--
ALTER TABLE `marcadores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
