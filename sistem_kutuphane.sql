-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 21, 2021 at 09:44 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sistem_kutuphane`
--

-- --------------------------------------------------------

--
-- Table structure for table `kitap`
--

CREATE TABLE `kitap` (
  `kitap_id` int(11) NOT NULL,
  `kutuphane_id` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_ad` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_yazar` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_yayimci` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_adet` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `kitap`
--

INSERT INTO `kitap` (`kitap_id`, `kutuphane_id`, `kitap_ad`, `kitap_yazar`, `kitap_yayimci`, `kitap_adet`) VALUES
(124, '11', 'A Kitap', 'A Yazar', 'A Yayınları', '1'),
(125, '11', 'B Kitap', 'B Yazar', 'B Yayınları', '4');

-- --------------------------------------------------------

--
-- Table structure for table `kitap_durum`
--

CREATE TABLE `kitap_durum` (
  `kitap_durum_id` int(11) NOT NULL,
  `kutuphane_id` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_id` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_ad` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_yazar` text COLLATE utf8_turkish_ci NOT NULL,
  `alis_tarihi` text COLLATE utf8_turkish_ci NOT NULL,
  `teslim_tarihi` text COLLATE utf8_turkish_ci NOT NULL,
  `ogrenci_id` text COLLATE utf8_turkish_ci NOT NULL,
  `ogrenci_ad` text COLLATE utf8_turkish_ci NOT NULL,
  `ogrenci_email` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `kitap_durum`
--

INSERT INTO `kitap_durum` (`kitap_durum_id`, `kutuphane_id`, `kitap_id`, `kitap_ad`, `kitap_yazar`, `alis_tarihi`, `teslim_tarihi`, `ogrenci_id`, `ogrenci_ad`, `ogrenci_email`) VALUES
(39, '11', '125', 'B Kitap', 'B Yazar', '21/04/2021', '22/04/2021', '17', 'A Öğrenci', 'ogrenci@ogrenci.com');

-- --------------------------------------------------------

--
-- Table structure for table `kitap_talep`
--

CREATE TABLE `kitap_talep` (
  `kitap_talep_id` int(11) NOT NULL,
  `kutuphane_id` text COLLATE utf8_turkish_ci NOT NULL,
  `ogrenci_id` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_ad` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_yazar` text COLLATE utf8_turkish_ci NOT NULL,
  `kitap_yayimci` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `kitap_talep`
--

INSERT INTO `kitap_talep` (`kitap_talep_id`, `kutuphane_id`, `ogrenci_id`, `kitap_ad`, `kitap_yazar`, `kitap_yayimci`) VALUES
(6, '11', '17', 'C Kitap', 'C Yazar', 'C Yayınları');

-- --------------------------------------------------------

--
-- Table structure for table `kutuphane`
--

CREATE TABLE `kutuphane` (
  `kutuphane_id` int(11) NOT NULL,
  `kutuphane_ad` text COLLATE utf8_turkish_ci NOT NULL,
  `kutuphane_email` text COLLATE utf8_turkish_ci NOT NULL,
  `kutuphane_adres` text COLLATE utf8_turkish_ci NOT NULL,
  `kutuphane_tel` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `kutuphane`
--

INSERT INTO `kutuphane` (`kutuphane_id`, `kutuphane_ad`, `kutuphane_email`, `kutuphane_adres`, `kutuphane_tel`) VALUES
(11, 'A Kütüphanesi', 'akutuphane@edu.tr', 'İstanbul', '01112233');

-- --------------------------------------------------------

--
-- Table structure for table `ogrenci`
--

CREATE TABLE `ogrenci` (
  `ogrenci_id` int(11) NOT NULL,
  `ogrenci_ad` text COLLATE utf8_turkish_ci NOT NULL,
  `ogrenci_email` text COLLATE utf8_turkish_ci NOT NULL,
  `ogrenci_kullanici_adi` text COLLATE utf8_turkish_ci NOT NULL,
  `ogrenci_sifre` text COLLATE utf8_turkish_ci NOT NULL,
  `kutuphane_id` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `ogrenci`
--

INSERT INTO `ogrenci` (`ogrenci_id`, `ogrenci_ad`, `ogrenci_email`, `ogrenci_kullanici_adi`, `ogrenci_sifre`, `kutuphane_id`) VALUES
(17, 'A Öğrenci', 'ogrenci@ogrenci.com', 'ogrenci', 'ogrenci', '11');

-- --------------------------------------------------------

--
-- Table structure for table `personel`
--

CREATE TABLE `personel` (
  `personel_id` int(11) NOT NULL,
  `kutuphane_id` text COLLATE utf8_turkish_ci NOT NULL,
  `personel_ad` text COLLATE utf8_turkish_ci NOT NULL,
  `personel_kullanici_adi` text COLLATE utf8_turkish_ci NOT NULL,
  `personel_sifre` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `personel`
--

INSERT INTO `personel` (`personel_id`, `kutuphane_id`, `personel_ad`, `personel_kullanici_adi`, `personel_sifre`) VALUES
(13, '11', 'A Personel', 'apersonel', 'apersonel');

-- --------------------------------------------------------

--
-- Table structure for table `yonetici`
--

CREATE TABLE `yonetici` (
  `yonetici_id` text COLLATE utf8_turkish_ci NOT NULL,
  `yonetici_sifre` text COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Dumping data for table `yonetici`
--

INSERT INTO `yonetici` (`yonetici_id`, `yonetici_sifre`) VALUES
('admin', 'admin'),
('admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kitap`
--
ALTER TABLE `kitap`
  ADD PRIMARY KEY (`kitap_id`);

--
-- Indexes for table `kitap_durum`
--
ALTER TABLE `kitap_durum`
  ADD PRIMARY KEY (`kitap_durum_id`);

--
-- Indexes for table `kitap_talep`
--
ALTER TABLE `kitap_talep`
  ADD PRIMARY KEY (`kitap_talep_id`);

--
-- Indexes for table `kutuphane`
--
ALTER TABLE `kutuphane`
  ADD PRIMARY KEY (`kutuphane_id`);

--
-- Indexes for table `ogrenci`
--
ALTER TABLE `ogrenci`
  ADD PRIMARY KEY (`ogrenci_id`);

--
-- Indexes for table `personel`
--
ALTER TABLE `personel`
  ADD PRIMARY KEY (`personel_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kitap`
--
ALTER TABLE `kitap`
  MODIFY `kitap_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=126;

--
-- AUTO_INCREMENT for table `kitap_durum`
--
ALTER TABLE `kitap_durum`
  MODIFY `kitap_durum_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `kitap_talep`
--
ALTER TABLE `kitap_talep`
  MODIFY `kitap_talep_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `kutuphane`
--
ALTER TABLE `kutuphane`
  MODIFY `kutuphane_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `ogrenci`
--
ALTER TABLE `ogrenci`
  MODIFY `ogrenci_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `personel`
--
ALTER TABLE `personel`
  MODIFY `personel_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
