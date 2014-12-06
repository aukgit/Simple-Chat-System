/*
Navicat MySQL Data Transfer

Source Server         : localhost(mysql)
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : chatdatabase

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2014-12-07 01:18:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `chatlist`
-- ----------------------------
DROP TABLE IF EXISTS `chatlist`;
CREATE TABLE `chatlist` (
  `ChatListID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OriginalUserID` int(11) NOT NULL,
  `RelatedUserID` int(11) NOT NULL,
  PRIMARY KEY (`ChatListID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of chatlist
-- ----------------------------

-- ----------------------------
-- Table structure for `chatsession`
-- ----------------------------
DROP TABLE IF EXISTS `chatsession`;
CREATE TABLE `chatsession` (
  `ChatSessionID` int(11) NOT NULL AUTO_INCREMENT,
  `Timed` datetime NOT NULL,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ChatSessionID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of chatsession
-- ----------------------------
INSERT INTO `chatsession` VALUES ('1', '2014-11-26 03:55:57', '1');
INSERT INTO `chatsession` VALUES ('2', '2014-11-26 03:55:57', '1');
INSERT INTO `chatsession` VALUES ('3', '2014-11-26 03:55:57', '1');
INSERT INTO `chatsession` VALUES ('4', '2014-11-26 03:55:57', '1');
INSERT INTO `chatsession` VALUES ('5', '2014-11-26 03:55:57', '1');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `MessageID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SendFromUserID` int(11) NOT NULL,
  `SendToUserID` int(11) NOT NULL,
  `IsReceivedByUser` tinyint(1) NOT NULL,
  `Message` varchar(2000) NOT NULL,
  PRIMARY KEY (`MessageID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `serversetting`
-- ----------------------------
DROP TABLE IF EXISTS `serversetting`;
CREATE TABLE `serversetting` (
  `ServerSettingID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `ServerIP` varchar(50) NOT NULL,
  `ServerPort` int(11) NOT NULL,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`ServerSettingID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of serversetting
-- ----------------------------
INSERT INTO `serversetting` VALUES ('1', 'localhost', '9861', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Email` varchar(256) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `PasswordHash` varchar(45) NOT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `IsBlocked` tinyint(1) NOT NULL,
  `IsActive` tinyint(1) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
