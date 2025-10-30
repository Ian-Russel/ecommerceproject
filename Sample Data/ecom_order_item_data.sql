<<<<<<< HEAD
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: ecom
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `order_item_data`
--

DROP TABLE IF EXISTS `order_item_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_image` varchar(255) DEFAULT NULL,
  `product_price` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `product_color` varchar(50) DEFAULT NULL,
  `product_size` varchar(50) DEFAULT NULL,
  `product_brand` varchar(100) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `status` varchar(50) DEFAULT 'PENDING',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `FK36f0qt3r3mpu1dvlqimqpjqxv` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item_data`
--

LOCK TABLES `order_item_data` WRITE;
/*!40000 ALTER TABLE `order_item_data` DISABLE KEYS */;
INSERT INTO `order_item_data` VALUES (3,14,9,'soundar2',NULL,350.00,1,350.00,'','','',2,'PENDING','2025-10-25 22:35:44','2025-10-25 22:35:44'),(4,14,8,'soundar2',NULL,350.00,1,350.00,'','','',2,'PENDING','2025-10-25 22:35:44','2025-10-25 22:35:44'),(5,15,3,'Cardbury',NULL,10.00,1,10.00,'','','',2,'PENDING','2025-10-25 22:41:33','2025-10-25 22:41:33'),(6,15,4,'Milo',NULL,120.00,1,120.00,'','','',2,'PENDING','2025-10-25 22:41:33','2025-10-25 22:41:33'),(7,16,4,'Milo',NULL,120.00,1,120.00,'','','',2,'PENDING','2025-10-26 12:35:16','2025-10-26 12:35:16'),(8,16,3,'Cardbury',NULL,10.00,1,10.00,'','','',2,'PENDING','2025-10-26 12:35:16','2025-10-26 12:35:16'),(9,17,3,'Cardbury',NULL,10.00,1,10.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59'),(10,17,2,'Alaska',NULL,30.00,1,30.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59'),(11,17,1,'soundar2',NULL,350.00,1,350.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59'),(12,17,4,'Milo',NULL,120.00,1,120.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59');
/*!40000 ALTER TABLE `order_item_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-29 20:43:30
=======
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: ecom
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `order_item_data`
--

DROP TABLE IF EXISTS `order_item_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_image` varchar(255) DEFAULT NULL,
  `product_price` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `product_color` varchar(50) DEFAULT NULL,
  `product_size` varchar(50) DEFAULT NULL,
  `product_brand` varchar(100) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `status` varchar(50) DEFAULT 'PENDING',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `FK36f0qt3r3mpu1dvlqimqpjqxv` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item_data`
--

LOCK TABLES `order_item_data` WRITE;
/*!40000 ALTER TABLE `order_item_data` DISABLE KEYS */;
INSERT INTO `order_item_data` VALUES (3,14,9,'soundar2',NULL,350.00,1,350.00,'','','',2,'PENDING','2025-10-25 22:35:44','2025-10-25 22:35:44'),(4,14,8,'soundar2',NULL,350.00,1,350.00,'','','',2,'PENDING','2025-10-25 22:35:44','2025-10-25 22:35:44'),(5,15,3,'Cardbury',NULL,10.00,1,10.00,'','','',2,'PENDING','2025-10-25 22:41:33','2025-10-25 22:41:33'),(6,15,4,'Milo',NULL,120.00,1,120.00,'','','',2,'PENDING','2025-10-25 22:41:33','2025-10-25 22:41:33'),(7,16,4,'Milo',NULL,120.00,1,120.00,'','','',2,'PENDING','2025-10-26 12:35:16','2025-10-26 12:35:16'),(8,16,3,'Cardbury',NULL,10.00,1,10.00,'','','',2,'PENDING','2025-10-26 12:35:16','2025-10-26 12:35:16'),(9,17,3,'Cardbury',NULL,10.00,1,10.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59'),(10,17,2,'Alaska',NULL,30.00,1,30.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59'),(11,17,1,'soundar2',NULL,350.00,1,350.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59'),(12,17,4,'Milo',NULL,120.00,1,120.00,'','','',2,'PENDING','2025-10-26 12:47:59','2025-10-26 12:47:59');
/*!40000 ALTER TABLE `order_item_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-29 20:43:30
>>>>>>> d27523e3bedb06f3d5ed78656cdb02a4d25823cb
