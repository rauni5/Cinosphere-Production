-- phpMyAdmin SQL Dump
-- Cinosphere Database — corrected & updated
-- Today: 2026-05-19
-- Changes made:
--   1. Showtime dates updated to 2026-05-19 through 2026-05-25 (all accessible from today)
--   2. Seats fully regenerated for every screen matching total_capacity:
--        Seats  1– 90  → Standard  (rows A–E, 18 per row)
--        Seats 91–180  → Premium   (rows F–J, 18 per row)
--        Seats 181–270 → VIP       (rows K–O, 18 per row)  [only for screens with capacity > 180]
--        seat_number is a plain integer string; row_number is the row letter
--   3. Ticket seat_ids and showtime_ids updated to match new seats/showtimes
--   4. Ticket ticket_type normalised to Standard / Premium / VIP
--   5. Payment dates corrected to match their booking dates
--   6. user_id=2 (user) is_active fixed from 0 → 1 so the account can log in
--   7. Showtimes expanded to cover all 4 NOW_SHOWING movies across all 5 screens
--      for the next 7 days with morning / afternoon / evening slots

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- ============================================================
-- Database: `cinosphere`
-- ============================================================

-- ────────────────────────────────────────────────────────────
-- Table structure
-- ────────────────────────────────────────────────────────────

CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `booking_date` date NOT NULL,
  `booking_time` time NOT NULL,
  `booking_status` varchar(20) NOT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `booking_channel` varchar(30) NOT NULL,
  `loyalty_points_earned` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL CHECK (`rating` between 1 and 5),
  `description` text NOT NULL,
  `feedback_date` date NOT NULL,
  `feedback_time` time NOT NULL,
  `feedback_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `membership` (
  `membership_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `membership_type` varchar(30) NOT NULL,
  `membership_status` varchar(20) NOT NULL,
  `total_loyalty_points` int(11) NOT NULL,
  `discount_percentage` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `movie` (
  `movie_id` int(11) NOT NULL,
  `movie_name` varchar(100) NOT NULL,
  `duration` int(11) NOT NULL,
  `director` varchar(100) NOT NULL,
  `genre` varchar(50) NOT NULL,
  `movie_language` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `release_date` date NOT NULL,
  `movie_status` varchar(20) NOT NULL,
  `age_rating` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `payment_method` varchar(30) NOT NULL,
  `payment_amount` decimal(10,2) NOT NULL,
  `payment_date` date NOT NULL,
  `payment_time` time NOT NULL,
  `payment_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `screen` (
  `screen_id` int(11) NOT NULL,
  `theatre_id` int(11) NOT NULL,
  `screen_name` varchar(50) NOT NULL,
  `screen_type` varchar(30) NOT NULL,
  `screen_status` varchar(20) NOT NULL,
  `total_capacity` int(11) NOT NULL,
  `base_price` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `seat` (
  `seat_id` int(11) NOT NULL,
  `screen_id` int(11) NOT NULL,
  `seat_number` varchar(10) NOT NULL,
  `row_number` varchar(10) NOT NULL,
  `seat_type` varchar(20) NOT NULL,
  `seat_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `showtime` (
  `showtime_id` int(11) NOT NULL,
  `screen_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `show_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `show_status` varchar(20) NOT NULL,
  `show_type` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `theatre` (
  `theatre_id` int(11) NOT NULL,
  `theatre_name` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `total_screens` int(11) NOT NULL,
  `theatre_status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `ticket` (
  `ticket_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `showtime_id` int(11) NOT NULL,
  `seat_id` int(11) NOT NULL,
  `ticket_type` varchar(30) NOT NULL,
  `ticket_status` varchar(20) NOT NULL,
  `issue_date` date NOT NULL,
  `ticket_price` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `hash_password` varchar(255) NOT NULL,
  `registration_date` date NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `user_role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- ────────────────────────────────────────────────────────────
-- USERS
-- Fix: user_id=2 is_active was 0 → corrected to 1
-- ────────────────────────────────────────────────────────────

INSERT INTO `users` (`user_id`, `first_name`, `last_name`, `username`, `email`, `date_of_birth`, `gender`, `hash_password`, `registration_date`, `is_active`, `user_role`) VALUES
(2, 'Aditya',  'Raut', 'user',    'user@gmail.com',       '2004-05-02', 'other', '$2a$10$FN0uBcB3jJpeDOoMKPjrsOu9G/0qsQb4pDyjvtUTx5ldJRNRV7Km2', '2026-05-03', 1, 'CUSTOMER'),
(3, 'Raunit',  'Giri', 'admin',   'admin@gmail.com',      '2002-05-02', 'other', '$2a$10$jQL7T18UfniO7u.ww89tveroj6aErLvP.6Gd.lYIQe5C7Enye8hN6', '2026-05-03', 1, 'ADMIN'),
(4, 'Raunit',  'Giri', 'RAWunit', 'raunit06@gmail.com',   '2003-04-27', 'male',  '$2a$10$0o00Bpdaq9L5o4xn49MpaeNSnXgLgNWrhqgs0fpVFexHSsTKzW5Ea', '2026-05-04', 1, 'CUSTOMER');


-- ────────────────────────────────────────────────────────────
-- THEATRE
-- ────────────────────────────────────────────────────────────

INSERT INTO `theatre` (`theatre_id`, `theatre_name`, `city`, `email`, `contact_number`, `total_screens`, `theatre_status`) VALUES
(1, 'Cinosphere Prime',   'Kathmandu', 'prime@cinosphere.com',   '9849832584', 3, 'active'),
(2, 'Cinosphere Central', 'Pokhara',   'central@cinosphere.com', '9849832584', 2, 'active');


-- ────────────────────────────────────────────────────────────
-- SCREEN
-- Capacities kept as-is; total_capacity must equal the
-- number of seat rows generated below.
--   Screen 1 (IMAX, 150)      → seats 1–150  (rows A–H, some rows partial)
--   Screen 2 (4DX, 108)       → seats 1–108  (rows A–F, 18/row) — adjusted from 100
--   Screen 3 (Standard, 198)  → seats 1–198  (rows A–K, 18/row) — adjusted from 200
--   Screen 4 (IMAX, 126)      → seats 1–126  (rows A–G, 18/row) — adjusted from 120
--   Screen 5 (Standard, 180)  → seats 1–180  (rows A–J, 18/row)
-- Capacities updated to exact multiples of 18 for clean row layout.
-- ────────────────────────────────────────────────────────────

INSERT INTO `screen` (`screen_id`, `theatre_id`, `screen_name`, `screen_type`, `screen_status`, `total_capacity`, `base_price`) VALUES
(1, 1, 'Screen 1', 'IMAX',     'active', 162, 500.00),
(2, 1, 'Screen 2', '4DX',      'active', 108, 450.00),
(3, 1, 'Screen 3', 'Standard', 'active', 198, 250.00),
(4, 2, 'Screen 1', 'IMAX',     'active', 126, 480.00),
(5, 2, 'Screen 2', 'Standard', 'active', 180, 230.00);


-- ────────────────────────────────────────────────────────────
-- SEATS
--
-- Rules applied:
--   seat_number = plain integer 1, 2, 3 … (stored as varchar)
--   row_number  = letter A, B, C …
--   18 seats per row
--   Seats  1– 90  → Standard   (rows A–E)
--   Seats 91–180  → Premium    (rows F–J)
--   Seats 181+    → VIP        (rows K+)
--
-- Screen 1 (162 seats) → rows A–E Standard (90), F–J Premium (90 — but cap 162 so 72 premium)
--   Actually: 162 = 9 rows × 18. Rows A–E Standard, F–H Premium, I–J VIP would give: 
--   Standard 90 (A–E), Premium 54 (F–H), VIP 18 (I) = 162. Let's keep the tier logic clean:
--   1–90 Standard, 91–162 Premium (no VIP in screens with cap ≤180, VIP only if cap > 180+)
--   To keep booking tier rule: 1–90 Std, 91–180 Prem, 181+ VIP
--   Screen 1: 162 seats → 90 Standard + 72 Premium
--   Screen 2: 108 seats → 90 Standard + 18 Premium
--   Screen 3: 198 seats → 90 Standard + 90 Premium + 18 VIP
--   Screen 4: 126 seats → 90 Standard + 36 Premium
--   Screen 5: 180 seats → 90 Standard + 90 Premium
-- ────────────────────────────────────────────────────────────

INSERT INTO `seat` (`seat_id`, `screen_id`, `seat_number`, `row_number`, `seat_type`, `seat_status`) VALUES

-- ── Screen 1 (screen_id=1, 162 seats, IMAX) ────────────────
-- Standard: seats 1–90 (rows A–E)
(1,1,'1','A','Standard','available'),(2,1,'2','A','Standard','available'),(3,1,'3','A','Standard','available'),(4,1,'4','A','Standard','available'),(5,1,'5','A','Standard','available'),(6,1,'6','A','Standard','available'),(7,1,'7','A','Standard','available'),(8,1,'8','A','Standard','available'),(9,1,'9','A','Standard','available'),(10,1,'10','A','Standard','available'),(11,1,'11','A','Standard','available'),(12,1,'12','A','Standard','available'),(13,1,'13','A','Standard','available'),(14,1,'14','A','Standard','available'),(15,1,'15','A','Standard','available'),(16,1,'16','A','Standard','available'),(17,1,'17','A','Standard','available'),(18,1,'18','A','Standard','available'),
(19,1,'19','B','Standard','available'),(20,1,'20','B','Standard','available'),(21,1,'21','B','Standard','available'),(22,1,'22','B','Standard','available'),(23,1,'23','B','Standard','available'),(24,1,'24','B','Standard','available'),(25,1,'25','B','Standard','available'),(26,1,'26','B','Standard','available'),(27,1,'27','B','Standard','available'),(28,1,'28','B','Standard','available'),(29,1,'29','B','Standard','available'),(30,1,'30','B','Standard','available'),(31,1,'31','B','Standard','available'),(32,1,'32','B','Standard','available'),(33,1,'33','B','Standard','available'),(34,1,'34','B','Standard','available'),(35,1,'35','B','Standard','available'),(36,1,'36','B','Standard','available'),
(37,1,'37','C','Standard','available'),(38,1,'38','C','Standard','available'),(39,1,'39','C','Standard','available'),(40,1,'40','C','Standard','available'),(41,1,'41','C','Standard','available'),(42,1,'42','C','Standard','available'),(43,1,'43','C','Standard','available'),(44,1,'44','C','Standard','available'),(45,1,'45','C','Standard','available'),(46,1,'46','C','Standard','available'),(47,1,'47','C','Standard','available'),(48,1,'48','C','Standard','available'),(49,1,'49','C','Standard','available'),(50,1,'50','C','Standard','available'),(51,1,'51','C','Standard','available'),(52,1,'52','C','Standard','available'),(53,1,'53','C','Standard','available'),(54,1,'54','C','Standard','available'),
(55,1,'55','D','Standard','available'),(56,1,'56','D','Standard','available'),(57,1,'57','D','Standard','available'),(58,1,'58','D','Standard','available'),(59,1,'59','D','Standard','available'),(60,1,'60','D','Standard','available'),(61,1,'61','D','Standard','available'),(62,1,'62','D','Standard','available'),(63,1,'63','D','Standard','available'),(64,1,'64','D','Standard','available'),(65,1,'65','D','Standard','available'),(66,1,'66','D','Standard','available'),(67,1,'67','D','Standard','available'),(68,1,'68','D','Standard','available'),(69,1,'69','D','Standard','available'),(70,1,'70','D','Standard','available'),(71,1,'71','D','Standard','available'),(72,1,'72','D','Standard','available'),
(73,1,'73','E','Standard','available'),(74,1,'74','E','Standard','available'),(75,1,'75','E','Standard','available'),(76,1,'76','E','Standard','available'),(77,1,'77','E','Standard','available'),(78,1,'78','E','Standard','available'),(79,1,'79','E','Standard','available'),(80,1,'80','E','Standard','available'),(81,1,'81','E','Standard','available'),(82,1,'82','E','Standard','available'),(83,1,'83','E','Standard','available'),(84,1,'84','E','Standard','available'),(85,1,'85','E','Standard','available'),(86,1,'86','E','Standard','available'),(87,1,'87','E','Standard','available'),(88,1,'88','E','Standard','available'),(89,1,'89','E','Standard','available'),(90,1,'90','E','Standard','available'),
-- Premium: seats 91–162 (rows F–I, 72 seats = 4 rows)
(91,1,'91','F','Premium','available'),(92,1,'92','F','Premium','available'),(93,1,'93','F','Premium','available'),(94,1,'94','F','Premium','available'),(95,1,'95','F','Premium','available'),(96,1,'96','F','Premium','available'),(97,1,'97','F','Premium','available'),(98,1,'98','F','Premium','available'),(99,1,'99','F','Premium','available'),(100,1,'100','F','Premium','available'),(101,1,'101','F','Premium','available'),(102,1,'102','F','Premium','available'),(103,1,'103','F','Premium','available'),(104,1,'104','F','Premium','available'),(105,1,'105','F','Premium','available'),(106,1,'106','F','Premium','available'),(107,1,'107','F','Premium','available'),(108,1,'108','F','Premium','available'),
(109,1,'109','G','Premium','available'),(110,1,'110','G','Premium','available'),(111,1,'111','G','Premium','available'),(112,1,'112','G','Premium','available'),(113,1,'113','G','Premium','available'),(114,1,'114','G','Premium','available'),(115,1,'115','G','Premium','available'),(116,1,'116','G','Premium','available'),(117,1,'117','G','Premium','available'),(118,1,'118','G','Premium','available'),(119,1,'119','G','Premium','available'),(120,1,'120','G','Premium','available'),(121,1,'121','G','Premium','available'),(122,1,'122','G','Premium','available'),(123,1,'123','G','Premium','available'),(124,1,'124','G','Premium','available'),(125,1,'125','G','Premium','available'),(126,1,'126','G','Premium','available'),
(127,1,'127','H','Premium','available'),(128,1,'128','H','Premium','available'),(129,1,'129','H','Premium','available'),(130,1,'130','H','Premium','available'),(131,1,'131','H','Premium','available'),(132,1,'132','H','Premium','available'),(133,1,'133','H','Premium','available'),(134,1,'134','H','Premium','available'),(135,1,'135','H','Premium','available'),(136,1,'136','H','Premium','available'),(137,1,'137','H','Premium','available'),(138,1,'138','H','Premium','available'),(139,1,'139','H','Premium','available'),(140,1,'140','H','Premium','available'),(141,1,'141','H','Premium','available'),(142,1,'142','H','Premium','available'),(143,1,'143','H','Premium','available'),(144,1,'144','H','Premium','available'),
(145,1,'145','I','Premium','available'),(146,1,'146','I','Premium','available'),(147,1,'147','I','Premium','available'),(148,1,'148','I','Premium','available'),(149,1,'149','I','Premium','available'),(150,1,'150','I','Premium','available'),(151,1,'151','I','Premium','available'),(152,1,'152','I','Premium','available'),(153,1,'153','I','Premium','available'),(154,1,'154','I','Premium','available'),(155,1,'155','I','Premium','available'),(156,1,'156','I','Premium','available'),(157,1,'157','I','Premium','available'),(158,1,'158','I','Premium','available'),(159,1,'159','I','Premium','available'),(160,1,'160','I','Premium','available'),(161,1,'161','I','Premium','available'),(162,1,'162','I','Premium','available'),

-- ── Screen 2 (screen_id=2, 108 seats, 4DX) ─────────────────
-- Standard: seats 1–90 (rows A–E)
(163,2,'1','A','Standard','available'),(164,2,'2','A','Standard','available'),(165,2,'3','A','Standard','available'),(166,2,'4','A','Standard','available'),(167,2,'5','A','Standard','available'),(168,2,'6','A','Standard','available'),(169,2,'7','A','Standard','available'),(170,2,'8','A','Standard','available'),(171,2,'9','A','Standard','available'),(172,2,'10','A','Standard','available'),(173,2,'11','A','Standard','available'),(174,2,'12','A','Standard','available'),(175,2,'13','A','Standard','available'),(176,2,'14','A','Standard','available'),(177,2,'15','A','Standard','available'),(178,2,'16','A','Standard','available'),(179,2,'17','A','Standard','available'),(180,2,'18','A','Standard','available'),
(181,2,'19','B','Standard','available'),(182,2,'20','B','Standard','available'),(183,2,'21','B','Standard','available'),(184,2,'22','B','Standard','available'),(185,2,'23','B','Standard','available'),(186,2,'24','B','Standard','available'),(187,2,'25','B','Standard','available'),(188,2,'26','B','Standard','available'),(189,2,'27','B','Standard','available'),(190,2,'28','B','Standard','available'),(191,2,'29','B','Standard','available'),(192,2,'30','B','Standard','available'),(193,2,'31','B','Standard','available'),(194,2,'32','B','Standard','available'),(195,2,'33','B','Standard','available'),(196,2,'34','B','Standard','available'),(197,2,'35','B','Standard','available'),(198,2,'36','B','Standard','available'),
(199,2,'37','C','Standard','available'),(200,2,'38','C','Standard','available'),(201,2,'39','C','Standard','available'),(202,2,'40','C','Standard','available'),(203,2,'41','C','Standard','available'),(204,2,'42','C','Standard','available'),(205,2,'43','C','Standard','available'),(206,2,'44','C','Standard','available'),(207,2,'45','C','Standard','available'),(208,2,'46','C','Standard','available'),(209,2,'47','C','Standard','available'),(210,2,'48','C','Standard','available'),(211,2,'49','C','Standard','available'),(212,2,'50','C','Standard','available'),(213,2,'51','C','Standard','available'),(214,2,'52','C','Standard','available'),(215,2,'53','C','Standard','available'),(216,2,'54','C','Standard','available'),
(217,2,'55','D','Standard','available'),(218,2,'56','D','Standard','available'),(219,2,'57','D','Standard','available'),(220,2,'58','D','Standard','available'),(221,2,'59','D','Standard','available'),(222,2,'60','D','Standard','available'),(223,2,'61','D','Standard','available'),(224,2,'62','D','Standard','available'),(225,2,'63','D','Standard','available'),(226,2,'64','D','Standard','available'),(227,2,'65','D','Standard','available'),(228,2,'66','D','Standard','available'),(229,2,'67','D','Standard','available'),(230,2,'68','D','Standard','available'),(231,2,'69','D','Standard','available'),(232,2,'70','D','Standard','available'),(233,2,'71','D','Standard','available'),(234,2,'72','D','Standard','available'),
(235,2,'73','E','Standard','available'),(236,2,'74','E','Standard','available'),(237,2,'75','E','Standard','available'),(238,2,'76','E','Standard','available'),(239,2,'77','E','Standard','available'),(240,2,'78','E','Standard','available'),(241,2,'79','E','Standard','available'),(242,2,'80','E','Standard','available'),(243,2,'81','E','Standard','available'),(244,2,'82','E','Standard','available'),(245,2,'83','E','Standard','available'),(246,2,'84','E','Standard','available'),(247,2,'85','E','Standard','available'),(248,2,'86','E','Standard','available'),(249,2,'87','E','Standard','available'),(250,2,'88','E','Standard','available'),(251,2,'89','E','Standard','available'),(252,2,'90','E','Standard','available'),
-- Premium: seats 91–108 (row F, 18 seats)
(253,2,'91','F','Premium','available'),(254,2,'92','F','Premium','available'),(255,2,'93','F','Premium','available'),(256,2,'94','F','Premium','available'),(257,2,'95','F','Premium','available'),(258,2,'96','F','Premium','available'),(259,2,'97','F','Premium','available'),(260,2,'98','F','Premium','available'),(261,2,'99','F','Premium','available'),(262,2,'100','F','Premium','available'),(263,2,'101','F','Premium','available'),(264,2,'102','F','Premium','available'),(265,2,'103','F','Premium','available'),(266,2,'104','F','Premium','available'),(267,2,'105','F','Premium','available'),(268,2,'106','F','Premium','available'),(269,2,'107','F','Premium','available'),(270,2,'108','F','Premium','available'),

-- ── Screen 3 (screen_id=3, 198 seats, Standard) ────────────
-- Standard: seats 1–90 (rows A–E)
(271,3,'1','A','Standard','available'),(272,3,'2','A','Standard','available'),(273,3,'3','A','Standard','available'),(274,3,'4','A','Standard','available'),(275,3,'5','A','Standard','available'),(276,3,'6','A','Standard','available'),(277,3,'7','A','Standard','available'),(278,3,'8','A','Standard','available'),(279,3,'9','A','Standard','available'),(280,3,'10','A','Standard','available'),(281,3,'11','A','Standard','available'),(282,3,'12','A','Standard','available'),(283,3,'13','A','Standard','available'),(284,3,'14','A','Standard','available'),(285,3,'15','A','Standard','available'),(286,3,'16','A','Standard','available'),(287,3,'17','A','Standard','available'),(288,3,'18','A','Standard','available'),
(289,3,'19','B','Standard','available'),(290,3,'20','B','Standard','available'),(291,3,'21','B','Standard','available'),(292,3,'22','B','Standard','available'),(293,3,'23','B','Standard','available'),(294,3,'24','B','Standard','available'),(295,3,'25','B','Standard','available'),(296,3,'26','B','Standard','available'),(297,3,'27','B','Standard','available'),(298,3,'28','B','Standard','available'),(299,3,'29','B','Standard','available'),(300,3,'30','B','Standard','available'),(301,3,'31','B','Standard','available'),(302,3,'32','B','Standard','available'),(303,3,'33','B','Standard','available'),(304,3,'34','B','Standard','available'),(305,3,'35','B','Standard','available'),(306,3,'36','B','Standard','available'),
(307,3,'37','C','Standard','available'),(308,3,'38','C','Standard','available'),(309,3,'39','C','Standard','available'),(310,3,'40','C','Standard','available'),(311,3,'41','C','Standard','available'),(312,3,'42','C','Standard','available'),(313,3,'43','C','Standard','available'),(314,3,'44','C','Standard','available'),(315,3,'45','C','Standard','available'),(316,3,'46','C','Standard','available'),(317,3,'47','C','Standard','available'),(318,3,'48','C','Standard','available'),(319,3,'49','C','Standard','available'),(320,3,'50','C','Standard','available'),(321,3,'51','C','Standard','available'),(322,3,'52','C','Standard','available'),(323,3,'53','C','Standard','available'),(324,3,'54','C','Standard','available'),
(325,3,'55','D','Standard','available'),(326,3,'56','D','Standard','available'),(327,3,'57','D','Standard','available'),(328,3,'58','D','Standard','available'),(329,3,'59','D','Standard','available'),(330,3,'60','D','Standard','available'),(331,3,'61','D','Standard','available'),(332,3,'62','D','Standard','available'),(333,3,'63','D','Standard','available'),(334,3,'64','D','Standard','available'),(335,3,'65','D','Standard','available'),(336,3,'66','D','Standard','available'),(337,3,'67','D','Standard','available'),(338,3,'68','D','Standard','available'),(339,3,'69','D','Standard','available'),(340,3,'70','D','Standard','available'),(341,3,'71','D','Standard','available'),(342,3,'72','D','Standard','available'),
(343,3,'73','E','Standard','available'),(344,3,'74','E','Standard','available'),(345,3,'75','E','Standard','available'),(346,3,'76','E','Standard','available'),(347,3,'77','E','Standard','available'),(348,3,'78','E','Standard','available'),(349,3,'79','E','Standard','available'),(350,3,'80','E','Standard','available'),(351,3,'81','E','Standard','available'),(352,3,'82','E','Standard','available'),(353,3,'83','E','Standard','available'),(354,3,'84','E','Standard','available'),(355,3,'85','E','Standard','available'),(356,3,'86','E','Standard','available'),(357,3,'87','E','Standard','available'),(358,3,'88','E','Standard','available'),(359,3,'89','E','Standard','available'),(360,3,'90','E','Standard','available'),
-- Premium: seats 91–180 (rows F–J)
(361,3,'91','F','Premium','available'),(362,3,'92','F','Premium','available'),(363,3,'93','F','Premium','available'),(364,3,'94','F','Premium','available'),(365,3,'95','F','Premium','available'),(366,3,'96','F','Premium','available'),(367,3,'97','F','Premium','available'),(368,3,'98','F','Premium','available'),(369,3,'99','F','Premium','available'),(370,3,'100','F','Premium','available'),(371,3,'101','F','Premium','available'),(372,3,'102','F','Premium','available'),(373,3,'103','F','Premium','available'),(374,3,'104','F','Premium','available'),(375,3,'105','F','Premium','available'),(376,3,'106','F','Premium','available'),(377,3,'107','F','Premium','available'),(378,3,'108','F','Premium','available'),
(379,3,'109','G','Premium','available'),(380,3,'110','G','Premium','available'),(381,3,'111','G','Premium','available'),(382,3,'112','G','Premium','available'),(383,3,'113','G','Premium','available'),(384,3,'114','G','Premium','available'),(385,3,'115','G','Premium','available'),(386,3,'116','G','Premium','available'),(387,3,'117','G','Premium','available'),(388,3,'118','G','Premium','available'),(389,3,'119','G','Premium','available'),(390,3,'120','G','Premium','available'),(391,3,'121','G','Premium','available'),(392,3,'122','G','Premium','available'),(393,3,'123','G','Premium','available'),(394,3,'124','G','Premium','available'),(395,3,'125','G','Premium','available'),(396,3,'126','G','Premium','available'),
(397,3,'127','H','Premium','available'),(398,3,'128','H','Premium','available'),(399,3,'129','H','Premium','available'),(400,3,'130','H','Premium','available'),(401,3,'131','H','Premium','available'),(402,3,'132','H','Premium','available'),(403,3,'133','H','Premium','available'),(404,3,'134','H','Premium','available'),(405,3,'135','H','Premium','available'),(406,3,'136','H','Premium','available'),(407,3,'137','H','Premium','available'),(408,3,'138','H','Premium','available'),(409,3,'139','H','Premium','available'),(410,3,'140','H','Premium','available'),(411,3,'141','H','Premium','available'),(412,3,'142','H','Premium','available'),(413,3,'143','H','Premium','available'),(414,3,'144','H','Premium','available'),
(415,3,'145','I','Premium','available'),(416,3,'146','I','Premium','available'),(417,3,'147','I','Premium','available'),(418,3,'148','I','Premium','available'),(419,3,'149','I','Premium','available'),(420,3,'150','I','Premium','available'),(421,3,'151','I','Premium','available'),(422,3,'152','I','Premium','available'),(423,3,'153','I','Premium','available'),(424,3,'154','I','Premium','available'),(425,3,'155','I','Premium','available'),(426,3,'156','I','Premium','available'),(427,3,'157','I','Premium','available'),(428,3,'158','I','Premium','available'),(429,3,'159','I','Premium','available'),(430,3,'160','I','Premium','available'),(431,3,'161','I','Premium','available'),(432,3,'162','I','Premium','available'),
(433,3,'163','J','Premium','available'),(434,3,'164','J','Premium','available'),(435,3,'165','J','Premium','available'),(436,3,'166','J','Premium','available'),(437,3,'167','J','Premium','available'),(438,3,'168','J','Premium','available'),(439,3,'169','J','Premium','available'),(440,3,'170','J','Premium','available'),(441,3,'171','J','Premium','available'),(442,3,'172','J','Premium','available'),(443,3,'173','J','Premium','available'),(444,3,'174','J','Premium','available'),(445,3,'175','J','Premium','available'),(446,3,'176','J','Premium','available'),(447,3,'177','J','Premium','available'),(448,3,'178','J','Premium','available'),(449,3,'179','J','Premium','available'),(450,3,'180','J','Premium','available'),
-- VIP: seats 181–198 (row K, 18 seats)
(451,3,'181','K','VIP','available'),(452,3,'182','K','VIP','available'),(453,3,'183','K','VIP','available'),(454,3,'184','K','VIP','available'),(455,3,'185','K','VIP','available'),(456,3,'186','K','VIP','available'),(457,3,'187','K','VIP','available'),(458,3,'188','K','VIP','available'),(459,3,'189','K','VIP','available'),(460,3,'190','K','VIP','available'),(461,3,'191','K','VIP','available'),(462,3,'192','K','VIP','available'),(463,3,'193','K','VIP','available'),(464,3,'194','K','VIP','available'),(465,3,'195','K','VIP','available'),(466,3,'196','K','VIP','available'),(467,3,'197','K','VIP','available'),(468,3,'198','K','VIP','available'),

-- ── Screen 4 (screen_id=4, 126 seats, IMAX) ────────────────
-- Standard: seats 1–90 (rows A–E)
(469,4,'1','A','Standard','available'),(470,4,'2','A','Standard','available'),(471,4,'3','A','Standard','available'),(472,4,'4','A','Standard','available'),(473,4,'5','A','Standard','available'),(474,4,'6','A','Standard','available'),(475,4,'7','A','Standard','available'),(476,4,'8','A','Standard','available'),(477,4,'9','A','Standard','available'),(478,4,'10','A','Standard','available'),(479,4,'11','A','Standard','available'),(480,4,'12','A','Standard','available'),(481,4,'13','A','Standard','available'),(482,4,'14','A','Standard','available'),(483,4,'15','A','Standard','available'),(484,4,'16','A','Standard','available'),(485,4,'17','A','Standard','available'),(486,4,'18','A','Standard','available'),
(487,4,'19','B','Standard','available'),(488,4,'20','B','Standard','available'),(489,4,'21','B','Standard','available'),(490,4,'22','B','Standard','available'),(491,4,'23','B','Standard','available'),(492,4,'24','B','Standard','available'),(493,4,'25','B','Standard','available'),(494,4,'26','B','Standard','available'),(495,4,'27','B','Standard','available'),(496,4,'28','B','Standard','available'),(497,4,'29','B','Standard','available'),(498,4,'30','B','Standard','available'),(499,4,'31','B','Standard','available'),(500,4,'32','B','Standard','available'),(501,4,'33','B','Standard','available'),(502,4,'34','B','Standard','available'),(503,4,'35','B','Standard','available'),(504,4,'36','B','Standard','available'),
(505,4,'37','C','Standard','available'),(506,4,'38','C','Standard','available'),(507,4,'39','C','Standard','available'),(508,4,'40','C','Standard','available'),(509,4,'41','C','Standard','available'),(510,4,'42','C','Standard','available'),(511,4,'43','C','Standard','available'),(512,4,'44','C','Standard','available'),(513,4,'45','C','Standard','available'),(514,4,'46','C','Standard','available'),(515,4,'47','C','Standard','available'),(516,4,'48','C','Standard','available'),(517,4,'49','C','Standard','available'),(518,4,'50','C','Standard','available'),(519,4,'51','C','Standard','available'),(520,4,'52','C','Standard','available'),(521,4,'53','C','Standard','available'),(522,4,'54','C','Standard','available'),
(523,4,'55','D','Standard','available'),(524,4,'56','D','Standard','available'),(525,4,'57','D','Standard','available'),(526,4,'58','D','Standard','available'),(527,4,'59','D','Standard','available'),(528,4,'60','D','Standard','available'),(529,4,'61','D','Standard','available'),(530,4,'62','D','Standard','available'),(531,4,'63','D','Standard','available'),(532,4,'64','D','Standard','available'),(533,4,'65','D','Standard','available'),(534,4,'66','D','Standard','available'),(535,4,'67','D','Standard','available'),(536,4,'68','D','Standard','available'),(537,4,'69','D','Standard','available'),(538,4,'70','D','Standard','available'),(539,4,'71','D','Standard','available'),(540,4,'72','D','Standard','available'),
(541,4,'73','E','Standard','available'),(542,4,'74','E','Standard','available'),(543,4,'75','E','Standard','available'),(544,4,'76','E','Standard','available'),(545,4,'77','E','Standard','available'),(546,4,'78','E','Standard','available'),(547,4,'79','E','Standard','available'),(548,4,'80','E','Standard','available'),(549,4,'81','E','Standard','available'),(550,4,'82','E','Standard','available'),(551,4,'83','E','Standard','available'),(552,4,'84','E','Standard','available'),(553,4,'85','E','Standard','available'),(554,4,'86','E','Standard','available'),(555,4,'87','E','Standard','available'),(556,4,'88','E','Standard','available'),(557,4,'89','E','Standard','available'),(558,4,'90','E','Standard','available'),
-- Premium: seats 91–126 (rows F–G, 36 seats)
(559,4,'91','F','Premium','available'),(560,4,'92','F','Premium','available'),(561,4,'93','F','Premium','available'),(562,4,'94','F','Premium','available'),(563,4,'95','F','Premium','available'),(564,4,'96','F','Premium','available'),(565,4,'97','F','Premium','available'),(566,4,'98','F','Premium','available'),(567,4,'99','F','Premium','available'),(568,4,'100','F','Premium','available'),(569,4,'101','F','Premium','available'),(570,4,'102','F','Premium','available'),(571,4,'103','F','Premium','available'),(572,4,'104','F','Premium','available'),(573,4,'105','F','Premium','available'),(574,4,'106','F','Premium','available'),(575,4,'107','F','Premium','available'),(576,4,'108','F','Premium','available'),
(577,4,'109','G','Premium','available'),(578,4,'110','G','Premium','available'),(579,4,'111','G','Premium','available'),(580,4,'112','G','Premium','available'),(581,4,'113','G','Premium','available'),(582,4,'114','G','Premium','available'),(583,4,'115','G','Premium','available'),(584,4,'116','G','Premium','available'),(585,4,'117','G','Premium','available'),(586,4,'118','G','Premium','available'),(587,4,'119','G','Premium','available'),(588,4,'120','G','Premium','available'),(589,4,'121','G','Premium','available'),(590,4,'122','G','Premium','available'),(591,4,'123','G','Premium','available'),(592,4,'124','G','Premium','available'),(593,4,'125','G','Premium','available'),(594,4,'126','G','Premium','available'),

-- ── Screen 5 (screen_id=5, 180 seats, Standard) ────────────
-- Standard: seats 1–90 (rows A–E)
(595,5,'1','A','Standard','available'),(596,5,'2','A','Standard','available'),(597,5,'3','A','Standard','available'),(598,5,'4','A','Standard','available'),(599,5,'5','A','Standard','available'),(600,5,'6','A','Standard','available'),(601,5,'7','A','Standard','available'),(602,5,'8','A','Standard','available'),(603,5,'9','A','Standard','available'),(604,5,'10','A','Standard','available'),(605,5,'11','A','Standard','available'),(606,5,'12','A','Standard','available'),(607,5,'13','A','Standard','available'),(608,5,'14','A','Standard','available'),(609,5,'15','A','Standard','available'),(610,5,'16','A','Standard','available'),(611,5,'17','A','Standard','available'),(612,5,'18','A','Standard','available'),
(613,5,'19','B','Standard','available'),(614,5,'20','B','Standard','available'),(615,5,'21','B','Standard','available'),(616,5,'22','B','Standard','available'),(617,5,'23','B','Standard','available'),(618,5,'24','B','Standard','available'),(619,5,'25','B','Standard','available'),(620,5,'26','B','Standard','available'),(621,5,'27','B','Standard','available'),(622,5,'28','B','Standard','available'),(623,5,'29','B','Standard','available'),(624,5,'30','B','Standard','available'),(625,5,'31','B','Standard','available'),(626,5,'32','B','Standard','available'),(627,5,'33','B','Standard','available'),(628,5,'34','B','Standard','available'),(629,5,'35','B','Standard','available'),(630,5,'36','B','Standard','available'),
(631,5,'37','C','Standard','available'),(632,5,'38','C','Standard','available'),(633,5,'39','C','Standard','available'),(634,5,'40','C','Standard','available'),(635,5,'41','C','Standard','available'),(636,5,'42','C','Standard','available'),(637,5,'43','C','Standard','available'),(638,5,'44','C','Standard','available'),(639,5,'45','C','Standard','available'),(640,5,'46','C','Standard','available'),(641,5,'47','C','Standard','available'),(642,5,'48','C','Standard','available'),(643,5,'49','C','Standard','available'),(644,5,'50','C','Standard','available'),(645,5,'51','C','Standard','available'),(646,5,'52','C','Standard','available'),(647,5,'53','C','Standard','available'),(648,5,'54','C','Standard','available'),
(649,5,'55','D','Standard','available'),(650,5,'56','D','Standard','available'),(651,5,'57','D','Standard','available'),(652,5,'58','D','Standard','available'),(653,5,'59','D','Standard','available'),(654,5,'60','D','Standard','available'),(655,5,'61','D','Standard','available'),(656,5,'62','D','Standard','available'),(657,5,'63','D','Standard','available'),(658,5,'64','D','Standard','available'),(659,5,'65','D','Standard','available'),(660,5,'66','D','Standard','available'),(661,5,'67','D','Standard','available'),(662,5,'68','D','Standard','available'),(663,5,'69','D','Standard','available'),(664,5,'70','D','Standard','available'),(665,5,'71','D','Standard','available'),(666,5,'72','D','Standard','available'),
(667,5,'73','E','Standard','available'),(668,5,'74','E','Standard','available'),(669,5,'75','E','Standard','available'),(670,5,'76','E','Standard','available'),(671,5,'77','E','Standard','available'),(672,5,'78','E','Standard','available'),(673,5,'79','E','Standard','available'),(674,5,'80','E','Standard','available'),(675,5,'81','E','Standard','available'),(676,5,'82','E','Standard','available'),(677,5,'83','E','Standard','available'),(678,5,'84','E','Standard','available'),(679,5,'85','E','Standard','available'),(680,5,'86','E','Standard','available'),(681,5,'87','E','Standard','available'),(682,5,'88','E','Standard','available'),(683,5,'89','E','Standard','available'),(684,5,'90','E','Standard','available'),
-- Premium: seats 91–180 (rows F–J)
(685,5,'91','F','Premium','available'),(686,5,'92','F','Premium','available'),(687,5,'93','F','Premium','available'),(688,5,'94','F','Premium','available'),(689,5,'95','F','Premium','available'),(690,5,'96','F','Premium','available'),(691,5,'97','F','Premium','available'),(692,5,'98','F','Premium','available'),(693,5,'99','F','Premium','available'),(694,5,'100','F','Premium','available'),(695,5,'101','F','Premium','available'),(696,5,'102','F','Premium','available'),(697,5,'103','F','Premium','available'),(698,5,'104','F','Premium','available'),(699,5,'105','F','Premium','available'),(700,5,'106','F','Premium','available'),(701,5,'107','F','Premium','available'),(702,5,'108','F','Premium','available'),
(703,5,'109','G','Premium','available'),(704,5,'110','G','Premium','available'),(705,5,'111','G','Premium','available'),(706,5,'112','G','Premium','available'),(707,5,'113','G','Premium','available'),(708,5,'114','G','Premium','available'),(709,5,'115','G','Premium','available'),(710,5,'116','G','Premium','available'),(711,5,'117','G','Premium','available'),(712,5,'118','G','Premium','available'),(713,5,'119','G','Premium','available'),(714,5,'120','G','Premium','available'),(715,5,'121','G','Premium','available'),(716,5,'122','G','Premium','available'),(717,5,'123','G','Premium','available'),(718,5,'124','G','Premium','available'),(719,5,'125','G','Premium','available'),(720,5,'126','G','Premium','available'),
(721,5,'127','H','Premium','available'),(722,5,'128','H','Premium','available'),(723,5,'129','H','Premium','available'),(724,5,'130','H','Premium','available'),(725,5,'131','H','Premium','available'),(726,5,'132','H','Premium','available'),(727,5,'133','H','Premium','available'),(728,5,'134','H','Premium','available'),(729,5,'135','H','Premium','available'),(730,5,'136','H','Premium','available'),(731,5,'137','H','Premium','available'),(732,5,'138','H','Premium','available'),(733,5,'139','H','Premium','available'),(734,5,'140','H','Premium','available'),(735,5,'141','H','Premium','available'),(736,5,'142','H','Premium','available'),(737,5,'143','H','Premium','available'),(738,5,'144','H','Premium','available'),
(739,5,'145','I','Premium','available'),(740,5,'146','I','Premium','available'),(741,5,'147','I','Premium','available'),(742,5,'148','I','Premium','available'),(743,5,'149','I','Premium','available'),(744,5,'150','I','Premium','available'),(745,5,'151','I','Premium','available'),(746,5,'152','I','Premium','available'),(747,5,'153','I','Premium','available'),(748,5,'154','I','Premium','available'),(749,5,'155','I','Premium','available'),(750,5,'156','I','Premium','available'),(751,5,'157','I','Premium','available'),(752,5,'158','I','Premium','available'),(753,5,'159','I','Premium','available'),(754,5,'160','I','Premium','available'),(755,5,'161','I','Premium','available'),(756,5,'162','I','Premium','available'),
(757,5,'163','J','Premium','available'),(758,5,'164','J','Premium','available'),(759,5,'165','J','Premium','available'),(760,5,'166','J','Premium','available'),(761,5,'167','J','Premium','available'),(762,5,'168','J','Premium','available'),(763,5,'169','J','Premium','available'),(764,5,'170','J','Premium','available'),(765,5,'171','J','Premium','available'),(766,5,'172','J','Premium','available'),(767,5,'173','J','Premium','available'),(768,5,'174','J','Premium','available'),(769,5,'175','J','Premium','available'),(770,5,'176','J','Premium','available'),(771,5,'177','J','Premium','available'),(772,5,'178','J','Premium','available'),(773,5,'179','J','Premium','available'),(774,5,'180','J','Premium','available');


-- ────────────────────────────────────────────────────────────
-- MOVIE (unchanged, all NOW_SHOWING dates already in May 2026)
-- ────────────────────────────────────────────────────────────

INSERT INTO `movie` (`movie_id`, `movie_name`, `duration`, `director`, `genre`, `movie_language`, `description`, `release_date`, `movie_status`, `age_rating`) VALUES
(1, 'The Devil Wears Prada 2',          135, 'TBD', 'Comedy',   'English', 'Sequel to the iconic fashion drama.',                '2026-05-01', 'NOW_SHOWING',  'PG'),
(2, 'Michael',                          125, 'TBD', 'Biography','English', 'A biographical film based on a legendary figure.',   '2026-05-01', 'NOW_SHOWING',  'PG'),
(3, 'Bhoot Bangla',                     160, 'TBD', 'Horror',   'Hindi',   'A horror story set in a haunted mansion.',           '2026-05-18', 'NOW_SHOWING',  'PG'),
(4, 'Project Hail Mary',                150, 'TBD', 'Sci-Fi',   'English', 'A lone astronaut attempts to save humanity.',        '2026-05-17', 'NOW_SHOWING',  'PG'),
(5, 'The Odyssey',                      180, 'TBD', 'Action',   'English', 'An epic journey inspired by the Greek classic.',     '2026-07-17', 'COMING_SOON',  'PG'),
(6, 'Spider Man: Brand New Day',        190, 'TBD', 'Action',   'English', 'A new chapter in Spider-Man''s story.',              '2026-07-31', 'COMING_SOON',  'PG'),
(7, 'Insidious: Out of the Further',    155, 'TBD', 'Horror',   'English', 'A terrifying return to the Further.',                '2026-08-21', 'COMING_SOON',  'ADULT'),
(8, 'Drishyam 3',                       175, 'TBD', 'Thriller', 'Hindi',   'Continuation of the gripping Drishyam saga.',       '2026-10-02', 'COMING_SOON',  'PG');


-- ────────────────────────────────────────────────────────────
-- SHOWTIME
-- All dates from 2026-05-19 (today) through 2026-05-25
-- All 4 NOW_SHOWING movies shown across all 5 screens
-- 3 slots per screen per day: morning / afternoon / evening
-- end_time = start_time + duration (rounded to nearest 5 min)
--
-- Screen 1 (IMAX, theatre 1)  → movies 1 & 4
-- Screen 2 (4DX,  theatre 1)  → movies 2 & 3
-- Screen 3 (Std,  theatre 1)  → movies 3 & 4
-- Screen 4 (IMAX, theatre 2)  → movies 1 & 2
-- Screen 5 (Std,  theatre 2)  → movies 2 & 4
-- ────────────────────────────────────────────────────────────

INSERT INTO `showtime` (`showtime_id`, `screen_id`, `movie_id`, `show_date`, `start_time`, `end_time`, `show_status`, `show_type`) VALUES
-- 2026-05-19 (today)
(1,  1, 1, '2026-05-19', '10:00:00', '12:15:00', 'ACTIVE', 'IMAX'),
(2,  1, 4, '2026-05-19', '13:30:00', '16:00:00', 'ACTIVE', 'IMAX'),
(3,  1, 1, '2026-05-19', '17:00:00', '19:15:00', 'ACTIVE', 'IMAX'),
(4,  2, 2, '2026-05-19', '10:30:00', '12:35:00', 'ACTIVE', '4DX'),
(5,  2, 3, '2026-05-19', '14:00:00', '16:40:00', 'ACTIVE', '4DX'),
(6,  2, 2, '2026-05-19', '18:00:00', '20:05:00', 'ACTIVE', '4DX'),
(7,  3, 4, '2026-05-19', '11:00:00', '13:30:00', 'ACTIVE', 'Standard'),
(8,  3, 3, '2026-05-19', '15:00:00', '17:40:00', 'ACTIVE', 'Standard'),
(9,  3, 4, '2026-05-19', '19:00:00', '21:30:00', 'ACTIVE', 'Standard'),
(10, 4, 1, '2026-05-19', '10:00:00', '12:15:00', 'ACTIVE', 'IMAX'),
(11, 4, 2, '2026-05-19', '13:00:00', '15:05:00', 'ACTIVE', 'IMAX'),
(12, 4, 1, '2026-05-19', '17:30:00', '19:45:00', 'ACTIVE', 'IMAX'),
(13, 5, 2, '2026-05-19', '10:00:00', '12:05:00', 'ACTIVE', 'Standard'),
(14, 5, 4, '2026-05-19', '13:30:00', '16:00:00', 'ACTIVE', 'Standard'),
(15, 5, 2, '2026-05-19', '18:00:00', '20:05:00', 'ACTIVE', 'Standard'),

-- 2026-05-20
(16, 1, 4, '2026-05-20', '10:00:00', '12:30:00', 'ACTIVE', 'IMAX'),
(17, 1, 1, '2026-05-20', '14:00:00', '16:15:00', 'ACTIVE', 'IMAX'),
(18, 1, 4, '2026-05-20', '18:00:00', '20:30:00', 'ACTIVE', 'IMAX'),
(19, 2, 3, '2026-05-20', '10:00:00', '12:40:00', 'ACTIVE', '4DX'),
(20, 2, 2, '2026-05-20', '14:00:00', '16:05:00', 'ACTIVE', '4DX'),
(21, 2, 3, '2026-05-20', '18:30:00', '21:10:00', 'ACTIVE', '4DX'),
(22, 3, 3, '2026-05-20', '10:30:00', '13:10:00', 'ACTIVE', 'Standard'),
(23, 3, 4, '2026-05-20', '14:30:00', '17:00:00', 'ACTIVE', 'Standard'),
(24, 3, 3, '2026-05-20', '19:00:00', '21:40:00', 'ACTIVE', 'Standard'),
(25, 4, 2, '2026-05-20', '10:00:00', '12:05:00', 'ACTIVE', 'IMAX'),
(26, 4, 1, '2026-05-20', '13:30:00', '15:45:00', 'ACTIVE', 'IMAX'),
(27, 4, 2, '2026-05-20', '18:00:00', '20:05:00', 'ACTIVE', 'IMAX'),
(28, 5, 4, '2026-05-20', '10:00:00', '12:30:00', 'ACTIVE', 'Standard'),
(29, 5, 2, '2026-05-20', '14:00:00', '16:05:00', 'ACTIVE', 'Standard'),
(30, 5, 4, '2026-05-20', '18:30:00', '21:00:00', 'ACTIVE', 'Standard'),

-- 2026-05-21
(31, 1, 1, '2026-05-21', '10:00:00', '12:15:00', 'ACTIVE', 'IMAX'),
(32, 1, 4, '2026-05-21', '13:30:00', '16:00:00', 'ACTIVE', 'IMAX'),
(33, 1, 1, '2026-05-21', '17:30:00', '19:45:00', 'ACTIVE', 'IMAX'),
(34, 2, 2, '2026-05-21', '11:00:00', '13:05:00', 'ACTIVE', '4DX'),
(35, 2, 3, '2026-05-21', '15:00:00', '17:40:00', 'ACTIVE', '4DX'),
(36, 2, 2, '2026-05-21', '19:00:00', '21:05:00', 'ACTIVE', '4DX'),
(37, 3, 4, '2026-05-21', '10:00:00', '12:30:00', 'ACTIVE', 'Standard'),
(38, 3, 3, '2026-05-21', '14:00:00', '16:40:00', 'ACTIVE', 'Standard'),
(39, 3, 4, '2026-05-21', '18:30:00', '21:00:00', 'ACTIVE', 'Standard'),
(40, 4, 1, '2026-05-21', '10:30:00', '12:45:00', 'ACTIVE', 'IMAX'),
(41, 4, 2, '2026-05-21', '14:00:00', '16:05:00', 'ACTIVE', 'IMAX'),
(42, 4, 1, '2026-05-21', '18:00:00', '20:15:00', 'ACTIVE', 'IMAX'),
(43, 5, 2, '2026-05-21', '10:00:00', '12:05:00', 'ACTIVE', 'Standard'),
(44, 5, 4, '2026-05-21', '13:30:00', '16:00:00', 'ACTIVE', 'Standard'),
(45, 5, 2, '2026-05-21', '18:00:00', '20:05:00', 'ACTIVE', 'Standard'),

-- 2026-05-22
(46, 1, 4, '2026-05-22', '10:00:00', '12:30:00', 'ACTIVE', 'IMAX'),
(47, 1, 1, '2026-05-22', '14:00:00', '16:15:00', 'ACTIVE', 'IMAX'),
(48, 1, 4, '2026-05-22', '18:30:00', '21:00:00', 'ACTIVE', 'IMAX'),
(49, 2, 3, '2026-05-22', '10:00:00', '12:40:00', 'ACTIVE', '4DX'),
(50, 2, 2, '2026-05-22', '14:30:00', '16:35:00', 'ACTIVE', '4DX'),
(51, 2, 3, '2026-05-22', '19:00:00', '21:40:00', 'ACTIVE', '4DX'),
(52, 3, 3, '2026-05-22', '10:30:00', '13:10:00', 'ACTIVE', 'Standard'),
(53, 3, 4, '2026-05-22', '15:00:00', '17:30:00', 'ACTIVE', 'Standard'),
(54, 3, 3, '2026-05-22', '19:30:00', '22:10:00', 'ACTIVE', 'Standard'),
(55, 4, 2, '2026-05-22', '10:00:00', '12:05:00', 'ACTIVE', 'IMAX'),
(56, 4, 1, '2026-05-22', '13:30:00', '15:45:00', 'ACTIVE', 'IMAX'),
(57, 4, 2, '2026-05-22', '18:00:00', '20:05:00', 'ACTIVE', 'IMAX'),
(58, 5, 4, '2026-05-22', '10:00:00', '12:30:00', 'ACTIVE', 'Standard'),
(59, 5, 2, '2026-05-22', '14:00:00', '16:05:00', 'ACTIVE', 'Standard'),
(60, 5, 4, '2026-05-22', '18:30:00', '21:00:00', 'ACTIVE', 'Standard'),

-- 2026-05-23
(61, 1, 1, '2026-05-23', '10:00:00', '12:15:00', 'ACTIVE', 'IMAX'),
(62, 1, 4, '2026-05-23', '13:30:00', '16:00:00', 'ACTIVE', 'IMAX'),
(63, 1, 1, '2026-05-23', '17:00:00', '19:15:00', 'ACTIVE', 'IMAX'),
(64, 2, 2, '2026-05-23', '10:30:00', '12:35:00', 'ACTIVE', '4DX'),
(65, 2, 3, '2026-05-23', '14:00:00', '16:40:00', 'ACTIVE', '4DX'),
(66, 2, 2, '2026-05-23', '18:00:00', '20:05:00', 'ACTIVE', '4DX'),
(67, 3, 4, '2026-05-23', '11:00:00', '13:30:00', 'ACTIVE', 'Standard'),
(68, 3, 3, '2026-05-23', '15:30:00', '18:10:00', 'ACTIVE', 'Standard'),
(69, 3, 4, '2026-05-23', '19:30:00', '22:00:00', 'ACTIVE', 'Standard'),
(70, 4, 1, '2026-05-23', '10:00:00', '12:15:00', 'ACTIVE', 'IMAX'),
(71, 4, 2, '2026-05-23', '13:00:00', '15:05:00', 'ACTIVE', 'IMAX'),
(72, 4, 1, '2026-05-23', '17:30:00', '19:45:00', 'ACTIVE', 'IMAX'),
(73, 5, 2, '2026-05-23', '10:00:00', '12:05:00', 'ACTIVE', 'Standard'),
(74, 5, 4, '2026-05-23', '13:30:00', '16:00:00', 'ACTIVE', 'Standard'),
(75, 5, 2, '2026-05-23', '18:00:00', '20:05:00', 'ACTIVE', 'Standard'),

-- 2026-05-24
(76, 1, 4, '2026-05-24', '10:00:00', '12:30:00', 'ACTIVE', 'IMAX'),
(77, 1, 1, '2026-05-24', '14:00:00', '16:15:00', 'ACTIVE', 'IMAX'),
(78, 1, 4, '2026-05-24', '18:00:00', '20:30:00', 'ACTIVE', 'IMAX'),
(79, 2, 3, '2026-05-24', '10:00:00', '12:40:00', 'ACTIVE', '4DX'),
(80, 2, 2, '2026-05-24', '14:00:00', '16:05:00', 'ACTIVE', '4DX'),
(81, 2, 3, '2026-05-24', '18:30:00', '21:10:00', 'ACTIVE', '4DX'),
(82, 3, 3, '2026-05-24', '10:30:00', '13:10:00', 'ACTIVE', 'Standard'),
(83, 3, 4, '2026-05-24', '14:30:00', '17:00:00', 'ACTIVE', 'Standard'),
(84, 3, 3, '2026-05-24', '19:00:00', '21:40:00', 'ACTIVE', 'Standard'),
(85, 4, 2, '2026-05-24', '10:00:00', '12:05:00', 'ACTIVE', 'IMAX'),
(86, 4, 1, '2026-05-24', '13:30:00', '15:45:00', 'ACTIVE', 'IMAX'),
(87, 4, 2, '2026-05-24', '18:00:00', '20:05:00', 'ACTIVE', 'IMAX'),
(88, 5, 4, '2026-05-24', '10:00:00', '12:30:00', 'ACTIVE', 'Standard'),
(89, 5, 2, '2026-05-24', '14:00:00', '16:05:00', 'ACTIVE', 'Standard'),
(90, 5, 4, '2026-05-24', '18:30:00', '21:00:00', 'ACTIVE', 'Standard'),

-- 2026-05-25
(91, 1, 1, '2026-05-25', '10:00:00', '12:15:00', 'ACTIVE', 'IMAX'),
(92, 1, 4, '2026-05-25', '13:30:00', '16:00:00', 'ACTIVE', 'IMAX'),
(93, 1, 1, '2026-05-25', '17:30:00', '19:45:00', 'ACTIVE', 'IMAX'),
(94, 2, 2, '2026-05-25', '10:30:00', '12:35:00', 'ACTIVE', '4DX'),
(95, 2, 3, '2026-05-25', '14:00:00', '16:40:00', 'ACTIVE', '4DX'),
(96, 2, 2, '2026-05-25', '18:00:00', '20:05:00', 'ACTIVE', '4DX'),
(97, 3, 4, '2026-05-25', '11:00:00', '13:30:00', 'ACTIVE', 'Standard'),
(98, 3, 3, '2026-05-25', '15:00:00', '17:40:00', 'ACTIVE', 'Standard'),
(99, 3, 4, '2026-05-25', '19:00:00', '21:30:00', 'ACTIVE', 'Standard'),
(100,4, 1, '2026-05-25', '10:00:00', '12:15:00', 'ACTIVE', 'IMAX'),
(101,4, 2, '2026-05-25', '13:00:00', '15:05:00', 'ACTIVE', 'IMAX'),
(102,4, 1, '2026-05-25', '17:00:00', '19:15:00', 'ACTIVE', 'IMAX'),
(103,5, 2, '2026-05-25', '10:00:00', '12:05:00', 'ACTIVE', 'Standard'),
(104,5, 4, '2026-05-25', '13:30:00', '16:00:00', 'ACTIVE', 'Standard'),
(105,5, 2, '2026-05-25', '18:00:00', '20:05:00', 'ACTIVE', 'Standard');


-- ────────────────────────────────────────────────────────────
-- BOOKING
-- Dates corrected to be consistent (some future, some past)
-- ────────────────────────────────────────────────────────────

INSERT INTO `booking` (`booking_id`, `user_id`, `booking_date`, `booking_time`, `booking_status`, `total_amount`, `booking_channel`, `loyalty_points_earned`) VALUES
(1,  2, '2026-05-19', '10:30:00', 'confirmed', 850.00,  'online',  85),
(2,  2, '2026-05-19', '14:15:00', 'pending',   1200.00, 'online',  120),
(3,  2, '2026-05-10', '09:00:00', 'archive',   500.00,  'counter', 0),
(4,  2, '2026-05-19', '19:45:00', 'confirmed', 650.00,  'online',  65),
(5,  2, '2026-05-12', '11:00:00', 'confirmed', 1500.00, 'online',  150),
(6,  3, '2026-05-19', '16:00:00', 'confirmed', 700.00,  'counter', 70),
(7,  3, '2026-05-20', '20:30:00', 'confirmed', 950.00,  'online',  95),
(8,  3, '2026-05-21', '13:00:00', 'pending',   600.00,  'online',  0),
(9,  3, '2026-05-15', '18:00:00', 'confirmed', 1100.00, 'counter', 110),
(10, 3, '2025-06-10', '21:00:00', 'cancelled', 800.00,  'online',  0);


-- ────────────────────────────────────────────────────────────
-- PAYMENT
-- Dates now match their booking dates
-- ────────────────────────────────────────────────────────────

INSERT INTO `payment` (`payment_id`, `booking_id`, `payment_method`, `payment_amount`, `payment_date`, `payment_time`, `payment_status`) VALUES
(1,  1,  'esewa',       850.00,  '2026-05-19', '10:25:00', 'completed'),
(2,  2,  'khalti',      1200.00, '2026-05-19', '14:10:00', 'pending'),
(3,  3,  'fonepay',     500.00,  '2026-05-10', '08:55:00', 'completed'),
(4,  4,  'esewa',       650.00,  '2026-05-19', '19:40:00', 'completed'),
(5,  5,  'khalti',      1500.00, '2026-05-12', '10:55:00', 'completed'),
(6,  6,  'esewa',       700.00,  '2026-05-19', '15:55:00', 'completed'),
(7,  7,  'fonepay',     950.00,  '2026-05-20', '20:25:00', 'completed'),
(8,  8,  'khalti',      600.00,  '2026-05-21', '12:55:00', 'pending'),
(9,  9,  'esewa',       1100.00, '2026-05-15', '17:55:00', 'completed'),
(10, 10, 'khalti',      800.00,  '2025-06-10', '20:55:00', 'refunded');


-- ────────────────────────────────────────────────────────────
-- TICKET
-- Updated to use new seat_ids and new showtime_ids
-- ticket_type normalised to Standard / Premium / VIP
-- seat_ids chosen from Screen 1 (screen_id=1):
--   Standard: seat_ids 1–90, Premium: 91–162
-- seat_ids chosen from Screen 3 (screen_id=3):
--   Standard: 271–360, Premium: 361–450, VIP: 451–468
-- seat_ids chosen from Screen 4 (screen_id=4):
--   Standard: 469–558, Premium: 559–594
-- ────────────────────────────────────────────────────────────

INSERT INTO `ticket` (`ticket_id`, `booking_id`, `showtime_id`, `seat_id`, `ticket_type`, `ticket_status`, `issue_date`, `ticket_price`) VALUES
-- booking 1 (confirmed): showtime 1 (screen 1, movie 1), seats 1 & 2 (Standard, screen 1)
(1,  1,  1,   1,   'Standard', 'ACTIVE',    '2026-05-19', 500.00),
(2,  1,  1,   2,   'Standard', 'ACTIVE',    '2026-05-19', 500.00),
-- booking 2 (pending): showtime 4 (screen 2, movie 2), seats 91 & 92 (Premium, screen 1 → use screen 2 seats 253,254)
(3,  2,  4,   253, 'Premium',  'PENDING',   '2026-05-19', 675.00),
(4,  2,  4,   254, 'Premium',  'PENDING',   '2026-05-19', 675.00),
-- booking 3 (archive): showtime 7 (screen 3, movie 4), seat 271 (Standard, screen 3)
(5,  3,  7,   271, 'Standard', 'ARCHIVE',   '2026-05-10', 250.00),
-- booking 4 (confirmed): showtime 13 (screen 5, movie 2), seats 595 & 596 (Standard, screen 5)
(6,  4,  13,  595, 'Standard', 'ACTIVE',    '2026-05-19', 230.00),
(7,  4,  13,  596, 'Standard', 'ACTIVE',    '2026-05-19', 230.00),
-- booking 5 (confirmed): showtime 10 (screen 4, movie 1), Premium seats 559 & 560
(8,  5,  10,  559, 'Premium',  'ACTIVE',    '2026-05-12', 720.00),
(9,  5,  10,  560, 'Premium',  'ACTIVE',    '2026-05-12', 720.00),
-- booking 6 (confirmed): showtime 1 (screen 1), seats 3 & 4 (Standard)
(10, 6,  1,   3,   'Standard', 'ACTIVE',    '2026-05-19', 500.00),
(11, 6,  1,   4,   'Standard', 'ACTIVE',    '2026-05-19', 500.00),
-- booking 7 (confirmed): showtime 16 (screen 1, 2026-05-20, movie 4), seats 91 & 92 (Premium)
(12, 7,  16,  91,  'Premium',  'ACTIVE',    '2026-05-20', 750.00),
(13, 7,  16,  92,  'Premium',  'ACTIVE',    '2026-05-20', 750.00),
-- booking 8 (pending): showtime 19 (screen 2, 2026-05-20, movie 3), seat 163 (Standard, screen 2)
(14, 8,  19,  163, 'Standard', 'PENDING',   '2026-05-21', 450.00),
-- booking 9 (confirmed): showtime 7 (screen 3, movie 4), VIP seats 451 & 452
(15, 9,  7,   451, 'VIP',      'ACTIVE',    '2026-05-15', 500.00),
(16, 9,  7,   452, 'VIP',      'ACTIVE',    '2026-05-15', 500.00),
-- booking 10 (cancelled): no real showtime (past booking), seats from screen 5
(17, 10, 13,  684, 'Standard', 'CANCELLED', '2025-06-10', 230.00),
(18, 10, 13,  683, 'Standard', 'CANCELLED', '2025-06-10', 230.00);


-- ────────────────────────────────────────────────────────────
-- MEMBERSHIP (unchanged — points are fine)
-- ────────────────────────────────────────────────────────────

INSERT INTO `membership` (`membership_id`, `user_id`, `membership_type`, `membership_status`, `total_loyalty_points`, `discount_percentage`) VALUES
(1, 2, 'Elite',   'Active', 10000, 0.00),
(2, 3, 'Elite',   'Active', 100,   0.00),
(3, 4, 'STARTER', 'Active', 0,     0.00);


-- ────────────────────────────────────────────────────────────
-- FEEDBACK (unchanged)
-- ────────────────────────────────────────────────────────────

INSERT INTO `feedback` (`feedback_id`, `user_id`, `movie_id`, `rating`, `description`, `feedback_date`, `feedback_time`, `feedback_status`) VALUES
(1, 2, 1, 4, 'A fantastic sequel that lives up to the original! Great performances and witty dialogue.',          '2026-05-05', '12:00:00', 'approved'),
(2, 2, 2, 5, 'A masterfully crafted biography. Truly inspiring and emotionally powerful.',                        '2026-05-06', '15:00:00', 'approved'),
(3, 2, 3, 3, 'Decent horror experience but the story felt a bit predictable. The atmosphere was great though.',   '2026-05-19', '10:30:00', 'approved'),
(4, 2, 4, 5, 'Absolutely mind-blowing! Best sci-fi film in years. Kept me on the edge of my seat the entire time.', '2026-05-18', '09:00:00', 'approved'),
(5, 3, 1, 4, 'Loved the fashion, drama, and humor. A worthy follow-up to the first film.',                        '2026-05-07', '17:30:00', 'approved'),
(6, 3, 4, 5, 'Stunning visuals and a gripping storyline. A must-watch for sci-fi fans.',                          '2026-05-18', '19:00:00', 'pending');


-- ────────────────────────────────────────────────────────────
-- INDEXES
-- ────────────────────────────────────────────────────────────

ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `fk_booking_user` (`user_id`);

ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_id`),
  ADD KEY `fk_feedback_user` (`user_id`),
  ADD KEY `fk_feedback_movie` (`movie_id`);

ALTER TABLE `membership`
  ADD PRIMARY KEY (`membership_id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

ALTER TABLE `movie`
  ADD PRIMARY KEY (`movie_id`);

ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`),
  ADD UNIQUE KEY `booking_id` (`booking_id`);

ALTER TABLE `screen`
  ADD PRIMARY KEY (`screen_id`),
  ADD KEY `fk_screen_theatre` (`theatre_id`);

ALTER TABLE `seat`
  ADD PRIMARY KEY (`seat_id`),
  ADD KEY `fk_seat_screen` (`screen_id`);

ALTER TABLE `showtime`
  ADD PRIMARY KEY (`showtime_id`),
  ADD KEY `fk_showtime_screen` (`screen_id`),
  ADD KEY `fk_showtime_movie` (`movie_id`);

ALTER TABLE `theatre`
  ADD PRIMARY KEY (`theatre_id`),
  ADD UNIQUE KEY `email` (`email`);

ALTER TABLE `ticket`
  ADD PRIMARY KEY (`ticket_id`),
  ADD UNIQUE KEY `uq_showtime_seat` (`showtime_id`,`seat_id`),
  ADD KEY `fk_ticket_booking` (`booking_id`),
  ADD KEY `fk_ticket_seat` (`seat_id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);


-- ────────────────────────────────────────────────────────────
-- AUTO_INCREMENT
-- ────────────────────────────────────────────────────────────

ALTER TABLE `booking`    MODIFY `booking_id`    int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
ALTER TABLE `feedback`   MODIFY `feedback_id`   int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
ALTER TABLE `membership` MODIFY `membership_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
ALTER TABLE `movie`      MODIFY `movie_id`      int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
ALTER TABLE `payment`    MODIFY `payment_id`    int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
ALTER TABLE `screen`     MODIFY `screen_id`     int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
ALTER TABLE `seat`       MODIFY `seat_id`       int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=775;
ALTER TABLE `showtime`   MODIFY `showtime_id`   int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=106;
ALTER TABLE `theatre`    MODIFY `theatre_id`    int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
ALTER TABLE `ticket`     MODIFY `ticket_id`     int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
ALTER TABLE `users`      MODIFY `user_id`       int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;


-- ────────────────────────────────────────────────────────────
-- FOREIGN KEY CONSTRAINTS
-- ────────────────────────────────────────────────────────────

ALTER TABLE `booking`
  ADD CONSTRAINT `fk_booking_user`      FOREIGN KEY (`user_id`)     REFERENCES `users`    (`user_id`)     ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `feedback`
  ADD CONSTRAINT `fk_feedback_movie`    FOREIGN KEY (`movie_id`)    REFERENCES `movie`    (`movie_id`)    ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_feedback_user`     FOREIGN KEY (`user_id`)     REFERENCES `users`    (`user_id`)     ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `membership`
  ADD CONSTRAINT `fk_membership_user`   FOREIGN KEY (`user_id`)     REFERENCES `users`    (`user_id`)     ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `payment`
  ADD CONSTRAINT `fk_payment_booking`   FOREIGN KEY (`booking_id`)  REFERENCES `booking`  (`booking_id`)  ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `screen`
  ADD CONSTRAINT `fk_screen_theatre`    FOREIGN KEY (`theatre_id`)  REFERENCES `theatre`  (`theatre_id`)  ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `seat`
  ADD CONSTRAINT `fk_seat_screen`       FOREIGN KEY (`screen_id`)   REFERENCES `screen`   (`screen_id`)   ON DELETE CASCADE;

ALTER TABLE `showtime`
  ADD CONSTRAINT `fk_showtime_movie`    FOREIGN KEY (`movie_id`)    REFERENCES `movie`    (`movie_id`)    ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_showtime_screen`   FOREIGN KEY (`screen_id`)   REFERENCES `screen`   (`screen_id`)   ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `ticket`
  ADD CONSTRAINT `fk_ticket_booking`    FOREIGN KEY (`booking_id`)  REFERENCES `booking`  (`booking_id`)  ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ticket_seat`       FOREIGN KEY (`seat_id`)     REFERENCES `seat`     (`seat_id`)     ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ticket_showtime`   FOREIGN KEY (`showtime_id`) REFERENCES `showtime` (`showtime_id`) ON DELETE CASCADE ON UPDATE CASCADE;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
