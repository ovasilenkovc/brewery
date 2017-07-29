-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: brewery
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
  `title` varchar(45) NOT NULL,
  `post_date` date DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (15,'history','2017-08-07');
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descriptions`
--

LOCK TABLES `descriptions` WRITE;
/*!40000 ALTER TABLE `descriptions` DISABLE KEYS */;
INSERT INTO `descriptions` VALUES (7,'blonde light beer','this is the most popular variety of beer in all around of the world! world! world! world! world! world! world! world! world! world! world! world! world! world! world! world! ','world! world! world! ','ENG'),(9,'Интенсивно темнок','Темное пиво','В состав входят следующие ингридиенты...','RUS'),(10,'dark','sthshddfjghjdhfghdffdndfdfmfmuyfuymfuyefgseg','ddndnhhggggggggggnmnmbnzsgbdbcvbnxnvbnzbdfbzdb','ENG'),(11,'light','SD:IFHlbslgkbjsn;klsrbn;srtkbnstk;jlbnsrkl;tbjnsrtkl;jbn st;bjo','smnkhsr6o[ihrtmaerlma\nsrt','ENG'),(12,'lager','LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER LAGER ','COMPOSITION','ENG'),(14,'Ginnes','awgaergsergsergsergsergsergefgb\nsgsgzsdrghzsetgherhsethhsrthrthrth\naehsethsrthsrthsrthsrthsxfghdfghfg\neahsrthsrthsrthrthrthdrth\nserthsrthsrthsrthsrt','aerhsthdrthdrthdrthdrthdrt\nawgsergergsergergssergsergsergserge','ENG'),(15,'Blonde','Lorem ispum; Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;','Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;Lorem ispum;','ENG'),(16,'Semi dark','loren ipsum; loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;','loren ipsum; loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;loren ipsum;','ENG'),(22,'темне','пкпукпывапук','укпукпуфыкпявапук','UA'),(23,'Світле','ЯУВПРІЕияіеолдитуежилджівкеиоджфуктщжиуіе','уврікетрпдьтлікджлтьікнждт','UA'),(24,'Світле нефільтроване','цпфукпіркерпкерфукмицкфмфкмфіукиіів','фкифуеиіваиваіпмфукмфіукмиуи','UA'),(25,'Світлий лагер','уедиікеьитдєлзікеьизєіщкеьзкеьитщікеиваьліиджваьлизхщіеьлизіуеьизіуеиьщікджльлієплджкьи','фузпмщулизжіекбизжєлбзікебиєікебди','UA'),(26,'Ginnes','увиеікевкамиікеиікеімииамтва\nувиеікевкамиікеиікеімииамтва\nувиеікевкамиікеиікеімииамтва\nувиеікевкамиікеиікеімииамтваувиеікевкамиікеиікеімииамтва\nувиеікевкамиікеиікеімииамтваувиеікевкамиікеиікеімииамтва','увиеікевкамиікеиікеімииамтваувиеікевкамиікеиікеімииамтваувиеікевкамиікеиікеімииамтва','UA'),(27,'Нефільтроване','лфуьрлщтькеджльафєдпльапшофукщьлфвамиджфвьиджіь джлваьидєівалиьдєваж','фупьлєеиулджьиєдфуклиьфукджиьбфукджибу','UA'),(28,'Напів темне','фуджпиьфулдмиуьємдьіваємбьявамбьва\nфуджпиьфулдмиуьємдьіваємбьявамбьвафуджпиьфулдмиуьємдьіваємбьявамбьвафуджпиьфулдмиуьємдьіваємбьявамбьва\nфуджпиьфулдмиуьємдьіваємбьявамбьва','фуджпиьфулдмиуьємдьіваємбьявамбьвафуджпиьфулдмиуьємдьіваємбьявамбьва','UA'),(29,'Светлое','упфуклиьфуджлиьуфыеджильваидьывадилжьываилдьываджиьлвыаджиьлывджлиьываджильываилджьываиджьлдлвадж','Состав..','RUS'),(30,'Белое нефильтрованное','фукруеиузльифуклщиьфіуеджлитілдотівлди\nфукруеиузльифуклщиьфіуеджлитілдотівлдифукруеиузльифуклщиьфіуеджлитілдотівлдифукруеиузльифуклщиьфіуеджлитілдотівлди','фукруеиузльифуклщиьфіуеджлитілдотівлди','RUS'),(31,'Лагер','фуподфутмиджфукьиджлуфьыилдуытл','фужолдитулджукиофутдклиофукджли','RUS'),(32,'Ginnes','фуирлдьфуджильуджилфуьиджлфуьилджфуткилдж','фукджолтфуклджитфукждиьлдфук','RUS'),(33,'Блонде','улдиотфулиофуктлдифуктлдифукио\nулдиотфулиофуктлдифуктлдифукиоулдиотфулиофуктлдифуктлдифукиоулдиотфулиофуктлдифуктлдифукиоулдиотфулиофуктлдифуктлдифукиоулдиотфулиофуктлдифуктлдифукиоулдиотфулиофуктлдифуктлдифукиоулдиотфулиофуктлдифуктлдифукио','октифуклдитфуклдитфуклдио','RUS'),(34,'Полутемное','фудльфукджильфукджильфукджильуфкждифудльфукджильфукджильфукджильуфкждилфудльфукджильфукджильфукджильуфкждилфудльфукджильфукджильфукджильуфкждилфудльфукджильфукджильфукджильуфкждилфудльфукджильфукджильфукджильуфкждилл','джфулиьфуджлиьфукджлиьукд','RUS');
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
  `name` varchar(350) NOT NULL,
  `path` varchar(700) NOT NULL,
  PRIMARY KEY (`image_id`),
  UNIQUE KEY `uni_img_name` (`name`,`path`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (33,'558fcf8ca0d2e742d7a8cdc3eada9f74.jpg','/pictures'),(30,'fantasy-images-2560x1440-18.jpg','/pictures'),(34,'stella_artois_beer_alcohol_glass_108957_2560x1440.jpg','/pictures');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invalidtokens`
