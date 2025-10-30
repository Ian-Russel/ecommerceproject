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
-- Table structure for table `product_data`
--

DROP TABLE IF EXISTS `product_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_data` (
  `id` int NOT NULL,
  `categoryName` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `imageFile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `unitOfMeasure` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `customerId` int NOT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `orderId` int NOT NULL,
  `productCategoryName` varchar(255) DEFAULT NULL,
  `productDescription` varchar(255) DEFAULT NULL,
  `productId` int NOT NULL,
  `productImageFile` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productUnitOfMeasure` varchar(255) DEFAULT NULL,
  `quantity` double NOT NULL,
  `status` varchar(50) DEFAULT 'Active',
  `category_name` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `image_file` varchar(255) DEFAULT NULL,
  `style` varchar(255) DEFAULT NULL,
  `unit_of_measure` varchar(255) DEFAULT NULL,
  `additional_images` varchar(1000) DEFAULT NULL,
  `attributes` json DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `discount_percentage` int DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `is_best_seller` bit(1) DEFAULT NULL,
  `is_featured` bit(1) DEFAULT NULL,
  `is_new_arrival` bit(1) DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `review_count` int DEFAULT NULL,
  `sku` varchar(100) DEFAULT NULL,
  `specifications` json DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `sub_category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_evwv8hkqi2x2tq96wr2qb45ni` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_data`
--

LOCK TABLES `product_data` WRITE;
/*!40000 ALTER TABLE `product_data` DISABLE KEYS */;
INSERT INTO `product_data` VALUES (1,'Animal','Meow meow meow','soundar2','Cat','5000','piece',NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,'Cat','Orange','Cat',NULL,'',NULL,NULL,'Cat',50,'Male',_binary '',_binary '',_binary '',NULL,'Shirt',4.5,1,NULL,NULL,0,NULL),(2,NULL,'A premium quality product','soundar2','Berkin','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','Berkin','White','A',NULL,'Pair',NULL,NULL,'Berkin',NULL,'Unisex',_binary '',_binary '',NULL,NULL,'Sandal',4.7,5000,NULL,NULL,1000,NULL),(3,NULL,'A fantastic yellow sandal','soundar2','W15','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCW','Yellow','B',NULL,'Pair',NULL,NULL,'Clasic',9,'Women',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.7,9000,NULL,NULL,1000,NULL),(4,NULL,'A beautiful looking footwear','soundar2','LS05','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCL','Brown','C',NULL,'Pair',NULL,NULL,'Lee',NULL,'Unisex',_binary '\0',_binary '',NULL,NULL,'Sandal',4.8,57,NULL,NULL,5000,NULL),(5,NULL,'A modest looking slipper','soundar2','W17','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCW','White','D',NULL,'Pair',NULL,NULL,'Classic',NULL,'Women',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.7,3000,NULL,NULL,1000,NULL),(6,NULL,'A cool looking slipper','soundar2','M08','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCM','Black','E',NULL,'Pair',NULL,NULL,'Classic',9,'Men',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.8,10000,NULL,NULL,1,NULL),(7,NULL,'A dapper looking slipper','soundar2','W26','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCW','Red','F',NULL,'Pair',NULL,NULL,'Classic',9,'Women',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.8,5000,NULL,NULL,1000,NULL);
/*!40000 ALTER TABLE `product_data` ENABLE KEYS */;
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
-- Table structure for table `product_data`
--

DROP TABLE IF EXISTS `product_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_data` (
  `id` int NOT NULL,
  `categoryName` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `imageFile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `unitOfMeasure` varchar(255) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `customerId` int NOT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `orderId` int NOT NULL,
  `productCategoryName` varchar(255) DEFAULT NULL,
  `productDescription` varchar(255) DEFAULT NULL,
  `productId` int NOT NULL,
  `productImageFile` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `productUnitOfMeasure` varchar(255) DEFAULT NULL,
  `quantity` double NOT NULL,
  `status` varchar(50) DEFAULT 'Active',
  `category_name` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `image_file` varchar(255) DEFAULT NULL,
  `style` varchar(255) DEFAULT NULL,
  `unit_of_measure` varchar(255) DEFAULT NULL,
  `additional_images` varchar(1000) DEFAULT NULL,
  `attributes` json DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `discount_percentage` int DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `is_best_seller` bit(1) DEFAULT NULL,
  `is_featured` bit(1) DEFAULT NULL,
  `is_new_arrival` bit(1) DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `review_count` int DEFAULT NULL,
  `sku` varchar(100) DEFAULT NULL,
  `specifications` json DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `sub_category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_evwv8hkqi2x2tq96wr2qb45ni` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_data`
--

LOCK TABLES `product_data` WRITE;
/*!40000 ALTER TABLE `product_data` DISABLE KEYS */;
INSERT INTO `product_data` VALUES (1,'Animal','Meow meow meow','soundar2','Cat','5000','piece',NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,'Cat','Orange','Cat',NULL,'',NULL,NULL,'Cat',50,'Male',_binary '',_binary '',_binary '',NULL,'Shirt',4.5,1,NULL,NULL,0,NULL),(2,NULL,'A premium quality product','soundar2','Berkin','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','Berkin','White','A',NULL,'Pair',NULL,NULL,'Berkin',NULL,'Unisex',_binary '',_binary '',NULL,NULL,'Sandal',4.7,5000,NULL,NULL,1000,NULL),(3,NULL,'A fantastic yellow sandal','soundar2','W15','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCW','Yellow','B',NULL,'Pair',NULL,NULL,'Clasic',9,'Women',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.7,9000,NULL,NULL,1000,NULL),(4,NULL,'A beautiful looking footwear','soundar2','LS05','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCL','Brown','C',NULL,'Pair',NULL,NULL,'Lee',NULL,'Unisex',_binary '\0',_binary '',NULL,NULL,'Sandal',4.8,57,NULL,NULL,5000,NULL),(5,NULL,'A modest looking slipper','soundar2','W17','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCW','White','D',NULL,'Pair',NULL,NULL,'Classic',NULL,'Women',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.7,3000,NULL,NULL,1000,NULL),(6,NULL,'A cool looking slipper','soundar2','M08','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCM','Black','E',NULL,'Pair',NULL,NULL,'Classic',9,'Men',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.8,10000,NULL,NULL,1,NULL),(7,NULL,'A dapper looking slipper','soundar2','W26','275.00',NULL,NULL,0,NULL,NULL,0,NULL,NULL,0,NULL,NULL,NULL,0,'Active','TSCW','Red','F',NULL,'Pair',NULL,NULL,'Classic',9,'Women',_binary '\0',_binary '\0',NULL,NULL,'Sandal',4.8,5000,NULL,NULL,1000,NULL);
/*!40000 ALTER TABLE `product_data` ENABLE KEYS */;
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
