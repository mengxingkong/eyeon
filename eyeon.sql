/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : eyeon

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-06-10 01:14:01
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
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'lijun', '123456');

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
-- Records of case
-- ----------------------------
INSERT INTO `case` VALUES ('2', '交通事故', 'lat lon', '2017-10-30 09:24:02', '10', '1');
INSERT INTO `case` VALUES ('3', '火情', 'lat lon', '2017-10-30 09:25:34', '20', '1');
INSERT INTO `case` VALUES ('4', '争执', 'lat lon ', '2017-10-25 09:26:09', '30', '1');
INSERT INTO `case` VALUES ('5', '事件1', 'lat lon', '2017-11-16 19:58:28', '20', '1');
INSERT INTO `case` VALUES ('6', '事件2', 'lat lon', '2017-11-23 19:59:02', '30', '1');
INSERT INTO `case` VALUES ('7', '事件3', 'lat lon ', '2017-11-16 19:59:32', '50', '1');
INSERT INTO `case` VALUES ('8', '事件4', 'lat lon', '2017-11-16 20:00:00', '40', '1');
INSERT INTO `case` VALUES ('9', '事件5', 'lat lon ', '2017-11-15 20:00:21', '60', '1');
INSERT INTO `case` VALUES ('10', '事件6', 'lat lon', '2017-11-30 23:44:32', '20', '1');
INSERT INTO `case` VALUES ('11', 'night1', 'lat lon', '2017-11-30 23:45:09', '80', '1');
INSERT INTO `case` VALUES ('12', 'night2', 'lat lon', '2017-11-30 23:45:24', '90', '1');
INSERT INTO `case` VALUES ('13', 'night3', 'lat lon', '2017-11-30 23:45:55', '60', '1');
INSERT INTO `case` VALUES ('14', 'night4', 'lat lon', '2017-11-30 23:46:27', '20', '1');
INSERT INTO `case` VALUES ('15', 'night5', 'lat lon', '2017-11-30 23:46:41', '30', '1');
INSERT INTO `case` VALUES ('16', '火车晚点，等你来救', '中国 天津 滨海新区', '2018-03-08 10:06:04', '10000000', '1');
INSERT INTO `case` VALUES ('17', '123', '中国 天津 滨海新区', '2018-03-08 10:31:21', '3131', '1');
INSERT INTO `case` VALUES ('18', '测试', '中国 辽宁 大连', '2018-03-08 11:03:31', '123', '1');

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
-- Records of donationhistory
-- ----------------------------

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
-- Records of donationinfo
-- ----------------------------

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
-- Records of donationuserinfo
-- ----------------------------
INSERT INTO `donationuserinfo` VALUES ('1', 'oSKKw0a-9r765359m31qfRdeazAY', '0', '18342211752', null);

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
-- Records of dronestate
-- ----------------------------

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
-- Records of identitycode
-- ----------------------------
INSERT INTO `identitycode` VALUES ('1', '18342211752', '908363', '2018-04-25 08:00:36', '2018-04-25 08:10:36');
INSERT INTO `identitycode` VALUES ('2', '18342241858', '870937', '2018-03-07 13:54:18', '2018-03-28 14:03:52');

