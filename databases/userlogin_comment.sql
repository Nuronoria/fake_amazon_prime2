-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: userlogin
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `Comment_ID` int NOT NULL AUTO_INCREMENT,
  `User_ID` int NOT NULL,
  `Movie_ID` int NOT NULL,
  `Comment_text` varchar(500) NOT NULL,
  `Comment_rating` double NOT NULL,
  `Comment_date` date NOT NULL,
  PRIMARY KEY (`Comment_ID`),
  UNIQUE KEY `Comment_ID_UNIQUE` (`Comment_ID`),
  KEY `movie_idx` (`Movie_ID`),
  KEY `user_idx` (`User_ID`),
  CONSTRAINT `movie` FOREIGN KEY (`Movie_ID`) REFERENCES `movie` (`Movie_ID`),
  CONSTRAINT `user` FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `comment_chk_1` CHECK (((`Comment_rating` >= 1) and (`Comment_rating` <= 5)))
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,2,1,'\"Super Drama! Tolle Effekte und empfehlenswert!\"',5,'2024-04-01'),(2,2,2,'\"Super Film! Tolle Effekte und spannende Story. Absolut empfehlenswert!\"',5,'2024-06-19'),(3,3,2,'\"Der Film war insgesamt in Ordnung. Die Handlung hatte einige interessante Momente.\"',3,'2024-05-23'),(4,4,2,'\"Leider hat mich der Film enttäuscht. Die Handlung war vorhersehbar und es fehlte an Spannung.\"',1,'2024-04-11'),(5,3,1,'\"War super kindsch und ich mag das Ende nicht.\"',2,'2024-04-03'),(6,4,1,'\"Es war so traurig, dass ich weinen musste.\"',4,'2024-04-04'),(7,2,3,'\"Ein Meisterwerk! Fesselnde Handlung und großartige Schauspieler. 5 Sterne von 5. Absolut sehenswert!\"',5,'2022-02-03'),(8,3,3,'\"Sehr unterhaltsam und gut gemacht, aber ein paar Schwächen.  Empfehlenswert!\"',4,'2023-03-04'),(9,4,3,'\"Spannend und gut gemacht, aber nicht perfekt.\"',3,'2023-05-08'),(10,2,4,'\n\"Packende Handlung und starke Performances. Kleine Mängel, aber sehenswert!\"',4,'2023-06-09'),(11,3,4,'\n\"Enttäuschend und langatmig. Keine fesselnde Story. Nicht empfehlenswert.\"',1,'2024-01-02'),(12,4,4,'\"Gute Ansätze, aber nicht durchgehend spannend. Kann man sich anschauen.\"',3,'2023-08-09'),(15,1,1,'\"nicht so meins.. hätte besser sein können.\"',2,'2024-06-20'),(18,5,1,'\"Einfach schauen!!\"',5,'2024-06-27');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-30 19:58:10
