/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 10.1.37-MariaDB : Database - bus_station
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bus_station` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `bus_station`;

/*Table structure for table `departure` */

DROP TABLE IF EXISTS `departure`;

CREATE TABLE `departure` (
  `lineID` bigint(10) NOT NULL,
  `departureID` bigint(10) NOT NULL,
  `departureTime` datetime DEFAULT NULL,
  `arrivalTime` datetime DEFAULT NULL,
  `maxPassengerNumber` int(3) DEFAULT NULL,
  `passengerNumber` int(3) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `platform` int(2) DEFAULT NULL,
  PRIMARY KEY (`lineID`,`departureID`),
  CONSTRAINT `departure_ibfk_1` FOREIGN KEY (`lineID`) REFERENCES `line` (`lineID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `departure` */

insert  into `departure`(`lineID`,`departureID`,`departureTime`,`arrivalTime`,`maxPassengerNumber`,`passengerNumber`,`price`,`platform`) values 
(1,1,'2022-09-05 18:00:00','2022-09-05 22:16:00',121,2,4000,11),
(1,2,'2022-09-07 18:00:00','2022-09-07 22:16:00',32,0,4000,2),
(2,1,'2022-08-22 17:18:01','2022-08-22 22:18:04',55,3,3200,8),
(3,2,'2022-08-26 22:00:00','2022-08-27 02:10:00',6,6,3800,4),
(4,1,'2022-09-06 20:30:00','2022-09-06 21:45:00',5,0,2000,13),
(4,2,'2022-10-22 14:00:00','2022-10-22 15:45:00',50,0,2200,2),
(8,1,'2022-10-10 20:00:00','2022-10-10 22:00:00',3,0,1200,9),
(9,1,'2022-10-16 19:00:00','2022-10-16 20:00:00',50,0,1100,11),
(10,1,'2022-10-22 14:00:00','2022-10-22 16:00:00',50,0,3000,1),
(10,2,'2022-10-22 15:00:00','2022-10-22 16:00:00',20,0,2000,2);

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `employeeID` bigint(11) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employeeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

insert  into `employee`(`employeeID`,`firstName`,`lastName`,`userName`,`password`) values 
(1,'Ana','Anic','ana','a1'),
(2,'Vladimir','Jecic','vladimir','v1'),
(3,'Test','User','t','t');

/*Table structure for table `line` */

DROP TABLE IF EXISTS `line`;

CREATE TABLE `line` (
  `lineID` bigint(10) NOT NULL AUTO_INCREMENT,
  `lineName` varchar(255) DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `travelTime` varchar(255) DEFAULT NULL,
  `employeeID` bigint(10) NOT NULL,
  `firstStationID` bigint(10) NOT NULL,
  `lastStationID` bigint(10) NOT NULL,
  PRIMARY KEY (`lineID`),
  KEY `line_ibfk_1` (`employeeID`),
  KEY `line_ibfk_2` (`firstStationID`),
  KEY `line_ibfk_3` (`lastStationID`),
  CONSTRAINT `line_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON UPDATE CASCADE,
  CONSTRAINT `line_ibfk_2` FOREIGN KEY (`firstStationID`) REFERENCES `station` (`stationID`) ON UPDATE CASCADE,
  CONSTRAINT `line_ibfk_3` FOREIGN KEY (`lastStationID`) REFERENCES `station` (`stationID`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `line` */

insert  into `line`(`lineID`,`lineName`,`distance`,`travelTime`,`employeeID`,`firstStationID`,`lastStationID`) values 
(1,'Subotica->Nis',430,'4h and 16min',3,1,7),
(2,'Zajecar->Novi Sad',332.5,'5h and 00min',3,6,3),
(3,'Beograd->Nis',350.5,'4h and 10min',3,4,7),
(4,'NoviSad->Beograd',100,'1h and 15min',3,3,4),
(6,'Novi Sad->Leskovac',600,'3h and 45min',3,3,8),
(7,'Novi Sad->Jagodina',250,'3h and 15min',3,3,9),
(8,'Kragujevac->Nis',150,'1h and 45min',3,5,7),
(9,'Subotica->Sombor',300,'1h and 00min',2,1,13),
(10,'Beograd->Leskovac',130,'0h and 28min',3,4,8);

/*Table structure for table `station` */

DROP TABLE IF EXISTS `station`;

CREATE TABLE `station` (
  `stationID` bigint(10) NOT NULL AUTO_INCREMENT,
  `stationName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`stationID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `station` */

insert  into `station`(`stationID`,`stationName`) values 
(1,'Subotica'),
(3,'Novi Sad'),
(4,'Beograd'),
(5,'Kragujevac'),
(6,'Zajecar'),
(7,'Nis'),
(8,'Leskovac'),
(9,'Jagodina'),
(10,'Bor'),
(11,'Uzice'),
(12,'Zlatibor'),
(13,'Sombor'),
(14,'Lazarevac');

/*Table structure for table `stop` */

DROP TABLE IF EXISTS `stop`;

CREATE TABLE `stop` (
  `lineID` bigint(10) NOT NULL,
  `stationID` bigint(10) NOT NULL,
  `stopNumber` int(2) DEFAULT NULL,
  PRIMARY KEY (`lineID`,`stationID`),
  KEY `stop_ibfk_4` (`stationID`),
  CONSTRAINT `stop_ibfk_3` FOREIGN KEY (`lineID`) REFERENCES `line` (`lineID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stop_ibfk_4` FOREIGN KEY (`stationID`) REFERENCES `station` (`stationID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stop` */

insert  into `stop`(`lineID`,`stationID`,`stopNumber`) values 
(1,1,1),
(1,3,2),
(1,4,3),
(1,6,4),
(1,7,5),
(2,3,3),
(2,4,2),
(2,6,1),
(3,4,1),
(3,5,2),
(3,6,3),
(3,7,4),
(4,3,1),
(4,4,2),
(6,3,1),
(6,4,2),
(6,8,3),
(7,3,1),
(7,4,2),
(7,9,3),
(8,5,1),
(8,7,2),
(9,1,1),
(9,13,2),
(10,4,1),
(10,8,2);

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `lineID` bigint(10) NOT NULL,
  `departureID` bigint(10) NOT NULL,
  `seatNumber` int(3) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `exitStationID` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`lineID`,`departureID`,`seatNumber`),
  KEY `departureID` (`departureID`,`lineID`),
  KEY `lastStationID` (`exitStationID`),
  KEY `lineID` (`lineID`,`exitStationID`),
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`lineID`, `departureID`) REFERENCES `departure` (`lineID`, `departureID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`lineID`, `exitStationID`) REFERENCES `stop` (`lineID`, `stationID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ticket` */

insert  into `ticket`(`lineID`,`departureID`,`seatNumber`,`firstName`,`lastName`,`exitStationID`) values 
(1,1,1,'Borko','Putnik',6),
(1,1,2,'Nikola','Jankovic',3),
(2,1,1,'Darko','Zikic',3),
(2,1,2,'Sima','Simic',4),
(2,1,3,'Ivana','Filipovic',3),
(3,2,1,'Hana','Jecic',5),
(3,2,2,'Mirko','Hinic',6),
(3,2,3,'Nina','Novakovic',5),
(3,2,4,'Goran','Jovanovic',5),
(3,2,5,'Vladimir','Jecic',5),
(3,2,6,'Hana','Jecic',5);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
