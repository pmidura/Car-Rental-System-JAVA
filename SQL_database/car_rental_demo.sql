-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 12 Cze 2022, 14:26
-- Wersja serwera: 10.4.24-MariaDB
-- Wersja PHP: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `car_rental`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `admins`
--

CREATE TABLE `admins` (
  `admin_id` int(3) NOT NULL,
  `role_id` int(3) NOT NULL,
  `login` varchar(30) NOT NULL,
  `password` char(32) NOT NULL,
  `admin_name` varchar(20) NOT NULL,
  `admin_surname` varchar(20) NOT NULL,
  `admin_phone` char(9) NOT NULL,
  `pesel` char(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `admins`
--

INSERT INTO `admins` (`admin_id`, `role_id`, `login`, `password`, `admin_name`, `admin_surname`, `admin_phone`, `pesel`) VALUES
(1, 1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Patryk', 'Midura', '665899755', '95021204512');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cars`
--

CREATE TABLE `cars` (
  `car_id` int(3) NOT NULL,
  `rental_id` int(3) NOT NULL,
  `car_registration` varchar(8) NOT NULL,
  `car_brand` varchar(30) NOT NULL,
  `car_model` varchar(30) NOT NULL,
  `car_production` year(4) NOT NULL,
  `car_price` float NOT NULL,
  `availability` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `cars`
--

INSERT INTO `cars` (`car_id`, `rental_id`, `car_registration`, `car_brand`, `car_model`, `car_production`, `car_price`, `availability`) VALUES
(1, 1, 'RJA80923', 'Audi', 'A4 B9', 2020, 500.25, 1),
(2, 1, 'RZ453123', 'Porsche', 'Panamera', 2019, 624.99, 1),
(3, 2, 'WZ4500D', 'Lamborghini', 'Aventador', 2022, 2499, 1),
(4, 2, 'WB89021', 'BMW', 'M5', 2016, 499.95, 0),
(5, 3, 'PZ90876', 'Polonez', 'Caro', 1998, 95, 1),
(6, 3, 'RSA80921', 'Fiat', '126P', 1989, 129.99, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `loans`
--

CREATE TABLE `loans` (
  `loan_id` int(3) NOT NULL,
  `rental_id` int(3) NOT NULL,
  `car_id` int(3) NOT NULL,
  `client_name` varchar(30) NOT NULL,
  `client_surname` varchar(30) NOT NULL,
  `client_pesel` char(11) NOT NULL,
  `client_phone` char(9) NOT NULL,
  `period` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `loans`
--

INSERT INTO `loans` (`loan_id`, `rental_id`, `car_id`, `client_name`, `client_surname`, `client_pesel`, `client_phone`, `period`) VALUES
(5, 2, 4, 'Janek', 'Kowalski', '95052211023', '693205566', 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `loans_cars`
--

CREATE TABLE `loans_cars` (
  `loan_id` int(3) NOT NULL,
  `car_id` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rentals`
--

CREATE TABLE `rentals` (
  `rental_id` int(3) NOT NULL,
  `user_id` int(3) NOT NULL,
  `rental_name` varchar(30) NOT NULL,
  `rental_city` varchar(30) NOT NULL,
  `rental_street` varchar(60) NOT NULL,
  `rental_workers` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `rentals`
--

INSERT INTO `rentals` (`rental_id`, `user_id`, `rental_name`, `rental_city`, `rental_street`, `rental_workers`) VALUES
(1, 1, 'Panek Car Sharing', 'Warszawa', 'Al. Jerozolimskie 15A', 2),
(2, 3, 'Autoshare', 'Poznan', 'Wielkopolska 18A/5', 1),
(3, 2, 'Wiejskie auteczka', 'Betlejem', 'Francuska 12', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rentals_users`
--

CREATE TABLE `rentals_users` (
  `rental_id` int(3) NOT NULL,
  `user_id` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `roles`
--

CREATE TABLE `roles` (
  `role_id` int(3) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `roles`
--

INSERT INTO `roles` (`role_id`, `role`) VALUES
(1, 'admin'),
(2, 'employee'),
(3, 'manager');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `user_id` int(3) NOT NULL,
  `role_id` int(3) NOT NULL,
  `rental_id` int(3) NOT NULL,
  `login` varchar(30) NOT NULL,
  `password` char(32) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_surname` varchar(20) NOT NULL,
  `user_phone` char(9) NOT NULL,
  `pesel` char(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`user_id`, `role_id`, `rental_id`, `login`, `password`, `user_name`, `user_surname`, `user_phone`, `pesel`) VALUES
(1, 3, 1, 'piotrek1', 'ae22fa65c51029cc918c876b05da114d', 'Piotr', 'Janik', '669589652', '98021303265'),
(2, 3, 3, 'maciek1', 'a1762608a657830bfac6833a43f66509', 'Maciej', 'Ziolkowski', '602325988', '97062105879'),
(3, 3, 2, 'adam1', 'ca061eb8ef438d81614b6675e1bc4e52', 'Adam', 'Janicki', '654325879', '95010325642'),
(4, 2, 1, 'wladek1', 'a1a9351648e89b4abc3c52ed84570304', 'Wladyslaw', 'Korzonka', '689203412', '95102005689'),
(5, 2, 2, 'jaska1', '1d61a213ca00cfd0a62f1582bd2e3ea', 'Janina', 'Nowobogacka', '693203566', '95012305623'),
(7, 2, 1, 'kacper1', '3e17000c430f8c8534f8c407a8c9ecf7', 'Kacper', 'Wojtaszek', '956089599', '95021402332'),
(8, 2, 3, 'robert1', 'd9ea06996245535b0789794ce2be5d9', 'Robert', 'Maklowicz', '665233609', '94032050236');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `admin_phone` (`admin_phone`),
  ADD UNIQUE KEY `pesel` (`pesel`),
  ADD KEY `FK_Admins_Roles` (`role_id`);

--
-- Indeksy dla tabeli `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`car_id`),
  ADD UNIQUE KEY `car_registration` (`car_registration`),
  ADD KEY `FK_Cars_Rentals` (`rental_id`);

--
-- Indeksy dla tabeli `loans`
--
ALTER TABLE `loans`
  ADD PRIMARY KEY (`loan_id`),
  ADD UNIQUE KEY `client_pesel` (`client_pesel`),
  ADD UNIQUE KEY `client_phone` (`client_phone`);

--
-- Indeksy dla tabeli `loans_cars`
--
ALTER TABLE `loans_cars`
  ADD PRIMARY KEY (`loan_id`,`car_id`),
  ADD KEY `FK_Cars_Loans_2` (`car_id`);

--
-- Indeksy dla tabeli `rentals`
--
ALTER TABLE `rentals`
  ADD PRIMARY KEY (`rental_id`);

--
-- Indeksy dla tabeli `rentals_users`
--
ALTER TABLE `rentals_users`
  ADD KEY `FK_Rentals_Users_1` (`rental_id`),
  ADD KEY `FK_Rentals_Users_2` (`user_id`);

--
-- Indeksy dla tabeli `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `pesel` (`pesel`) USING BTREE,
  ADD UNIQUE KEY `user_phone` (`user_phone`) USING BTREE,
  ADD KEY `FK_Users_Roles` (`role_id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `admins`
--
ALTER TABLE `admins`
  MODIFY `admin_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `cars`
--
ALTER TABLE `cars`
  MODIFY `car_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT dla tabeli `loans`
--
ALTER TABLE `loans`
  MODIFY `loan_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `rentals`
--
ALTER TABLE `rentals`
  MODIFY `rental_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `admins`
--
ALTER TABLE `admins`
  ADD CONSTRAINT `FK_Admins_Roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `cars`
--
ALTER TABLE `cars`
  ADD CONSTRAINT `FK_Cars_Rentals` FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`rental_id`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `loans_cars`
--
ALTER TABLE `loans_cars`
  ADD CONSTRAINT `FK_Cars_Loans_1` FOREIGN KEY (`loan_id`) REFERENCES `loans` (`loan_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Cars_Loans_2` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `rentals_users`
--
ALTER TABLE `rentals_users`
  ADD CONSTRAINT `FK_Rentals_Users_1` FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`rental_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Rentals_Users_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK_Users_Roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
