/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.100
Source Server Version : 50614
Source Host           : 192.168.1.100:3306
Source Database       : chatdatabase

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2014-12-29 20:31:20
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of chatlist
-- ----------------------------
INSERT INTO `chatlist` VALUES ('1', '1', '2', 'Alim Second Account');
INSERT INTO `chatlist` VALUES ('2', '2', '1', 'Alim First Account');
INSERT INTO `chatlist` VALUES ('3', '3', '1', 'Alim First Account');
INSERT INTO `chatlist` VALUES ('4', '1', '3', 'Alim Third Account');
INSERT INTO `chatlist` VALUES ('5', '5', '3', 'Alim Third Account');
INSERT INTO `chatlist` VALUES ('6', '3', '5', 'alim ul karim');
INSERT INTO `chatlist` VALUES ('7', '1', '5', 'alim ul karim');
INSERT INTO `chatlist` VALUES ('8', '5', '1', 'Alim First Account');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of friendrequest
-- ----------------------------
INSERT INTO `friendrequest` VALUES ('1', '2', '1', '0', 'qwdwdqdwq', '1');
INSERT INTO `friendrequest` VALUES ('2', '1', '3', '1', 'hello 3 alim i would like to add you]', '1');
INSERT INTO `friendrequest` VALUES ('3', '3', '5', '1', 'alim5 , I am alim 3 sending you request', '1');
INSERT INTO `friendrequest` VALUES ('4', '5', '1', '1', 'dq', '1');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `MessageID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `SendFromUserID` int(11) NOT NULL,
  `ReceiverUserId` int(11) NOT NULL,
  `Message` varchar(400) NOT NULL,
  `IsFileExit` tinyint(1) NOT NULL,
  `IsSeen` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MessageID`),
  KEY `MessageIDDsc` (`MessageID`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', '1', '2', 'qdwqw', '0', '0');
INSERT INTO `message` VALUES ('2', '1', '2', 'wfewf', '0', '1');
INSERT INTO `message` VALUES ('3', '2', '1', '', '0', '1');
INSERT INTO `message` VALUES ('4', '5', '1', 'dqw', '0', '1');
INSERT INTO `message` VALUES ('5', '5', '1', 'how you doing>?', '0', '1');
INSERT INTO `message` VALUES ('6', '5', '1', 'hello', '0', '1');
INSERT INTO `message` VALUES ('7', '5', '1', 'how you doing now?', '0', '1');
INSERT INTO `message` VALUES ('8', '5', '1', 'how you doing now?', '0', '1');
INSERT INTO `message` VALUES ('9', '5', '1', 'catch up later', '0', '1');
INSERT INTO `message` VALUES ('10', '5', '1', 'hoKKK', '0', '1');
INSERT INTO `message` VALUES ('11', '5', '1', 'qdwqwdqwdqw dqwqwd dqw', '0', '1');
INSERT INTO `message` VALUES ('12', '5', '1', 'low on mind', '0', '1');
INSERT INTO `message` VALUES ('13', '5', '1', 'keep going', '0', '1');
INSERT INTO `message` VALUES ('14', '5', '1', 'keep harming', '0', '1');
INSERT INTO `message` VALUES ('15', '5', '1', 'keep calm', '0', '1');
INSERT INTO `message` VALUES ('16', '5', '1', 'ko', '0', '1');
INSERT INTO `message` VALUES ('17', '5', '1', 'tomar nam ki', '0', '1');
INSERT INTO `message` VALUES ('18', '1', '5', 'ok I got it', '0', '1');
INSERT INTO `message` VALUES ('19', '1', '5', 'hey is it', '0', '1');
INSERT INTO `message` VALUES ('20', '1', '5', 'are you?', '0', '1');
INSERT INTO `message` VALUES ('21', '1', '5', 'hey alim 5', '0', '1');
INSERT INTO `message` VALUES ('22', '5', '1', 'hey', '0', '1');
INSERT INTO `message` VALUES ('23', '5', '1', 'dqwwdq', '0', '1');
INSERT INTO `message` VALUES ('24', '5', '1', 'opkjqdwdwq', '0', '1');
INSERT INTO `message` VALUES ('25', '1', '5', 'hello', '0', '1');
INSERT INTO `message` VALUES ('26', '1', '5', 'Hello 5h user , wrote from first user', '0', '1');
INSERT INTO `message` VALUES ('27', '5', '1', 'hi', '0', '1');
INSERT INTO `message` VALUES ('28', '5', '1', 'did you go offline', '0', '1');
INSERT INTO `message` VALUES ('29', '5', '1', 'did you go offline', '0', '1');
INSERT INTO `message` VALUES ('30', '5', '1', 'hey first account', '0', '1');

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
INSERT INTO `user` VALUES ('1', 'alim', 'Alim First Account', 'devorg.bd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-16 20:24:20', '0', '1', '1', '0', '1', 'Saying new things');
INSERT INTO `user` VALUES ('2', 'alim2', 'Alim Second Account', 'devorgb.d@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-26 19:45:03', '0', '1', '0', '0', '3', 'my current tst');
INSERT INTO `user` VALUES ('3', 'alim3', 'Alim Third Account', 'dev.orgbd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-26 19:45:16', '0', '1', '0', '0', '3', 'hello 2');
INSERT INTO `user` VALUES ('4', 'alim4', 'Alim Fourth Account', 'de.vorg.bd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-26 19:45:36', '0', '1', '0', '0', '0', '');
INSERT INTO `user` VALUES ('5', 'alim5', 'alim ul karim', 'dev.org.bd@gmail.com', '3DA541559918A808C2402BBA5012F6C60B27661C', '2014-12-29 05:35:46', '0', '1', '0', '0', '0', 'alim 5 state');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

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
INSERT INTO `userstatus` VALUES ('14', '2', 'my current tst', '2014-12-29 15:28:18');
INSERT INTO `userstatus` VALUES ('15', '5', 'alim 5 state', '2014-12-29 15:50:46');

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
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `messagerecent` AS select `message`.`MessageID` AS `MessageID`,`message`.`SendFromUserID` AS `SendFromUserID`,`message`.`ReceiverUserId` AS `ReceiverUserId`,`message`.`Message` AS `Message`,`message`.`IsFileExit` AS `IsFileExit`,`message`.`IsSeen` AS `IsSeen` from `message` order by `message`.`MessageID` desc ;

-- ----------------------------
-- View structure for `newmessageview`
-- ----------------------------
DROP VIEW IF EXISTS `newmessageview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `newmessageview` AS select count(`message`.`MessageID`) AS `Count`,`message`.`ReceiverUserId` AS `ReceiverUserId`,`message`.`SendFromUserID` AS `SendFromUserID` from `message` where (`message`.`IsSeen` = 0) group by `message`.`ReceiverUserId`,`message`.`SendFromUserID` ;

-- ----------------------------
-- View structure for `towhomaliaswhat`
-- ----------------------------
DROP VIEW IF EXISTS `towhomaliaswhat`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `towhomaliaswhat` AS select `user`.`UserID` AS `UserID`,`chatlist`.`OriginalUserID` AS `ToWhomUserID`,`user`.`Username` AS `Username`,`user`.`Email` AS `Email`,`chatlist`.`AliasAs` AS `AliasAs`,`user`.`CurrentStatus` AS `CurrentStatus`,`user`.`IsOnline` AS `IsOnline`,`user`.`CurrentActiveState` AS `CurrentActiveState` from (`chatlist` left join `user` on((`user`.`UserID` = `chatlist`.`RelatedUserID`))) ;

-- ----------------------------
-- View structure for `userrecentstatus`
-- ----------------------------
DROP VIEW IF EXISTS `userrecentstatus`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `userrecentstatus` AS select `userstatus`.`UserStatusID` AS `UserStatusID`,`userstatus`.`UserID` AS `UserID`,`userstatus`.`Status` AS `Status`,`userstatus`.`Dated` AS `Dated` from `userstatus` order by `userstatus`.`UserStatusID` desc ;

-- ----------------------------
-- Procedure structure for `EmptyUserTable`
-- ----------------------------
DROP PROCEDURE IF EXISTS `EmptyUserTable`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `EmptyUserTable`()
BEGIN
	#Routine body goes here...
	DELETE from `user`;
	TRUNCATE TABLE `user`;
END
;;
DELIMITER ;