--

DROP TABLE IF EXISTS `invalidtokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invalidtokens` (
  `token` varchar(250) NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invalidtokens`
--

LOCK TABLES `invalidtokens` WRITE;
/*!40000 ALTER TABLE `invalidtokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `invalidtokens` ENABLE KEYS */;
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
INSERT INTO `prod_descs` VALUES (9,7),(1,9),(1,10),(2,11),(13,12),(15,14),(16,15),(17,16),(1,22),(2,23),(9,24),(13,25),(15,26),(16,27),(17,28),(2,29),(9,30),(13,31),(15,32),(16,33),(17,34);
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
INSERT INTO `product` VALUES (1,'dark','dark'),(2,'light','light'),(9,'blonde light beer','unfiltered-light'),(13,'lager','light'),(15,'Ginnes','dark'),(16,'Blonde','unfiltered-light'),(17,'Semi dark','semi-dark');
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
INSERT INTO `roles` VALUES (15,'Owner','ROLE_ADMIN'),(14,'Owner','ROLE_SUPER_ADMIN');
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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `translations`
--

LOCK TABLES `translations` WRITE;
/*!40000 ALTER TABLE `translations` DISABLE KEYS */;
INSERT INTO `translations` VALUES (44,'Наша история','RUS','Наша история Наша история Наша история Наша история Наша история Наша история Наша \nНаша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история Наша история'),(45,'Наша історія','UA','Наша Історія Наша Історія Наша Історія Наша Історія Наша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша ІсторіяНаша Історія Наша Історія Наша Історія Наша Історія'),(46,'Our Story','ENG','this is a new article lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. \nthis is a new article lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. this is a new article lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem ispum. lorem');
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
INSERT INTO `translations_article` VALUES (44,15),(45,15),(46,15);
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
INSERT INTO `type` VALUES ('dark','/brewery/img/dark-beer.png'),('infiltered-dark','/brewery/img/dark-beer.png'),('light','/brewery/img/light-beer.png'),('red','/brewery/img/red-beer.png'),('semi-dark','/brewery/img/red-beer.png'),('unfiltered-light','/brewery/img/beer-wheaten.png');
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
INSERT INTO `users` VALUES ('Owner','admin',1);
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

-- Dump completed on 2017-07-29 21:46:51
