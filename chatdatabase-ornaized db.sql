/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.100
Source Server Version : 50614
Source Host           : 192.168.1.100:3306
Source Database       : chatdatabase

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2014-12-29 06:59:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `activestate`
-- ----------------------------
DROP TABLE IF EXISTS `activestate`;
CREATE TABLE `activestate` (
  `ActiveStateID` tinyint(2) NOT NULL AUTO_INCREMENT,
  `State` varchar(10) NOT NULL,
  `colorRed` smallint(2) NOT NULL,
  `colorGreen` smallint(2) NOT NULL,
  `colorBlue` smallint(2) NOT NULL,
  PRIMARY KEY (`ActiveStateID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of activestate
-- ----------------------------
INSERT INTO `activestate` VALUES ('1', 'Available', '0', '127', '0');
INSERT INTO `activestate` VALUES ('2', 'Away', '236', '132', '20');
INSERT INTO `activestate` VALUES ('3', 'Busy', '255', '25', '25');

-- ----------------------------
-- Table structure for `chatlist`
-- ----------------------------
DROP TABLE IF EXISTS `chatlist`;
CREATE TABLE `chatlist` (
  `ChatListID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OriginalUserID` int(11) NOT NULL,
  `RelatedUserID` int(11) NOT NULL,
  `AliasAs` varchar(100) NOT NULL,
  PRIMARY KEY (`ChatListID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of chatlist
-- ----------------------------
INSERT INTO `chatlist` VALUES ('1', '1', '2', 'Alim Second Account');
INSERT INTO `chatlist` VALUES ('2', '2', '1', 'Alim First Account');

-- ----------------------------
-- Table structure for `chatsession`
-- ----------------------------
DROP TABLE IF EXISTS `chatsession`;
CREATE TABLE `chatsession` (
  `ChatSessionID` int(11) NOT NULL AUTO_INCREMENT,
  `Timed` datetime NOT NULL,
  `IsActive` tinyint(1) NOT NULL,
  `SessionName` varchar(40) DEFAULT NULL,
  `IsSingleUser` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ChatSessionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of chatsession
-- ----------------------------

-- ----------------------------
-- Table structure for `chatsessionrelateduser`
-- ----------------------------
DROP TABLE IF EXISTS `chatsessionrelateduser`;
CREATE TABLE `chatsessionrelateduser` (
  `ChatSessionRelatedUserID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ChatSessionID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ChatSessionRelatedUserID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of chatsessionrelateduser
-- ----------------------------

-- ----------------------------
-- Table structure for `friendrequest`
-- ----------------------------
DROP TABLE IF EXISTS `friendrequest`;
CREATE TABLE `friendrequest` (
  `FriendRequestID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SenderUserID` int(11) NOT NULL,
  `ToWhomUserID` int(11) NOT NULL,
  `IsAccept` tinyint(4) NOT NULL,
  `Message` varchar(40) NOT NULL,
  `IsSeen` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`FriendRequestID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of friendrequest
-- ----------------------------
INSERT INTO `friendrequest` VALUES ('1', '2', '1', '0', 'qwdwdqdwq', '1');
INSERT INTO `friendrequest` VALUES ('2', '1', '3', '0', 'hello 3 alim i would like to add you]', '0');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `MessageID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `SendFromUserID` int(11) NOT NULL,
  `ChatSessionID` int(11) NOT NULL,
  `Message` varchar(400) NOT NULL,
  `IsFileExit` tinyint(1) NOT NULL,
  PRIMARY KEY (`MessageID`),
  KEY `MessageIDDsc` (`MessageID`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `msgfile`
-- ----------------------------
DROP TABLE IF EXISTS `msgfile`;
CREATE TABLE `msgfile` (
  `MsgFileID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MessageID` bigint(20) NOT NULL,
  `File` blob NOT NULL,
  PRIMARY KEY (`MsgFileID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of msgfile
-- ----------------------------

-- ----------------------------
-- Table structure for `serversetting`
-- ----------------------------
DROP TABLE IF EXISTS `serversetting`;
CREATE TABLE `serversetting` (
  `ServerSettingID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `ServerIP` varchar(50) NOT NULL,
  `ServerPort` smallint(11) NOT NULL,
  `IsActive` tinyint(1) NOT NULL,
  `ConnectionString` varchar(256) DEFAULT NULL,
  `UserOnlinePort` smallint(4) NOT NULL,
  `PictureUploaderPort` smallint(4) NOT NULL DEFAULT '0',
  `ProfilePicWidth` smallint(4) NOT NULL DEFAULT '0',
  `ProfilePicHeight` smallint(4) NOT NULL DEFAULT '0',
  `ChatingThumbWidth` smallint(4) NOT NULL DEFAULT '0',
  `ChatingThumbHeight` smallint(4) NOT NULL DEFAULT '0',
  `ChatListThumbWidth` smallint(4) NOT NULL DEFAULT '0',
  `ChatListThumbHeight` smallint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ServerSettingID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of serversetting
-- ----------------------------
INSERT INTO `serversetting` VALUES ('1', '192.168.1.100', '9861', '1', 'jdbc:mysql://192.168.1.100:3306/chatdatabase', '8089', '9807', '65', '65', '48', '48', '24', '24');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `Email` varchar(256) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `IsBlocked` tinyint(1) NOT NULL DEFAULT '0',
  `IsActive` tinyint(1) NOT NULL DEFAULT '1',
  `IsAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `IsOnline` tinyint(1) NOT NULL DEFAULT '0',
  `CurrentActiveState` tinyint(2) NOT NULL DEFAULT '0',
  `CurrentStatus` varchar(80) NOT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'alim', 'Alim First Account', 'devorg.bd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-16 20:24:20', '0', '1', '1', '0', '2', 'Saying new things');
INSERT INTO `user` VALUES ('2', 'alim2', 'Alim Second Account', 'devorgb.d@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-26 19:45:03', '0', '1', '0', '0', '3', '');
INSERT INTO `user` VALUES ('3', 'alim3', 'Alim Third Account', 'dev.orgbd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-26 19:45:16', '0', '1', '0', '0', '2', 'hello 2');
INSERT INTO `user` VALUES ('4', 'alim4', 'Alim Fourth Account', 'de.vorg.bd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-26 19:45:36', '0', '1', '0', '0', '0', '');
INSERT INTO `user` VALUES ('5', 'alim5', 'alim ul karim', 'dev.org.bd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-29 05:35:46', '0', '1', '0', '0', '0', 'None');

-- ----------------------------
-- Table structure for `usermsgreceived`
-- ----------------------------
DROP TABLE IF EXISTS `usermsgreceived`;
CREATE TABLE `usermsgreceived` (
  `UserMsgReceivedID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `MessageID` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`UserMsgReceivedID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of usermsgreceived
-- ----------------------------

-- ----------------------------
-- Table structure for `userstatus`
-- ----------------------------
DROP TABLE IF EXISTS `userstatus`;
CREATE TABLE `userstatus` (
  `UserStatusID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `Status` varchar(80) NOT NULL,
  `Dated` datetime NOT NULL,
  PRIMARY KEY (`UserStatusID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of userstatus
-- ----------------------------
INSERT INTO `userstatus` VALUES ('1', '1', 'dwqdqw', '2014-12-23 01:28:13');
INSERT INTO `userstatus` VALUES ('2', '1', 'qwdqdw', '2014-12-23 01:28:18');
INSERT INTO `userstatus` VALUES ('3', '1', 'dwqqdw', '2014-12-23 01:32:26');
INSERT INTO `userstatus` VALUES ('4', '1', 'dwqqwddwq', '2014-12-23 01:35:38');
INSERT INTO `userstatus` VALUES ('5', '1', 'dqw233232', '2014-12-23 01:40:35');
INSERT INTO `userstatus` VALUES ('6', '1', 'feewff123123', '2014-12-23 01:41:46');
INSERT INTO `userstatus` VALUES ('7', '1', 'People are static', '2014-12-23 01:42:24');
INSERT INTO `userstatus` VALUES ('8', '1', 'Hello world!', '2014-12-24 05:12:00');
INSERT INTO `userstatus` VALUES ('9', '1', 'Wrong turn!', '2014-12-24 05:35:29');
INSERT INTO `userstatus` VALUES ('10', '1', 'New world', '2014-12-26 19:35:34');
INSERT INTO `userstatus` VALUES ('11', '3', 'hello 1', '2014-12-27 05:45:02');
INSERT INTO `userstatus` VALUES ('12', '3', 'hello 2', '2014-12-27 05:46:37');
INSERT INTO `userstatus` VALUES ('13', '1', 'Saying new things', '2014-12-28 04:57:41');

-- ----------------------------
-- View structure for `friendrequestdisplay`
-- ----------------------------
DROP VIEW IF EXISTS `friendrequestdisplay`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `friendrequestdisplay` AS select `u1`.`FriendRequestID` AS `FriendRequestID`,`u1`.`SenderUserID` AS `SenderUserID`,`u1`.`ToWhomUserID` AS `ToWhomUserID`,`u1`.`IsAccept` AS `IsAccept`,`u1`.`Message` AS `Message`,`u1`.`IsSeen` AS `IsSeen`,`user`.`Name` AS `SenderName`,`u2`.`Name` AS `ReceiverName` from ((`user` join `friendrequest` `u1` on((`u1`.`SenderUserID` = `user`.`UserID`))) join `user` `u2` on((`u1`.`ToWhomUserID` = `u2`.`UserID`))) ;

-- ----------------------------
-- View structure for `lastchatsessionid`
-- ----------------------------
DROP VIEW IF EXISTS `lastchatsessionid`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `lastchatsessionid` AS select `chatsession`.`ChatSessionID` AS `ID` from `chatsession` order by `chatsession`.`ChatSessionID` desc limit 1 ;

-- ----------------------------
-- View structure for `messagerecent`
-- ----------------------------
DROP VIEW IF EXISTS `messagerecent`;