-- ----------------------------
-- Table structure for mission
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `MissionID` int(11) NOT NULL,
  PRIMARY KEY (`MissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mission
-- ----------------------------

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
-- Records of pageauthorize
-- ----------------------------
INSERT INTO `pageauthorize` VALUES ('1', 'oSKKw0a-9r765359m31qfRdeazAY', '9_5Hf6qHRQ-TsS91VLvrh64gG09-y8TLt25ys1lqVVT4ys2Vlnr7xJcsiXm8mhFibGVKYRAn1xIeUUdTyDc6pBpQtlGr0sH-t0OF1vOP2nHKY', '9_HndFgfYsvtInwamrdrBztDBT5aU_eAW_JxFJVBZNuW76LBxV3MLPvA8C5pbuP4kimMP56mJmZUjuW5iqVoIieCKJ1MKB-pfUJdp20PH6RFQ');
INSERT INTO `pageauthorize` VALUES ('2', 'oSKKw0YHR1M6Azb50b11yQbjGQSQ', '7_AKQ4lhyGljqv872Jjj-wjj3gcCBG7QzUVUNyG3PexsEvFOKtciGqRgwH4uA0-Jeypws6pkkUysQAoH-t5-e6CRBeN1KdomOXU4f0Nr2sWqo', '7_osxKob7nYgjCejaNeIdEbfvfoS5a6FrG4JQn3jvYIE8MM1wKeP_QfaXi3IhS7fdr7ugomafdTqwDYJGJ-UPzWBX130mqtVJ6P4wRlvGJ3ZU');
INSERT INTO `pageauthorize` VALUES ('3', 'oSKKw0bXcIFxgYdcT3zCPjzlDSoU', '7_xq50fMHCKVCjWRbpqbu975T0tFed3PafJbCbO3tYWOWi20CvjLvJ3avCvawuDNFmsjcQ3cN2L_dZtruq5TjYtjNPJHuY5b1fMsTwQ1fd5uw', '7_t6xo39Pgd53WS_BzOQ0X9JQZKRsKJ1Qp984NCuKVqxgI87p92-eVGdgiCf1xplTVqCBbcDUzyp-YrB0mohaVvD5_p_KFra1i_81DxCxoe8I');
INSERT INTO `pageauthorize` VALUES ('4', 'oSKKw0dypgUaTDRBpG2cWaCVquvk', '7_BlXRXVC-xa6gFywJiziXL06PBZi4AEDQMm-xjMvzCsRKxyH_v6ID8ES1kmDUhlnJo5gBdpq1s4srHsYlXBFmHIODTrnW9LoTUEZBOyOn_fs', '7_Lvr7o1sYAcRKyjEwa820Zacvq2w_aZtfgyDTFIi5Hp1Z5QBjv4JNH7G4VE8MV-9IQvs39O0TZiK6xT4EmsCRAnjg696RAfXJsUS_cMXF0KI');
INSERT INTO `pageauthorize` VALUES ('5', 'oSKKw0bWAIm2HVe5YMYIyFJfLStI', '8_OCZuN8q_z-jpbyfrxWbRhZHvDRzUwXl-J6affpHxTIzvxraJ7mqYsT6XMaZkwoJFMKwzw88-z47oL6b46Vkg8e4ZHwTXfaDVKWM9xIqp77o', '8_FIiz8MspLrlqbI73gMG4rgb3xhr6-zDxEmPLrAg44t9mU3v4gGo8wIvTFLEkZCV3BA8xOjBfcjFvUA7L0r2BhKSVuln-oI0ET-YmMXmn9qE');

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
-- Records of rescue
-- ----------------------------
INSERT INTO `rescue` VALUES ('12', '1', '张', '18342211752', '121.5242160', '38.8633900', '辽宁省大连市甘井子区广贤路107号', '测试', null, '1', '2018-03-08 03:11:19.985000');

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
-- Records of rescuesituation
-- ----------------------------
INSERT INTO `rescuesituation` VALUES ('17', '1', '11', '已经成功完成', null, '2018-03-08 02:47:37', null, '1');
INSERT INTO `rescuesituation` VALUES ('18', '2', '11', '已经成功完成', '2018-03-08 02:51:19', '2018-03-08 02:51:22', null, '1');
INSERT INTO `rescuesituation` VALUES ('19', '0', '12', '机动性好，快速赶到现场', '2018-03-08 11:12:37', null, null, null);
INSERT INTO `rescuesituation` VALUES ('20', '1', '12', '已经成功完成', '2018-03-08 13:23:27', '2018-03-08 13:23:36', null, '1');
INSERT INTO `rescuesituation` VALUES ('21', '2', '12', 'wangye18342241858', null, null, null, '1');
INSERT INTO `rescuesituation` VALUES ('22', '0', '12', '车长8米，高4米。携带救火装置', '2018-03-08 21:44:45', null, null, null);

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
-- Records of rescueuserinfo
-- ----------------------------
INSERT INTO `rescueuserinfo` VALUES ('1', 'oSKKw0a-9r765359m31qfRdeazAY', '利军', '18342211752', '0');
INSERT INTO `rescueuserinfo` VALUES ('2', 'oSKKw0YHR1M6Azb50b11yQbjGQSQ', 'wangye', '18342241858', '0');

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
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('1', '救护车', '133333333', '121.5223194', '38.8708548', '1', '车长8米，高4米。携带救火装置');
INSERT INTO `resources` VALUES ('2', '消防车', '1982345322', '121.5404557', '38.8680082', '0', '机动性好，快速赶到现场');
INSERT INTO `resources` VALUES ('3', '洒水车', '1534556367', '121.5304657', '38.8671093', '0', '水容量4吨');

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
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('1', '1', '交通事故', '很严重', '2017-12-20 16:31:32', '12222', '-1', '2', '2017-12-20 16:31:32', '3', '20', '2017-12-20 16:51:32', 'lat lon', '2');
INSERT INTO `task` VALUES ('2', '1', '火情', '严重', '2017-12-20 16:33:24', '46', '0', '2', '2017-10-30 16:33:44', '3', '20', '2017-11-02 16:33:54', 'lat lon', '2');
INSERT INTO `task` VALUES ('3', '1', '交通事故', '严重', '2017-12-20 16:35:01', '789', '0', '3', '2017-10-30 16:35:14', '5', '20', '2017-11-03 16:35:21', 'lao lon', '2');
INSERT INTO `task` VALUES ('4', '1', '交通数股', '严重', '2017-12-20 16:35:46', '456', '0', '3', '2017-10-30 16:35:58', '4', '20', '2017-11-03 16:36:06', 'lat lon', '2');
INSERT INTO `task` VALUES ('5', '1', '交通事故', '很严重', '2017-12-20 16:31:32', '123', '-1', '4', '2017-10-30 16:32:18', '3', '20', '2017-10-30 16:32:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('6', '1', '交通事故6', '很严重', '2017-12-20 16:31:32', '123', '-1', '5', '2017-10-30 16:32:18', '3', '20', '2017-10-30 16:32:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('7', '1', '交通事故7', '很严重', '2017-12-20 16:31:32', '123', '-1', '6', '2017-10-30 16:32:18', '3', '20', '2017-10-30 16:32:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('8', '1', '交通事故8', '很严重', '2017-12-20 16:31:32', '123', '-1', '7', '2017-10-30 16:32:18', '3', '20', '2017-10-30 16:32:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('9', '1', '交通事故9', '很严重', '2017-12-20 16:31:32', '123', '-1', '8', '2017-10-30 16:32:18', '4', '20', '2017-10-30 16:32:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('10', '1', '交通事故10', '很严重', '2017-12-20 16:31:32', '123', '-1', '9', '2017-10-30 16:32:18', '3', '20', '2017-10-30 16:32:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('11', '1', '交通事故11', '很严重', '2017-12-20 16:31:32', '123', '-1', '5', '2017-10-30 16:32:18', '3', '20', '2017-10-30 16:32:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('12', '1', '交通事故12', '很严重', '2017-12-20 16:31:32', '123', '-1', '5', '2017-10-30 16:32:18', '3', '20', '2017-10-30 16:32:34', 'lat lon', '0');
INSERT INTO `task` VALUES ('13', '1', '交通数股13', '很严重', '2017-12-20 13:41:03', '123', '0', '6', '2017-11-23 13:41:17', '2', '20', '2017-11-25 13:41:24', 'lat lon', '0');
INSERT INTO `task` VALUES ('14', '1', 'night11', '很严重', '2017-12-20 23:47:27', '132', '0', '11', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('15', '1', 'night12', '很严重', '2017-12-20 23:49:16', '132', '0', '11', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('16', '1', 'night13', '很严重', '2017-12-20 23:49:39', '132', '0', '11', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('17', '1', 'night21', '很严重', '2017-12-20 23:50:02', '132', '0', '12', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('18', '1', 'night22', '很严重', '2017-12-20 23:50:18', '132', '0', '12', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('19', '1', 'night23', '很严重', '2017-12-20 23:50:32', '132', '0', '12', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('20', '1', 'night31', '很严重', '2017-12-20 23:50:47', '132', '0', '13', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('21', '1', 'night32', '很严重', '2017-12-20 23:51:01', '132', '0', '13', '2017-11-30 23:47:59', '4', '20', '2017-11-30 23:48:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('23', '1', 'ceshi', '很严重', '2017-12-20 18:23:18', '123', '-1', '2', '2017-12-20 18:23:18', '10', '0.001', '2017-12-20 18:35:18', 'lat lon', '8');
INSERT INTO `task` VALUES ('24', '1', '1SB', '111', '2017-12-20 19:39:36', '11', '-1', '2', '2017-12-06 19:39:36', '2', '11', '2017-12-06 19:39:36', 'lat lon', '0');
INSERT INTO `task` VALUES ('25', '1', '1', '1', '2017-12-20 18:36:55', '1', '-1', '4', '2017-12-20 18:36:55', '1', '1', '2017-12-20 18:37:55', 'lat lon ', '1');
INSERT INTO `task` VALUES ('26', '1', '1', '1', '2017-12-20 18:37:10', '1', '-1', '2', '2017-12-20 18:37:10', '1', '2', '2017-12-20 18:39:10', 'lat lon', '1');
INSERT INTO `task` VALUES ('27', '1', '1', '1', '2017-12-20 18:42:12', '1', '-1', '4', '2017-12-20 18:42:12', '1', '1', '2017-12-20 18:44:12', 'lat lon', '0');
INSERT INTO `task` VALUES ('28', '1', '铁胆火车侠', '哈哈哈哈', '2018-03-08 10:06:16', '快来救我', '-1', '2', '2018-03-08 10:06:16', '8', '1', '2018-03-08 10:19:16', 'lat lon', '3');
INSERT INTO `task` VALUES ('29', '1', '12', '21', '2018-03-08 10:31:34', '21', '-1', '2', '2018-03-08 10:31:34', '4', '11', '2018-03-08 10:43:34', 'lat lon', '2');
INSERT INTO `task` VALUES ('30', '1', '火灾', '测试', '2018-03-08 11:04:33', '1', '0', '18', '2018-03-08 11:04:33', '3', '1', '2018-03-08 11:16:33', '中国 辽宁 大连', '2');

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
-- Records of taskassignment
-- ----------------------------
INSERT INTO `taskassignment` VALUES ('6', '6', '2', '2017-11-01 07:23:46', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('7', '6', '3', '2017-11-01 07:33:20', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('8', '6', '1', '2017-11-08 00:01:12', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('9', '6', '4', '2017-11-08 00:01:57', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('10', '6', '3', '2017-11-22 09:01:26', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('11', '6', '5', '2017-11-01 07:23:46', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('12', '6', '6', '2017-11-01 07:23:46', '0', '2', '0');
INSERT INTO `taskassignment` VALUES ('13', '6', '7', '2017-11-01 07:23:46', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('14', '6', '8', '2017-11-01 07:23:46', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('15', '6', '9', '2017-11-01 07:23:46', '0', '2', '0');
INSERT INTO `taskassignment` VALUES ('16', '6', '11', '2017-11-11 15:32:00', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('17', '6', '14', '2017-11-30 23:52:48', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('18', '6', '14', '2017-11-30 23:53:21', '0', '2', '0');
INSERT INTO `taskassignment` VALUES ('19', '6', '20', '2017-12-01 01:37:26', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('20', '6', '21', '2017-12-01 22:02:24', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('21', '7', '23', '2017-12-06 18:29:06', '0', '2', '0.001');
INSERT INTO `taskassignment` VALUES ('22', '7', '23', '2017-12-06 18:30:51', '0', '3', '0.1');
INSERT INTO `taskassignment` VALUES ('23', '6', '16', '2017-12-06 19:33:01', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('24', '6', '23', '2017-12-06 19:36:24', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('25', '6', '24', '2017-12-20 18:32:01', '0', '-1', '0');
INSERT INTO `taskassignment` VALUES ('26', '6', '24', '2017-12-20 18:40:15', '1', '-1', '1');
INSERT INTO `taskassignment` VALUES ('27', '7', '24', '2017-12-20 18:40:15', '1', '-1', '1');
INSERT INTO `taskassignment` VALUES ('28', '6', '27', '2017-12-20 18:42:28', '1', '-1', '1');
INSERT INTO `taskassignment` VALUES ('29', '6', '26', '2018-02-27 07:30:40', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('30', '9', '1', '2018-03-08 01:04:35', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('31', '9', '4', '2018-03-08 01:05:10', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('32', '7', '1', '2018-03-08 01:15:58', '0', '1', '0');
INSERT INTO `taskassignment` VALUES ('33', '9', '2', '2018-03-08 01:34:34', '0', '3', '0');
INSERT INTO `taskassignment` VALUES ('34', '6', '28', '2018-03-08 10:10:59', '1', '1', '1');
INSERT INTO `taskassignment` VALUES ('35', '6', '28', '2018-03-08 10:11:32', '111', '2', '111');
INSERT INTO `taskassignment` VALUES ('36', '6', '29', '2018-03-08 10:31:57', '11', '-1', '11');
INSERT INTO `taskassignment` VALUES ('37', '6', '30', '2018-03-08 11:05:59', '122', '3', '12');
INSERT INTO `taskassignment` VALUES ('38', '6', '12', '2018-03-08 03:09:34', '0', '2', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tipreport
-- ----------------------------
INSERT INTO `tipreport` VALUES ('7', '2017-11-05 15:33:16', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:\\Jfinal-demo\\jfinal-demo-web\\src\\main\\webapp\\evidence\\tipreport\\2083725562.jpg', '0', '2');
INSERT INTO `tipreport` VALUES ('8', '2017-11-05 15:33:24', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:\\Jfinal-demo\\jfinal-demo-web\\src\\main\\webapp\\evidence\\tipreport\\2083727267.jpg', '0', '2');
INSERT INTO `tipreport` VALUES ('9', '2017-11-05 15:33:30', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:\\Jfinal-demo\\jfinal-demo-web\\src\\main\\webapp\\evidence\\tipreport\\2083809945.jpg', '0', '2');
INSERT INTO `tipreport` VALUES ('10', '2017-11-05 15:33:34', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:\\Jfinal-demo\\jfinal-demo-web\\src\\main\\webapp\\evidence\\tipreport\\2083809967.jpg', '0', '2');
INSERT INTO `tipreport` VALUES ('11', '2017-11-29 23:50:27', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809968.jpg', '0', '3');
INSERT INTO `tipreport` VALUES ('12', '2017-11-29 23:50:34', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809970.jpg', '0', '3');
INSERT INTO `tipreport` VALUES ('13', '2017-11-29 23:50:37', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809971.jpg', '0', '3');
INSERT INTO `tipreport` VALUES ('14', '2017-11-29 23:50:40', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809972.jpg', '0', '3');
INSERT INTO `tipreport` VALUES ('15', '2017-11-29 23:50:45', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809973.jpg', '0', '4');
INSERT INTO `tipreport` VALUES ('16', '2017-11-29 23:50:47', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809971.jpg', '0', '4');
INSERT INTO `tipreport` VALUES ('17', '2017-11-29 23:50:51', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809972.jpg', '0', '4');
INSERT INTO `tipreport` VALUES ('18', '2017-11-29 23:50:54', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809970.jpg', '0', '4');
INSERT INTO `tipreport` VALUES ('19', '2017-11-29 23:50:58', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809973.jpg', '0', '2');
INSERT INTO `tipreport` VALUES ('20', '2017-11-29 23:51:01', '中国 辽宁 大连', '121.528', '38.8677', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/2083809974.jpg', '0', '2');
INSERT INTO `tipreport` VALUES ('21', '2017-11-29 23:51:04', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067055437.jpg', '0', '5');
INSERT INTO `tipreport` VALUES ('22', '2017-11-29 23:51:11', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067055432.jpg', '0', '6');
INSERT INTO `tipreport` VALUES ('23', '2017-11-29 23:51:14', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067054288.jpg', '0', '7');
INSERT INTO `tipreport` VALUES ('24', '2017-11-29 23:51:18', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067053518.jpg', '0', '8');
INSERT INTO `tipreport` VALUES ('25', '2017-11-29 23:51:20', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067053515.jpg', '0', '9');
INSERT INTO `tipreport` VALUES ('26', '2017-11-29 23:51:23', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067053510.jpg', '0', '5');
INSERT INTO `tipreport` VALUES ('27', '2017-11-29 23:51:26', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067052527.jpg', '0', '6');
INSERT INTO `tipreport` VALUES ('28', '2017-11-29 23:51:29', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2067052459.jpg', '0', '7');
INSERT INTO `tipreport` VALUES ('29', '2017-11-29 23:51:31', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2037590218.jpg', '0', '8');
INSERT INTO `tipreport` VALUES ('30', '2017-11-29 23:51:35', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-2037589220.jpg', '0', '9');
INSERT INTO `tipreport` VALUES ('31', '2017-12-06 14:19:56', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/1/oSKKw0a-9r765359m31qfRdeazAY/', '1', '11');
INSERT INTO `tipreport` VALUES ('32', '2017-11-09 11:40:44', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/1/oSKKw0a-9r765359m31qfRdeazAY/', '1', '3');
INSERT INTO `tipreport` VALUES ('33', '2017-12-06 14:19:53', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/3/oSKKw0a-9r765359m31qfRdeazAY/', '1', '12');
INSERT INTO `tipreport` VALUES ('34', '2017-11-09 12:36:31', '中国 辽宁 大连', '121.528', '38.8678', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/2/oSKKw0a-9r765359m31qfRdeazAY/', '1', '2');
INSERT INTO `tipreport` VALUES ('35', '2017-11-11 23:51:25', '中国 辽宁 大连', '121.517', '38.8703', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/4/5/oSKKw0a-9r765359m31qfRdeazAY/', '1', '4');
INSERT INTO `tipreport` VALUES ('36', '2017-11-16 18:42:03', '中国 辽宁 大连', '121.524', '38.865', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/4/oSKKw0a-9r765359m31qfRdeazAY/', '1', '3');
INSERT INTO `tipreport` VALUES ('37', '2017-12-06 14:19:43', '中国 辽宁 大连', '121.524', '38.865', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/4/oSKKw0a-9r765359m31qfRdeazAY/', '1', '11');
INSERT INTO `tipreport` VALUES ('38', '2017-12-01 21:59:36', '中国 辽宁 大连', '121.517', '38.8712', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/6/7/oSKKw0a-9r765359m31qfRdeazAY/', '1', '6');
INSERT INTO `tipreport` VALUES ('39', '2017-12-06 18:15:10', '中国 辽宁 大连', '121.523', '38.8648', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1966321001.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('40', '2017-12-06 18:16:46', '中国 辽宁 大连', '121.523', '38.8647', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1966321937.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('41', '2017-12-06 18:34:56', '新加坡  ', '121.523', '38.868', '7', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/7/23/oSKKw0YHR1M6Azb50b11yQbjGQSQ/', '1', '7');
INSERT INTO `tipreport` VALUES ('42', '2017-12-23 23:00:16', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828328.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('43', '2017-12-23 23:00:14', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828330.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('44', '2017-12-23 23:00:16', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828328.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('45', '2017-12-23 23:00:14', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828330.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('46', '2017-12-23 23:00:51', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828209.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('47', '2017-12-23 23:00:16', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828328.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('48', '2017-12-23 23:01:10', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828148.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('49', '2017-12-23 23:01:08', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828171.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('50', '2017-12-23 23:01:10', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828148.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('51', '2017-12-23 23:01:08', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828171.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('52', '2017-12-23 23:01:10', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828148.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('53', '2017-12-23 23:01:08', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697828171.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('54', '2017-12-23 23:02:27', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697827273.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('55', '2017-12-23 23:02:46', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697827212.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('56', '2017-12-23 23:02:46', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697827212.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('57', '2017-12-23 23:02:46', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697827212.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('58', '2017-12-23 23:04:33', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697826223.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('59', '2017-12-23 23:04:33', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697826223.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('60', '2017-12-23 23:05:08', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697825474.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('61', '2017-12-23 23:05:08', '中国 辽宁 大连', '121.523', '38.8646', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-697825474.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('62', '2018-02-27 07:29:29', '中国 辽宁 大连', '121.528', '38.8631', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/11/14/oSKKw0a-9r765359m31qfRdeazAY/', '1', '11');
INSERT INTO `tipreport` VALUES ('63', '2018-02-27 15:31:45', '中国 辽宁 大连', '121.528', '38.8631', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-357489997.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('64', '2018-02-27 07:34:18', '中国 辽宁 大连', '121.524', '38.8634', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/3/oSKKw0a-9r765359m31qfRdeazAY/', '1', '3');
INSERT INTO `tipreport` VALUES ('65', '2018-03-08 08:58:08', '中国 四川 成都', '121.524', '38.8647', '9', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/-447183504.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('66', '2018-03-08 01:36:52', '中国 四川 成都', '121.524', '38.8647', '9', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/2/oSKKw0bXcIFxgYdcT3zCPjzlDSoU/', '1', '2');
INSERT INTO `tipreport` VALUES ('67', '2018-03-08 02:31:35', '中国 天津 滨海新区', '121.524', '38.8649', '8', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/978390682.jpg', '0', '17');
INSERT INTO `tipreport` VALUES ('68', '2018-03-08 02:38:15', '中国 辽宁 大连', '121.524', '38.8634', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/29/oSKKw0a-9r765359m31qfRdeazAY/', '1', '2');
INSERT INTO `tipreport` VALUES ('69', '2018-03-08 02:38:20', '中国 辽宁 大连', '121.524', '38.8634', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/29/oSKKw0a-9r765359m31qfRdeazAY/', '1', '2');
INSERT INTO `tipreport` VALUES ('70', '2018-03-08 10:59:48', '中国 辽宁 大连', '121.524', '38.8634', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1907407438.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('71', '2018-03-08 03:03:53', '中国 辽宁 大连', '121.524', '38.8634', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1907407438.jpg', '0', '18');
INSERT INTO `tipreport` VALUES ('72', '2018-03-08 03:08:16', '中国 辽宁 大连', '121.528', '38.8632', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/18/30/oSKKw0a-9r765359m31qfRdeazAY/', '1', '18');
INSERT INTO `tipreport` VALUES ('73', '2018-03-08 21:16:11', '中国 辽宁 大连', '121.511', '38.8803', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1930406052.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('74', '2018-04-25 09:32:44', '中国 辽宁 大连', '121.528', '38.868', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1214231558.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('75', '2018-04-25 15:16:27', '中国 辽宁 大连', '121.521', '38.8652', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216730223.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('76', '2018-04-25 15:16:27', '中国 辽宁 大连', '121.521', '38.8652', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216730223.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('77', '2018-04-25 15:16:27', '中国 辽宁 大连', '121.521', '38.8652', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216730223.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('78', '2018-04-25 15:16:57', '中国 辽宁 大连', '121.521', '38.8652', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216730967.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('79', '2018-04-25 15:16:58', '中国 辽宁 大连', '121.521', '38.8652', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216730968.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('80', '2018-04-25 15:16:58', '中国 辽宁 大连', '121.521', '38.8652', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216730968.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('81', '2018-04-25 15:56:48', '中国 辽宁 大连', '121.522', '38.8659', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216814544.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('82', '2018-04-25 15:56:48', '中国 辽宁 大连', '121.522', '38.8659', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216814544.jpg', '0', null);
INSERT INTO `tipreport` VALUES ('83', '2018-04-25 15:56:48', '中国 辽宁 大连', '121.522', '38.8659', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/1216814544.jpg', '0', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tipreportmore
-- ----------------------------
INSERT INTO `tipreportmore` VALUES ('1', '你好:\n你好', '8', '6', '', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/2/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('2', '你好:\n好', '8', '10', '', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/3/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('3', '你好:\n你不敢', '8', '10', '', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/3/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('4', '啦啦啦:\n啦啦啦', '8', '8', '', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/1/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('5', '啦啦啦:\n啦啦啦', '8', '8', '', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/1/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('6', '啦啦啦:\n啦啦啦', '8', '7', '', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/3/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('7', '测试:\n测试', '34', '6', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/2/oSKKw0a-9r765359m31qfRdeazAY/0ad849a9-2c00-44f3-8c8d-3dcf6c21b8dc.mp3', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/2/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('8', '执行任务:\n执行任务', '35', '11', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/4/5/oSKKw0a-9r765359m31qfRdeazAY/f59d2397-b986-4392-a85a-b37cead6fb22.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/4/5/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('9', '你好:\n你好', '37', '9', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/4/oSKKw0a-9r765359m31qfRdeazAY/81ef1fb5-548d-4e60-8669-3b3b61800547.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/4/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('10', '你好:\n你好', '36', '9', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/4/oSKKw0a-9r765359m31qfRdeazAY/0ba01fbe-5c6e-48bc-8541-1d22a2af6cbf.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/4/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('11', '文本:\n积极叽叽叽叽', '38', '13', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/6/7/oSKKw0a-9r765359m31qfRdeazAY/17db9621-51e9-4eec-a0ec-6ce7355b5116.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/6/7/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('12', '王冶:\n王冶', '41', '22', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/7/23/oSKKw0YHR1M6Azb50b11yQbjGQSQ/2d55b234-17bb-4585-8aaa-61d0fdc7847c.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/7/23/oSKKw0YHR1M6Azb50b11yQbjGQSQ/');
INSERT INTO `tipreportmore` VALUES ('13', '你好:\n聚餐', '62', '17', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/11/14/oSKKw0a-9r765359m31qfRdeazAY/c70fe6e3-3f14-43da-9f2c-265b4857fb54.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/11/14/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('14', '1:\n12', '64', '10', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/3/oSKKw0a-9r765359m31qfRdeazAY/13a6189e-9699-4907-8346-b2d3659ab2dc.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/3/3/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('15', '正在执行很好:\n很好的', '66', '33', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/2/oSKKw0bXcIFxgYdcT3zCPjzlDSoU/c842cd11-f47c-4629-9b16-61c37efdc424.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/2/oSKKw0bXcIFxgYdcT3zCPjzlDSoU/');
INSERT INTO `tipreportmore` VALUES ('16', '测试:\n正在测试', '68', '36', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/29/oSKKw0a-9r765359m31qfRdeazAY/1d32fce9-f399-41ca-8318-55c6df3dae78.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/29/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('17', '测试:\n正在测试', '69', '36', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/29/oSKKw0a-9r765359m31qfRdeazAY/dea6eaa4-363d-4073-aaee-ca6fbebc51d8.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/2/29/oSKKw0a-9r765359m31qfRdeazAY/');
INSERT INTO `tipreportmore` VALUES ('18', '测试:\n测试', '72', '37', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/18/30/oSKKw0a-9r765359m31qfRdeazAY/47fb8f4a-9dca-4586-b6a8-df3a0df06a26.amr', 'D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore/18/30/oSKKw0a-9r765359m31qfRdeazAY/');

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

-- ----------------------------
-- Records of wechatuser
-- ----------------------------
INSERT INTO `wechatuser` VALUES ('6', 'oSKKw0a-9r765359m31qfRdeazAY', '中国 辽宁 大连', '利军', 'http://wx.qlogo.cn/mmopen/rNpMrTibgJovPDquLib3KHrJ2vRJLOYsIQtS6XwaSy0ea9LBY9rSibZ4KQWzpwaWZC5RhTeDHUuovPEblR0zZ55XW2V1kib0qaXh/0', '1', '38.865913 121.522415', '0', '2017-10-30 16:34:21', '0', '1');
INSERT INTO `wechatuser` VALUES ('7', 'oSKKw0YHR1M6Azb50b11yQbjGQSQ', '新加坡  ', '好好先生', 'http://wx.qlogo.cn/mmopen/hu1iajCF60PuZcbsiavTgDSzpnDdBiajgJ4ldsKdl0ZhjS2SQtrrTQa4XjQHp8VbrFSe4zRiab9hHcHZ6ERvlWQkKmJTWALKWZvib/0', '1', '38.868011 121.522667', '0', '2017-12-06 18:25:30', '0', '1');
INSERT INTO `wechatuser` VALUES ('8', 'oSKKw0dypgUaTDRBpG2cWaCVquvk', '中国 天津 滨海新区', 'SHIKI', 'http://thirdwx.qlogo.cn/mmopen/65REmGre9OcibYB9SpsLahYlNDHoAlMQNIDPN1iceZiaLgR3iaS6ibPibAJUHUKYicwzLia9W6WWU3ugqYibm2966N8fZpREcAsJWicjfy/132', '1', '38.864899 121.523750', '0', '2018-03-08 08:53:14', '0', '1');
INSERT INTO `wechatuser` VALUES ('9', 'oSKKw0bXcIFxgYdcT3zCPjzlDSoU', '中国 四川 成都', 'I am', 'http://thirdwx.qlogo.cn/mmopen/rNpMrTibgJotp9e6v0bMEBbLZ5fyluzbau9d3paiazJErnnWbPdmMM53MsZb5arOy3lzldpZrwbJIQxeSMSywcW8CFgxZZRksa/132', '2', '38.864719 121.523788', '0', '2018-03-08 08:53:42', '0', '1');
INSERT INTO `wechatuser` VALUES ('10', 'oSKKw0bWAIm2HVe5YMYIyFJfLStI', '中国 辽宁 大连', '周志峰', 'http://thirdwx.qlogo.cn/mmopen/hu1iajCF60PuZcbsiavTgDS10MNibu1K4JxOjiaCDIATb8kQZrWGUjcCJibyAaELoDC6DGv2QmyGic2gygaicgPMgO9ibspBicI3OHH6R/132', '1', '38.865017 121.526688', '0', '2018-04-13 17:19:22', '0', '1');
