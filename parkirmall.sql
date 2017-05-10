-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 10, 2017 at 02:20 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parkirmall`
--

-- --------------------------------------------------------

--
-- Table structure for table `blok`
--

CREATE TABLE `blok` (
  `id_blok` varchar(5) NOT NULL,
  `nama_blok` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `blok`
--

INSERT INTO `blok` (`id_blok`, `nama_blok`) VALUES
('A1', 'Area 1 Motor'),
('A2', 'Area 2 Motor'),
('B1', 'Area 1 Mobil'),
('B2', 'Area 2 Mobil'),
('OUT', 'Keluar');

-- --------------------------------------------------------

--
-- Table structure for table `karcis_mobil`
--

CREATE TABLE `karcis_mobil` (
  `id_karcis` int(255) NOT NULL,
  `nopol_mobil` varchar(10) NOT NULL,
  `jenis_mbl` varchar(20) NOT NULL,
  `tgl_parkir` date NOT NULL,
  `blok_parkir` varchar(5) DEFAULT NULL,
  `jam_masuk_mbl` time NOT NULL,
  `jam_keluar_mbl` time DEFAULT NULL,
  `tarif` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karcis_mobil`
--

INSERT INTO `karcis_mobil` (`id_karcis`, `nopol_mobil`, `jenis_mbl`, `tgl_parkir`, `blok_parkir`, `jam_masuk_mbl`, `jam_keluar_mbl`, `tarif`) VALUES
(1, 'KT2345KL', 'Mobil Sedan', '2017-05-01', 'OUT', '01:03:39', '01:04:11', 4000),
(2, 'KT2222ML', 'Mobil SUV', '2017-05-01', 'OUT', '01:07:51', '05:00:00', 16000),
(3, 'KT4545NN', 'Mobil Jeep', '2017-05-01', 'OUT', '01:38:30', '02:52:25', 8000),
(4, 'KT22333WA', 'Mobil Jeep', '2017-05-01', 'OUT', '17:47:21', '18:17:39', 4000),
(5, 'KT1231BB', 'Mobil Jeep', '2017-05-03', 'OUT', '03:19:46', '03:20:02', 4000);

-- --------------------------------------------------------

--
-- Table structure for table `karcis_motor`
--

CREATE TABLE `karcis_motor` (
  `id_karcis` int(255) NOT NULL,
  `nopol_motor` varchar(10) NOT NULL,
  `jenis_mtr` varchar(20) NOT NULL,
  `tgl_parkir` date NOT NULL,
  `blok_parkir` varchar(5) DEFAULT NULL,
  `jam_masuk_mtr` time NOT NULL,
  `jam_keluar_mtr` time DEFAULT NULL,
  `tarif` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karcis_motor`
--

INSERT INTO `karcis_motor` (`id_karcis`, `nopol_motor`, `jenis_mtr`, `tgl_parkir`, `blok_parkir`, `jam_masuk_mtr`, `jam_keluar_mtr`, `tarif`) VALUES
(1, 'KT2272EAD', 'Motor Matic', '2017-04-27', 'OUT', '08:56:29', '09:27:19', 2000),
(2, 'KT2222BL', 'Motor Matic', '2017-04-27', 'OUT', '09:13:23', '22:14:26', 8000),
(3, 'KT1921UI', 'Motor Matic', '2017-04-27', 'OUT', '13:00:46', '23:25:16', 8000),
(4, 'KT9091KL', 'Motor Matic', '2017-04-27', 'OUT', '13:05:01', '23:51:20', 8000),
(5, 'KT1231ML', 'Motor Sport', '2017-05-10', 'OUT', '19:44:38', '19:44:45', 2000),
(6, 'KT9989KK', 'Motor Bajaj', '2017-05-10', 'OUT', '20:09:49', '20:09:59', 2000);

-- --------------------------------------------------------

--
-- Table structure for table `tipe_kendaraan`
--

CREATE TABLE `tipe_kendaraan` (
  `id_tipe` int(11) NOT NULL,
  `jenis_kendaraan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tipe_kendaraan`
--

INSERT INTO `tipe_kendaraan` (`id_tipe`, `jenis_kendaraan`) VALUES
(1, 'Motor Matic'),
(2, 'Motor Bebek'),
(3, 'Motor Sport'),
(4, 'Mobil Sedan'),
(5, 'Mobil SUV'),
(6, 'Mobil Jeep'),
(7, 'Motor Roda 3'),
(8, 'Mobil Minibus 2'),
(9, 'Motor Bajaj'),
(10, 'Motor Boy'),
(11, 'Mobil Balap');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `level`) VALUES
(1, 'admin', 'admin', 0),
(2, 'ferry', 'ferry111', 1),
(3, 'indra', 'indra121', 1),
(4, 'rizal', 'rizal105', 1),
(5, 'maya', 'maya123', 1),
(6, 'maya2', 'maya123', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blok`
--
ALTER TABLE `blok`
  ADD PRIMARY KEY (`id_blok`);

--
-- Indexes for table `karcis_mobil`
--
ALTER TABLE `karcis_mobil`
  ADD PRIMARY KEY (`id_karcis`),
  ADD KEY `blok_parkir` (`blok_parkir`),
  ADD KEY `jenis_mbl` (`jenis_mbl`);

--
-- Indexes for table `karcis_motor`
--
ALTER TABLE `karcis_motor`
  ADD PRIMARY KEY (`id_karcis`),
  ADD KEY `blok_parkir` (`blok_parkir`),
  ADD KEY `jenis_mtr` (`jenis_mtr`);

--
-- Indexes for table `tipe_kendaraan`
--
ALTER TABLE `tipe_kendaraan`
  ADD PRIMARY KEY (`id_tipe`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `karcis_mobil`
--
ALTER TABLE `karcis_mobil`
  MODIFY `id_karcis` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `karcis_motor`
--
ALTER TABLE `karcis_motor`
  MODIFY `id_karcis` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT for table `tipe_kendaraan`
--
ALTER TABLE `tipe_kendaraan`
  MODIFY `id_tipe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `karcis_mobil`
--
ALTER TABLE `karcis_mobil`
  ADD CONSTRAINT `karcis_mobil_ibfk_1` FOREIGN KEY (`blok_parkir`) REFERENCES `blok` (`id_blok`);

--
-- Constraints for table `karcis_motor`
--
ALTER TABLE `karcis_motor`
  ADD CONSTRAINT `karcis_motor_ibfk_1` FOREIGN KEY (`blok_parkir`) REFERENCES `blok` (`id_blok`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
