-- MySQL dump 10.13  Distrib 5.7.19, for Linux (x86_64)
--
-- Host: localhost    Database: brewery
-- ------------------------------------------------------
-- Server version	5.7.19-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articles` (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `post_date` date DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (18,'Our Story','2017-08-07'),(19,'Our Story','2017-08-07'),(20,'history',NULL);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descriptions`
--

DROP TABLE IF EXISTS `descriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descriptions` (
  `desc_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` mediumtext COLLATE utf8_unicode_ci,
  `composition` mediumtext COLLATE utf8_unicode_ci,
  `translation_type` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`desc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descriptions`
--

LOCK TABLES `descriptions` WRITE;
/*!40000 ALTER TABLE `descriptions` DISABLE KEYS */;
INSERT INTO `descriptions` VALUES (7,'blonde light beer','this is the most popular variety of beer in all around of the world!','qwe','ENG'),(9,'Интенсивно темное','Самое Темное пиво в мире !!)','В состав входят следующие следующие следующие следующие ингридиенты...','RUS'),(12,'Темное легкое','Темное легкое','Состав..','RUS'),(13,'Intencive Dark','Really the most dark beer arround the World','Composition:...','ENG'),(14,'Iнтенсивно темне','темне пиво','Складова..','UA'),(15,'Легке свiтле','свiтле пиво','Складова..','UA'),(16,'Легкое светлое','светтлое пиво','Состав..','RUS'),(17,'Light','light beer','Composition..','ENG'),(18,'Светлое нефильтрованное','Светлое нефильтрованное пиво','Состав..','RUS'),(19,'Свiтле нефiльтроване','Свiтле нефiльтроване','Складова..','UA'),(20,'Темне легке','Темне легке пиво','Складова..','UA'),(21,'Темное легкое','Темное легкое Темное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкое','Состав..\n\nТемное легкоеТемное легкоеТемное легкоеТемное легкоеТемное легкое','RUS'),(22,'Темне легке пиво','Темне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоaТемне легке пивоa','Складова..\nТемне легке пивоaТемне легке пивоaТемне легке пивоa','UA'),(23,'DARK LIGHT BEER','DARK LIGHT BEER DARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEER','Composition..\nDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEERDARK LIGHT BEER','ENG'),(24,'BLONDESS','Light unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beer','Composition..\nLight unfiltered beerLight unfiltered beerLight unfiltered beerLight unfiltered beer','ENG'),(25,'Светлое нефильтрованное','Светлое нефильтрованное пивоСветлое нефильтрованное пивоСветлое нефильтрованное пивоСветлое нефильтрованное пивоСветлое нефильтрованное пивоСветлое нефильтрованное пивоСветлое нефильтрованное пивоСветлое нефильтрованное пивоСветлое нефильтрованное пиво','Состав..\nСветлое нефильтрованное пивоСветлое нефильтрованное пиво','RUS'),(26,'Свiтле нефiльтроване','Свiтле нефiльтроване пиво Свiтле нефiльтроване пиво\nСвiтле нефiльтроване пивоСвiтле нефiльтроване пивоСвiтле нефiльтроване пивоСвiтле нефiльтроване пивоСвiтле нефiльтроване пивоСвiтле нефiльтроване пивоСвiтле нефiльтроване пивоСвiтле нефiльтроване пиво','Складова..\nСвiтле нефiльтроване пивоСвiтле нефiльтроване пиво','UA'),(27,'Свiтле','Свiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пиво\nСвiтле пиво\nСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пивоСвiтле пиво','Складова..','UA'),(28,'Светлое','Светлое пивоСветлое пивоСветлое пивоСветлое пиво\nСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пиво','Состав..\nСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пивоСветлое пиво','RUS'),(29,'Light','True light beer brewed with love and experience of the centuries!\nTrue light beer brewed with love and experience of the centuries!\nTrue light beer brewed with love and experience of the centuries!True light beer brewed with love and experience of the centuries!','Composition: warer, hope, etc\nTrue light beer brewed with love and experience of the centuries!','ENG'),(30,'Dark','Dark beer Dark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beerDark beer','Composition..\nDark beerDark beerDark beerDark beerDark beer','ENG'),(31,'Темное','Темное пиво Темное пивоТемное пивоТемное пивоТемное пиво Темное пиво Темное пивоТемное пивоТемное пивоТемное пивоТемное пиво Темное пивоТемное пивоТемное пивоТемное пивоТемное пиво Темное пивоТемное пивоТемное пивоТемное пивоТемное пиво Темное пивоТемное пивоТемное пивоТемное пивоТемное пиво Темное пивоТемное пивоТемное пивоТемное пиво','Состав..\nТемное пиво Темное пивоТемное пивоТемное пивоТемное пиво','RUS'),(32,'Темне','Темне пивоТемне пивоТемне пиво\nТемне пиво\nТемне пиво\nТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пивоТемне пиво','Складова..','UA'),(33,'night darkness','this is the most popular variety of dark beer in all around of the world!','Vol 5%','RUS'),(34,'night darkness','this is the most popular variety of dark beer in all around of the world!','Vol 5%','UA'),(35,'night darkness','this is the most popular variety of dark beer in all around of the world!','Vol 5%','ENG'),(47,'Темнео нефильтрованное','Темнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное\nТемнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное','Состав...\nТемнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное Темнео нефильтрованное','RUS'),(48,'Темне нефільтроване','темне нефільтроване\nтемне нефільтроване\nтемне нефільтроване\nтемне нефільтроване\nтемне нефільтроване\nтемне нефільтроване\nvтемне нефільтроване','Складова...','UA'),(49,'Unfiltered Dark','True alive unfiltered dark beer True alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beer','Composition\nTrue alive unfiltered dark beerTrue alive unfiltered dark beerTrue alive unfiltered dark beer','ENG'),(50,'Св. Патрик','Св. Патрик Ирландское зеленое Св. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленое','Состав...\nСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленоеСв. Патрик Ирландское зеленое','RUS'),(51,'Св. Патрік ','Св. Патрік зелене Ірландське пиво\nСв. Патрік зелене Ірландське пиво\nСв. Патрік зелене Ірландське пиво\nСв. Патрік зелене Ірландське пиво\nСв. Патрік зелене Ірландське пиво\nСв. Патрік зелене Ірландське пиво\nСв. Патрік зелене Ірландське пиво','Складова\nСв. Патрік зелене Ірландське пиво','UA'),(52,'St. Patrik Green','St. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beer','Composituioin....\nSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beerSt. Patrik Green irish style beer','ENG'),(53,'Гиннес','Настоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный Эль','Состав...\nНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный ЭльНастоящий Ирландский темный Эль','RUS'),(54,'Гінес','Справжній Ірландський темній ель\nСправжній Ірландський темній ель\nСправжній Ірландський темній ель\nСправжній Ірландський темній ель\nСправжній Ірландський темній ель\nСправжній Ірландський темній ель','Складова...\nСправжній Ірландський темній ель','UA'),(55,'Guinnes','Tue Irish Dark Ele Tue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark Ele','Composition...\nTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark EleTue Irish Dark Ele','ENG');
/*!40000 ALTER TABLE `descriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `image_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `path` varchar(255) NOT NULL,
  PRIMARY KEY (`image_id`),
  UNIQUE KEY `uni_img_name` (`name`,`path`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (21,'abstraction_liquid_form_86987_1920x1080.jpg','/pictures'),(18,'black_background_red_color_paint_explosion_burst_9844_1920x1080.jpg','/pictures'),(19,'card_light_sky_85057_1920x1080.jpg','/pictures'),(20,'landscape_tree_black_92070_1920x1080.jpg','/pictures'),(16,'slider-img-1.png','/pictures');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invalidTokens`
--

DROP TABLE IF EXISTS `invalidTokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invalidTokens` (
  `token` varchar(250) NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invalidTokens`
--

LOCK TABLES `invalidTokens` WRITE;
/*!40000 ALTER TABLE `invalidTokens` DISABLE KEYS */;
INSERT INTO `invalidTokens` VALUES ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJPd25lciIsImlzRW5hYmxlZCI6dHJ1ZSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfU1VQRVJfQURNSU4ifSx7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.KfRz6NTwasxvJCcLJTuRqSOpXB7bP1FvmEZv7uwZI_Y');
/*!40000 ALTER TABLE `invalidTokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prod_descs`
--

DROP TABLE IF EXISTS `prod_descs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prod_descs` (
  `prod_id` bigint(20) NOT NULL,
  `description_id` bigint(20) NOT NULL,
  PRIMARY KEY (`description_id`,`prod_id`),
  UNIQUE KEY `desc_id_UNIQUE` (`description_id`),
  KEY `fk_desc` (`description_id`),
  KEY `fk_prod` (`prod_id`),
  CONSTRAINT `fk_desc` FOREIGN KEY (`description_id`) REFERENCES `descriptions` (`desc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_prod` FOREIGN KEY (`prod_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prod_descs`
--

LOCK TABLES `prod_descs` WRITE;
/*!40000 ALTER TABLE `prod_descs` DISABLE KEYS */;
INSERT INTO `prod_descs` VALUES (11,21),(11,22),(11,23),(9,24),(9,25),(9,26),(2,27),(2,28),(2,29),(1,30),(1,31),(1,32),(15,47),(15,48),(15,49),(16,50),(16,51),(16,52),(17,53),(17,54),(17,55);
/*!40000 ALTER TABLE `prod_descs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `product_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_name_UNIQUE` (`product_name`),
  KEY `fk_product_type_idx` (`product_type`),
  CONSTRAINT `fk_product_type` FOREIGN KEY (`product_type`) REFERENCES `type` (`product_type_name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'dark','dark'),(2,'Light','light'),(9,'BLONDE','light unfiltered'),(11,'black light beer','semi dark'),(15,'Unfiltered Dark','unfiltered dark'),(16,'St. Patrik','green irish ale'),(17,'Guinnes','black irish ale');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uni_username_role` (`role`,`username`),
  KEY `fk_username_idx` (`username`),
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'Ander','ROLE_ADMIN'),(4,'DSADF','ROLE_ADMIN'),(5,'DSADFF','ROLE_ADMIN'),(6,'NEWTEST','ROLE_ADMIN'),(14,'Owner','ROLE_ADMIN'),(15,'Owner','ROLE_SUPER_ADMIN'),(9,'ADMIN','USER_ADMIN'),(7,'Coolest','USER_ADMIN'),(10,'TEST','USER_ADMIN'),(11,'TEST','USER_GUEST'),(8,'Coolest','USER_SUPER_ADMIN'),(13,'USER','USER_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translations`
--

DROP TABLE IF EXISTS `translations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `translations` (
  `translation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `translation` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`translation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `translations`
--

LOCK TABLES `translations` WRITE;
/*!40000 ALTER TABLE `translations` DISABLE KEYS */;
INSERT INTO `translations` VALUES (51,'новая статья','RUS','новая статья'),(52,'нова стаття','UA','це нова стаття'),(53,'new test qwe','ENG','this is a new article'),(54,'новая статья','RUS','новая статья'),(55,'нова стаття','UA','це нова стаття'),(56,'new test qwe','ENG','this is a new article'),(57,'','UA','Україньська УкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньська\nУкраїньська УкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньська\nУкраїньська УкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньська\nУкраїньська УкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньська\nУкраїньська УкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньськаУкраїньська\nУкраїньська'),(58,'','ENG','LOREM ISPUM LOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUM\nLOREM ISPUM LOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUM\nLOREM ISPUM LOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUMLOREM ISPUM\n\n'),(59,'','RUS','Русский РусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийР history-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-info\nРусский РусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийР history-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-info\nРусский РусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийР history-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-info\nРусский РусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийР history-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-info\nРусский РусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийРусскийР history-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-infohistory-info');
/*!40000 ALTER TABLE `translations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translations_article`
--

DROP TABLE IF EXISTS `translations_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `translations_article` (
  `translation_id` bigint(20) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  PRIMARY KEY (`translation_id`,`article_id`),
  UNIQUE KEY `translation_id_UNIQUE` (`translation_id`),
  KEY `fk_translation` (`translation_id`),
  KEY `fk_article` (`article_id`),
  CONSTRAINT `fk_article` FOREIGN KEY (`article_id`) REFERENCES `articles` (`article_id`),
  CONSTRAINT `fk_translation` FOREIGN KEY (`translation_id`) REFERENCES `translations` (`translation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `translations_article`
--

LOCK TABLES `translations_article` WRITE;
/*!40000 ALTER TABLE `translations_article` DISABLE KEYS */;
INSERT INTO `translations_article` VALUES (51,18),(52,18),(53,18),(54,19),(55,19),(56,19),(57,20),(58,20),(59,20);
/*!40000 ALTER TABLE `translations_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type` (
  `product_type_name` varchar(45) NOT NULL,
  `product_type_ico` varchar(255) NOT NULL,
  PRIMARY KEY (`product_type_name`),
  UNIQUE KEY `product_type_name` (`product_type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES ('black irish ale','/brewery/img/black-beer.png'),('dark','/brewery/img/dark-beer.png'),('green irish ale','/brewery/img/green-beer.png'),('light','/brewery/img/light-beer.png'),('light unfiltered','/brewery/img/unfiltered-beer.png'),('red','/brewery/img/red-beer.png'),('semi dark','/brewery/img/red-beer.png'),('unfiltered dark','/brewery/img/unfiltered-dark-beer.png');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('ADMIN','123',1),('Ander','adqwmin',1),('Coolest','123',1),('DSADF','adqwmin',1),('DSADFF','123',1),('NEWTEST','123',0),('OstapVasilenko','admin',1),('OV','TESTR',0),('Owner','admin',1),('TEST','TESTR',1),('USER','123',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-04 19:05:35
