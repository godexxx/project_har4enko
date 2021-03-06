-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.1.65-community-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-04-24 20:29:03
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping structure for table project_tema.patern
DROP TABLE IF EXISTS `patern`;
CREATE TABLE IF NOT EXISTS `patern` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Name` char(50) NOT NULL,
  `Sequrity` char(50) NOT NULL,
  `Capasity` char(50) NOT NULL,
  `Character` text,
  `Module` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_patern_Modules` (`Module`),
  CONSTRAINT `FK_patern_Modules` FOREIGN KEY (`Module`) REFERENCES `layers` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- Dumping data for table project_tema.patern: ~32 rows (approximately)
/*!40000 ALTER TABLE `patern` DISABLE KEYS */;
INSERT INTO `patern` (`ID`, `Name`, `Sequrity`, `Capasity`, `Character`, `Module`) VALUES
	(2, 'Application Façade (Фасад приложения).', 'Високая', 'Средняя', 'Централизует и агрегирует поведение для обеспечения унифицированного слоя сервисов.', 1),
	(3, 'Chain of Responsibility (Цепочка обязанностей).', 'Низкая', 'Високая', 'Предоставляя возможность обработать запрос нескольким объектам, устраняет возможность связывания отправителя запроса с его получателем.', 2),
	(4, 'Command (Команда).', 'Средняя', 'Високая', 'Инкапсулирует обработку запроса в отдельный командный объект с общим интерфейсом выполнения.', 2),
	(5, 'Domain Model (Модель предметной области).', 'Средняя', 'Низкая', 'Набор бизнес-объектов, представляющих сущности предметной области и отношения между ними.', 4),
	(7, 'Entity Translator (Транслятор сущностей).', 'Високая', 'Средняя', 'Объект, преобразующий типы данных сообщения в бизнес-типы для запросов и выполняющий обратные преобразования для ответов.', 4),
	(8, 'Analytics.', 'Низкая', 'Средняя', 'Специализированная версия шаблона Mesh Composite View, предлагающая конечным пользователям панель анализа данных.', 5),
	(9, 'Mesh Composite View.', 'Низкая', 'Низкая', 'Использует такие компоненты UI, как Веб-части ASP.NET или компоненты MOSS, которые функционируют совместно для предоставления данных одной или разных LOB-систем.', 5),
	(10, 'RSS and Web Services Composition', 'Средняя', '', 'Специализированная версия шаблона Mesh Composite View, объединяющая данные, опубликованные посредством RSS-каналов или Веб-сервисов.', 5),
	(11, 'Active Record (Активная запись).', '', '', 'Включает объект доступа к данным в сущность предметной области.', 7),
	(12, 'Data Mapper (Преобразователь данных).', '', '', 'Реализует слой преобразования между объектами и структурой базы данных, используемый для перемещения данных из одной структуры в другую, обеспечивая при этом их независимость.', 7),
	(13, 'Parallel Processing (Параллельная обработка).', '', '', 'Позволяет обрабатывать множество пакетных операций одновременно, чтобы сократить время обработки.', 7),
	(14, 'Application Façade (Фасад приложения).', 'Високая', 'Средняя', 'Централизует и агрегирует поведение для обеспечения унифицированного слоя сервисов.', 10),
	(15, 'Chain of Responsibility (Цепочка обязанностей).', 'Низкая', 'Високая', 'Предоставляя возможность обработать запрос нескольким объектам, устраняет возможность связывания отправителя запроса с его получателем.', 12),
	(16, 'Command (Команда).', 'Средняя', 'Високая', 'Инкапсулирует обработку запроса в отдельный командный объект с общим интерфейсом выполнения.', 12),
	(17, 'Domain Model (Модель предметной области).', 'Средняя', 'Низкая', 'Набор бизнес-объектов, представляющих сущности предметной области и отношения между ними.', 13),
	(18, 'Analytics.', 'Низкая', 'Средняя', 'Специализированная версия шаблона Mesh Composite View, предлагающая конечным пользователям панель анализа данных.', 14),
	(19, 'RSS and Web Services Composition', 'Средняя', '', 'Специализированная версия шаблона Mesh Composite View, объединяющая данные, опубликованные посредством RSS-каналов или Веб-сервисов.', 14),
	(20, 'Mesh Composite View.', 'Низкая', 'Низкая', 'Использует такие компоненты UI, как Веб-части ASP.NET или компоненты MOSS, которые функционируют совместно для предоставления данных одной или разных LOB-систем.', 14),
	(21, 'Entity Translator (Транслятор сущностей).', 'Високая', 'Средняя', 'Объект, преобразующий типы данных сообщения в бизнес-типы для запросов и выполняющий обратные преобразования для ответов.', 13),
	(22, 'Active Record (Активная запись).', '', '', 'Включает объект доступа к данным в сущность предметной области.', 11),
	(23, 'Data Mapper (Преобразователь данных).', '', '', 'Реализует слой преобразования между объектами и структурой базы данных, используемый для перемещения данных из одной структуры в другую, обеспечивая при этом их независимость.', 11),
	(24, 'Parallel Processing (Параллельная обработка).', '', '', 'Позволяет обрабатывать множество пакетных операций одновременно, чтобы сократить время обработки.', 11),
	(25, 'Command (Команда),', '', '', NULL, 23),
	(26, 'Document (Документ)', '', '', NULL, 23),
	(27, 'Application Façade (Фасад приложения).', 'Високая', 'Средняя', 'Централизует и агрегирует поведение для обеспечения унифицированного слоя сервисов.', 16),
	(29, 'Chain of Responsibility (Цепочка обязанностей).', 'Низкая', 'Високая', 'Предоставляя возможность обработать запрос нескольким объектам, устраняет возможность связывания отправителя запроса с его получателем.', 17),
	(30, 'Command (Команда).', 'Средняя', 'Високая', 'Инкапсулирует обработку запроса в отдельный командный объект с общим интерфейсом выполнения.', 17),
	(31, 'Domain Model (Модель предметной области).', 'Средняя', 'Низкая', 'Набор бизнес-объектов, представляющих сущности предметной области и отношения между ними.', 18),
	(33, 'Entity Translator (Транслятор сущностей).', 'Високая', 'Средняя', 'Объект, преобразующий типы данных сообщения в бизнес-типы для запросов и выполняющий обратные преобразования для ответов.', 18),
	(34, 'Active Record (Активная запись).', '', '', 'Включает объект доступа к данным в сущность предметной области.', 19),
	(35, 'Data Mapper (Преобразователь данных).', '', '', 'Реализует слой преобразования между объектами и структурой базы данных, используемый для перемещения данных из одной структуры в другую, обеспечивая при этом их независимость.', 19),
	(36, 'Parallel Processing (Параллельная обработка).', '', '', 'Позволяет обрабатывать множество пакетных операций одновременно, чтобы сократить время обработки.', 19);
/*!40000 ALTER TABLE `patern` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
