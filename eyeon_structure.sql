/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : eyeon

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-10 06:23:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `AdminID` int(11) NOT NULL AUTO_INCREMENT,
  `AdminName` varchar(45) NOT NULL,
  `AdminPwd` varchar(45) NOT NULL,
  PRIMARY KEY (`AdminID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for case
-- ----------------------------
DROP TABLE IF EXISTS `case`;
CREATE TABLE `case` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Depict` varchar(500) NOT NULL,
  `Location` varchar(200) DEFAULT NULL,
  `Time` timestamp NULL DEFAULT NULL,
  `Budget` float DEFAULT '0',
  `AdminID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Case_Admin_idx` (`AdminID`),
  CONSTRAINT `fk_Case_Admin` FOREIGN KEY (`AdminID`) REFERENCES `admin` (`AdminID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for donationhistory
-- ----------------------------
DROP TABLE IF EXISTS `donationhistory`;
CREATE TABLE `donationhistory` (
  `DonationID` int(10) NOT NULL,
  `DonationUser` int(10) NOT NULL,
  `Count` float NOT NULL DEFAULT '0',
  `Time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `Message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DonationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for donationinfo
-- ----------------------------
DROP TABLE IF EXISTS `donationinfo`;
CREATE TABLE `donationinfo` (
  `DonationID` int(10) NOT NULL,
  `CaseID` int(10) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `DonationContent` varchar(255) DEFAULT NULL,
  `Count` float DEFAULT NULL,
  `BeginTIme` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `EndTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`DonationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for donationuserinfo
-- ----------------------------
DROP TABLE IF EXISTS `donationuserinfo`;
CREATE TABLE `donationuserinfo` (
  `DonationUserID` int(10) NOT NULL AUTO_INCREMENT,
  `OpenID` varchar(255) NOT NULL,
  `Integrate` int(10) NOT NULL DEFAULT '0',
  `PhoneNumber` varchar(11) NOT NULL,
  `CardNumber` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`DonationUserID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dronestate
-- ----------------------------
DROP TABLE IF EXISTS `dronestate`;
CREATE TABLE `dronestate` (
  `DroneID` varchar(45) NOT NULL,
  `Time` time NOT NULL,
  `Position` varchar(45) DEFAULT NULL,
  `Velocity` float DEFAULT NULL,
  `Altitude` float DEFAULT NULL,
  `HomeLocation` varchar(45) DEFAULT NULL,
  `Compass` varchar(45) DEFAULT NULL,
  `IMU` varchar(45) DEFAULT NULL,
  `Satellite` varchar(45) DEFAULT NULL,
  `HomeStatus` varchar(45) DEFAULT NULL,
  `MotorsStatus` varchar(45) DEFAULT NULL,
  `IsFly` varchar(45) DEFAULT NULL,
  `FlightLimitation` varchar(45) DEFAULT NULL,
  `GEO` varchar(45) DEFAULT NULL,
  `MissionID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DroneID`,`Time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for identitycode
-- ----------------------------
DROP TABLE IF EXISTS `identitycode`;
CREATE TABLE `identitycode` (
  `CodeID` int(10) NOT NULL AUTO_INCREMENT,
  `PhoneNum` varchar(11) NOT NULL,
  `Code` varchar(6) NOT NULL,
  `BeignTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `EndTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CodeID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `MissionID` int(11) NOT NULL,
  PRIMARY KEY (`MissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pageauthorize
-- ----------------------------
DROP TABLE IF EXISTS `pageauthorize`;
CREATE TABLE `pageauthorize` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `OpenID` varchar(255) NOT NULL,
  `Access_token` varchar(255) NOT NULL,
  `Refresh_token` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rescue
-- ----------------------------
DROP TABLE IF EXISTS `rescue`;
CREATE TABLE `rescue` (
  `Id` int(4) NOT NULL AUTO_INCREMENT,
  `RescueUserID` int(10) DEFAULT NULL,
  `HelpName` varchar(50) DEFAULT NULL,
  `Phone` varchar(50) DEFAULT NULL,
  `Longitude` decimal(20,7) DEFAULT NULL,
  `Latitude` decimal(20,7) DEFAULT NULL,
  `Address` varchar(50) DEFAULT NULL,
  `Describe` varchar(50) DEFAULT NULL,
  `Level` int(1) DEFAULT NULL,
  `Status` int(4) DEFAULT NULL COMMENT '0 等待救援  1救援  2成功  -1 救援失败',
  `RescueTime` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rescuesituation
-- ----------------------------
DROP TABLE IF EXISTS `rescuesituation`;
CREATE TABLE `rescuesituation` (
  `RescueSituID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `RescueUserID` int(10) NOT NULL,
  `RescueID` int(10) NOT NULL,
  `RescueInformation` varchar(255) DEFAULT NULL,
  `StartTime` timestamp NULL DEFAULT NULL,
  `EndTime` timestamp NULL DEFAULT NULL,
  `Evaluate` varchar(255) DEFAULT NULL,
  `IsOut` int(1) DEFAULT NULL,
  PRIMARY KEY (`RescueSituID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rescueuserinfo
-- ----------------------------
DROP TABLE IF EXISTS `rescueuserinfo`;
CREATE TABLE `rescueuserinfo` (
  `RescueUserID` int(10) NOT NULL AUTO_INCREMENT,
  `OpenID` varchar(200) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `PhoneNumber` varchar(11) NOT NULL,
  `IsBusy` int(1) DEFAULT '0',
  PRIMARY KEY (`RescueUserID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `Id` int(4) NOT NULL AUTO_INCREMENT,
  `RescueName` varchar(50) DEFAULT NULL,
  `Phone` int(50) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `IsUse` int(4) DEFAULT NULL,
  `Describe` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `TaskID` int(11) NOT NULL AUTO_INCREMENT,
  `AdminID` int(11) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `CreationTime` datetime NOT NULL,
  `Keywords` varchar(50) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `Question` int(11) DEFAULT NULL COMMENT '关联Case',
  `Lifetime` timestamp NULL DEFAULT NULL,
  `MaxAssignments` int(11) DEFAULT NULL,
  `Reward` float DEFAULT NULL,
  `AssignmentDuration` timestamp NULL DEFAULT NULL,
  `TaskGPS` varchar(30) DEFAULT NULL,
  `FollowMax` int(11) DEFAULT NULL,
  PRIMARY KEY (`TaskID`),
  KEY `fk_Task_Admin_idx` (`AdminID`),
  KEY `fk_Task_Case_idx` (`Question`),
  CONSTRAINT `fk_Task_Admin` FOREIGN KEY (`AdminID`) REFERENCES `admin` (`AdminID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Case` FOREIGN KEY (`Question`) REFERENCES `case` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for taskassignment
-- ----------------------------
DROP TABLE IF EXISTS `taskassignment`;
CREATE TABLE `taskassignment` (
  `AssignID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `TaskID` int(11) NOT NULL,
  `AssignDate` timestamp NULL DEFAULT NULL,
  `AssignType` int(11) DEFAULT NULL COMMENT '0：主动\n1：被动',
  `Status` int(11) DEFAULT NULL COMMENT '0：工作中 => "已完成"或“已拒绝”，“已过期”\n1：已指派 =>工作中',
  `Reward` float DEFAULT NULL,
  PRIMARY KEY (`AssignID`),
  KEY `fk_TaskAssignment_User_idx` (`UserID`),
  KEY `fk_TaskAssignment_Task_idx` (`TaskID`),
  CONSTRAINT `fk_TaskAssignment_Task` FOREIGN KEY (`TaskID`) REFERENCES `task` (`TaskID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskAssignment_User` FOREIGN KEY (`UserID`) REFERENCES `wechatuser` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tipreport
-- ----------------------------
DROP TABLE IF EXISTS `tipreport`;
CREATE TABLE `tipreport` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Location` varchar(200) NOT NULL,
  `Longitude` float DEFAULT NULL,
  `Latitude` float DEFAULT NULL,
  `Tipster` int(11) NOT NULL,
  `IMGDIR` longtext NOT NULL,
  `IsMore` int(11) NOT NULL DEFAULT '0' COMMENT '"1"表示 复杂举报\n“0”表示 简单举报',
  `CaseID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ShortReport_User_idx` (`Tipster`),
  KEY `fk_TipReport_Case_idx` (`CaseID`),
  CONSTRAINT `fk_ShortReport_User` FOREIGN KEY (`Tipster`) REFERENCES `wechatuser` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TipReport_Case` FOREIGN KEY (`CaseID`) REFERENCES `case` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tipreportmore
-- ----------------------------
DROP TABLE IF EXISTS `tipreportmore`;
CREATE TABLE `tipreportmore` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Depict` varchar(200) NOT NULL,
  `ReportID` int(11) NOT NULL,
  `AssignID` int(11) NOT NULL,
  `SoundDir` varchar(225) DEFAULT NULL,
  `VideoDir` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_TipReportMore_TipReport_idx` (`ReportID`),
  KEY `fk_TipReportMore_Assign_idx` (`AssignID`),
  CONSTRAINT `fk_TipReportMore_Assign` FOREIGN KEY (`AssignID`) REFERENCES `taskassignment` (`AssignID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TipReportMore_TipReport` FOREIGN KEY (`ReportID`) REFERENCES `tipreport` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for wechatuser
-- ----------------------------
DROP TABLE IF EXISTS `wechatuser`;
CREATE TABLE `wechatuser` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OpenID` varchar(200) NOT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `UserName` varchar(45) NOT NULL,
  `HeadImg` longtext NOT NULL,
  `Sex` int(11) DEFAULT NULL,
  `GPS` varchar(45) DEFAULT NULL,
  `Quota` int(11) DEFAULT '0',
  `BeginTime` timestamp NULL DEFAULT NULL,
  `Credit` float DEFAULT '0',
  `IsFollowed` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
