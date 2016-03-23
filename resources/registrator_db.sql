-- phpMyAdmin SQL Dump
-- version 3.5.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 02, 2015 at 07:39 PM
-- Server version: 5.5.25a
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `registrator_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE IF NOT EXISTS `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `building` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `district` varchar(255) DEFAULT NULL,
  `flat` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) NOT NULL,
  `region` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FK_7rod8a71yep5vxasb0ms3osbg` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `area`
--

CREATE TABLE IF NOT EXISTS `area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `number_of_point` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`area_id`),
  KEY `FK_j05enuc6gftyec9v9m07880bs` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `discrete_values`
--

CREATE TABLE IF NOT EXISTS `discrete_values` (
  `discrete_values_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `unit_name` varchar(255) NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  PRIMARY KEY (`discrete_values_id`),
  KEY `FK_l6o6xiui7mpn52s6laoacahjc` (`resource_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `discrete_values`
--

INSERT INTO `discrete_values` (`discrete_values_id`, `description`, `unit_name`, `resource_type_id`) VALUES
(1, 'type 1 dparam 1', 'Hz', 1),
(2, 'type 1 dparam 2 ', 'm', 1),
(3, 'type 2 dparam2', 'm', 2);

-- --------------------------------------------------------

--
-- Table structure for table `inquiry_list`
--

CREATE TABLE IF NOT EXISTS `inquiry_list` (
  `inquiry_list_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `inquiry_type` enum('INPUT','OUTPUT') NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  PRIMARY KEY (`inquiry_list_id`),
  KEY `FK_cqtn406s2sbvribvsveeln8k1` (`to_user_id`),
  KEY `FK_j8gyy6aabddkoxp4jivw8fini` (`resource_id`),
  KEY `FK_37qp17x0dnyms8oxyo33jigpb` (`from_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `lines_size`
--

CREATE TABLE IF NOT EXISTS `lines_size` (
  `lines_size_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `unit_name` varchar(255) NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  PRIMARY KEY (`lines_size_id`),
  KEY `FK_jtxprxsxb34pr1ucids6gl9rs` (`resource_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `lines_size`
--

INSERT INTO `lines_size` (`lines_size_id`, `description`, `unit_name`, `resource_type_id`) VALUES
(1, 'type 1 lparam 1', 'pcs', 1),
(2, 'type 2 lparam 1', '', 2),
(3, 'type 2 lparam2', 'mln pcs', 2);

-- --------------------------------------------------------

--
-- Table structure for table `list_of_resouces`
--

CREATE TABLE IF NOT EXISTS `list_of_resouces` (
  `resources_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `identifier` varchar(255) NOT NULL,
  `reason_inclusion` varchar(255) NOT NULL,
  `status` enum('ACTIVE','UNCHECKED','DENIDED','OBSOLETE') NOT NULL,
  `tome_id` int(11) NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  `registrator_id` int(11) NOT NULL,
  PRIMARY KEY (`resources_id`),
  KEY `FK_3dk2u1o6r3f41knbp4bw0u4e2` (`tome_id`),
  KEY `FK_764t63m3e5fl8seck12tyr8j` (`resource_type_id`),
  KEY `FK_2bflnodo3qtvgos2ou0s9sp9` (`registrator_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `list_of_resouces`
--

INSERT INTO `list_of_resouces` (`resources_id`, `date`, `description`, `identifier`, `reason_inclusion`, `status`, `tome_id`, `resource_type_id`, `registrator_id`) VALUES
(3, '2015-12-01 00:00:00', 'res 1', '79000/001', 'I want', 'ACTIVE', 1, 1, 1),
(4, '2015-12-01 00:00:00', 'res 2', '79000/002', 'Don''t know', 'ACTIVE', 1, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `passport_data`
--

CREATE TABLE IF NOT EXISTS `passport_data` (
  `passport_data_id` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `published_by_data` varchar(255) NOT NULL,
  `seria` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`passport_data_id`),
  KEY `FK_b3ufslic16u2m3j35ksfp0ivb` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resource_parameters`
--

CREATE TABLE IF NOT EXISTS `resource_parameters` (
  `resource_parameters_id` int(11) NOT NULL AUTO_INCREMENT,
  `parameter_id` int(11) NOT NULL,
  `resource_type_id` int(11) NOT NULL,
  PRIMARY KEY (`resource_parameters_id`),
  KEY `FK_tc2co2gdknt0kyt43e0wwejh9` (`parameter_id`),
  KEY `FK_1unvdmfastc818i00xvmglchl` (`resource_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resource_types`
--

CREATE TABLE IF NOT EXISTS `resource_types` (
  `resource_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`resource_type_id`),
  UNIQUE KEY `UK_5fwgdwi603f06mf65x5fhv42a` (`type_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `resource_types`
--

INSERT INTO `resource_types` (`resource_type_id`, `type_name`) VALUES
(1, 'resType1'),
(2, 'resType2');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `name` enum('USER','REGISTRATOR','ADMIN') NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`role_id`, `description`, `name`) VALUES
(1, 'description', 'REGISTRATOR');

-- --------------------------------------------------------

--
-- Table structure for table `store_of_discrete_values`
--

CREATE TABLE IF NOT EXISTS `store_of_discrete_values` (
  `store_of_discrete_values_id` int(11) NOT NULL AUTO_INCREMENT,
  `value` double NOT NULL,
  `discrete_values_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`store_of_discrete_values_id`),
  KEY `FK_4meqlvq5cximihuaceblx2eat` (`discrete_values_id`),
  KEY `FK_nhytl29kvxo7ydlx22hb2cmkr` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `store_of_line_sizes`
--

CREATE TABLE IF NOT EXISTS `store_of_line_sizes` (
  `store_of_line_sizes_id` int(11) NOT NULL AUTO_INCREMENT,
  `maximal_value` double NOT NULL,
  `minimal_value` double NOT NULL,
  `lines_size_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`store_of_line_sizes_id`),
  KEY `FK_3xyhv8kkfw07fteqogw9quiv5` (`lines_size_id`),
  KEY `FK_b3slij1w8e86itel464agc7l2` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tomes`
--

CREATE TABLE IF NOT EXISTS `tomes` (
  `tome_id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(255) NOT NULL,
  `registrator_id` int(11) NOT NULL,
  PRIMARY KEY (`tome_id`),
  KEY `FK_pnsd367apavsotihxdt51mo7v` (`registrator_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tomes`
--

INSERT INTO `tomes` (`tome_id`, `identifier`, `registrator_id`) VALUES
(1, 'tome 1', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `middle_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` enum('BLOCK','ACTIVE','INACTIVE') NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`),
  KEY `FK_krvotbtiqhudlkamvlpaqus0t` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email`, `first_name`, `last_name`, `login`, `middle_name`, `password`, `status`, `role_id`) VALUES
(1, 'email.com', 'FirstName', 'LastName', 'login', 'MiddleName', 'password', 'BLOCK', 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `FK_7rod8a71yep5vxasb0ms3osbg` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `area`
--
ALTER TABLE `area`
  ADD CONSTRAINT `FK_j05enuc6gftyec9v9m07880bs` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`);

--
-- Constraints for table `discrete_values`
--
ALTER TABLE `discrete_values`
  ADD CONSTRAINT `FK_l6o6xiui7mpn52s6laoacahjc` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`);

--
-- Constraints for table `inquiry_list`
--
ALTER TABLE `inquiry_list`
  ADD CONSTRAINT `FK_37qp17x0dnyms8oxyo33jigpb` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK_cqtn406s2sbvribvsveeln8k1` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK_j8gyy6aabddkoxp4jivw8fini` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`);

--
-- Constraints for table `lines_size`
--
ALTER TABLE `lines_size`
  ADD CONSTRAINT `FK_jtxprxsxb34pr1ucids6gl9rs` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`);

--
-- Constraints for table `list_of_resouces`
--
ALTER TABLE `list_of_resouces`
  ADD CONSTRAINT `FK_2bflnodo3qtvgos2ou0s9sp9` FOREIGN KEY (`registrator_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK_3dk2u1o6r3f41knbp4bw0u4e2` FOREIGN KEY (`tome_id`) REFERENCES `tomes` (`tome_id`),
  ADD CONSTRAINT `FK_764t63m3e5fl8seck12tyr8j` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`);

--
-- Constraints for table `passport_data`
--
ALTER TABLE `passport_data`
  ADD CONSTRAINT `FK_b3ufslic16u2m3j35ksfp0ivb` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `resource_parameters`
--
ALTER TABLE `resource_parameters`
  ADD CONSTRAINT `FK_1unvdmfastc818i00xvmglchl` FOREIGN KEY (`resource_type_id`) REFERENCES `resource_types` (`resource_type_id`),
  ADD CONSTRAINT `FK_tc2co2gdknt0kyt43e0wwejh9` FOREIGN KEY (`parameter_id`) REFERENCES `lines_size` (`lines_size_id`);

--
-- Constraints for table `store_of_discrete_values`
--
ALTER TABLE `store_of_discrete_values`
  ADD CONSTRAINT `FK_nhytl29kvxo7ydlx22hb2cmkr` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`),
  ADD CONSTRAINT `FK_4meqlvq5cximihuaceblx2eat` FOREIGN KEY (`discrete_values_id`) REFERENCES `discrete_values` (`discrete_values_id`);

--
-- Constraints for table `store_of_line_sizes`
--
ALTER TABLE `store_of_line_sizes`
  ADD CONSTRAINT `FK_b3slij1w8e86itel464agc7l2` FOREIGN KEY (`resource_id`) REFERENCES `list_of_resouces` (`resources_id`),
  ADD CONSTRAINT `FK_3xyhv8kkfw07fteqogw9quiv5` FOREIGN KEY (`lines_size_id`) REFERENCES `lines_size` (`lines_size_id`);

--
-- Constraints for table `tomes`
--
ALTER TABLE `tomes`
  ADD CONSTRAINT `FK_pnsd367apavsotihxdt51mo7v` FOREIGN KEY (`registrator_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK_krvotbtiqhudlkamvlpaqus0t` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
