/*
Navicat MySQL Data Transfer

Source Server         : localhost_3307
Source Server Version : 50505
Source Host           : localhost:3307
Source Database       : box

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-08-23 06:19:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_base
-- ----------------------------
DROP TABLE IF EXISTS `file_base`;
CREATE TABLE `file_base` (
  `file_base_id` varchar(64) NOT NULL,
  `file_base_real_path` varchar(1020) NOT NULL COMMENT '文件的真实路径',
  `file_base_md5` varchar(255) NOT NULL COMMENT '文件的md5',
  `file_base_state` tinyint(4) NOT NULL COMMENT '1:上传完成 2：上传未完成',
  `file_base_total_size` bigint(20) NOT NULL COMMENT '文件总大小',
  `file_base_pos` bigint(20) NOT NULL COMMENT '文件上传位置当与file_total_size一样时说明上传完成',
  `file_base_create_time` datetime NOT NULL,
  `file_base_complete_time` datetime DEFAULT NULL,
  `file_base_next_upload_time` datetime DEFAULT NULL,
  PRIMARY KEY (`file_base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_boxinfo
-- ----------------------------
DROP TABLE IF EXISTS `user_boxinfo`;
CREATE TABLE `user_boxinfo` (
  `user_id` varchar(64) NOT NULL,
  `box_size_offset` int(11) NOT NULL COMMENT '网盘大小偏移值',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file` (
  `user_file_id` varchar(64) NOT NULL,
  `user_file_name` varchar(255) NOT NULL,
  `user_fold_parent_id` varchar(64) NOT NULL,
  `user_file_create_time` datetime NOT NULL,
  `user_file_update_time` datetime NOT NULL,
  `user_file_state` tinyint(4) NOT NULL COMMENT '1：可用 2：回收站 3：逻辑上删除',
  `user_fold_top_id` varchar(64) NOT NULL,
  `create_user_id` varchar(64) NOT NULL,
  `file_base_id` varchar(64) NOT NULL,
  PRIMARY KEY (`user_file_id`),
  KEY `user_fold_parent_id` (`user_fold_parent_id`),
  KEY `user_fold_top_id` (`user_fold_top_id`),
  KEY `user_file_ibfk_1` (`file_base_id`),
  CONSTRAINT `user_file_ibfk_1` FOREIGN KEY (`file_base_id`) REFERENCES `file_base` (`file_base_id`),
  CONSTRAINT `user_file_ibfk_2` FOREIGN KEY (`user_fold_parent_id`) REFERENCES `user_fold` (`user_fold_id`),
  CONSTRAINT `user_file_ibfk_3` FOREIGN KEY (`user_fold_top_id`) REFERENCES `user_fold` (`user_fold_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_fold
-- ----------------------------
DROP TABLE IF EXISTS `user_fold`;
CREATE TABLE `user_fold` (
  `user_fold_id` varchar(64) NOT NULL,
  `user_fold_name` varchar(255) DEFAULT NULL,
  `user_fold_parent_id` varchar(64) DEFAULT NULL COMMENT '空 ：代表顶级',
  `user_fold_create_time` datetime NOT NULL,
  `user_fold_update_time` datetime NOT NULL,
  `user_fold_state` tinyint(4) NOT NULL COMMENT '1：可用 2：回收站 3：逻辑上删除',
  `user_fold_top_id` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL COMMENT '该文件夹的创建者',
  `user_fold_owner_type` tinyint(4) DEFAULT NULL COMMENT '1：个人，2：组织  3：应用，只有顶级文件夹有意义',
  `user_fold_owner_id` varchar(64) DEFAULT NULL COMMENT '文件夹的持有者id 只有顶级文件夹有意义',
  `user_fold_channel_type` tinyint(4) DEFAULT NULL COMMENT '渠道1云邮 只有顶级文件夹有意义',
  `user_fold_channel_user_id` varchar(64) DEFAULT NULL COMMENT '渠道下的用户id 只有顶级文件夹有意义',
  PRIMARY KEY (`user_fold_id`),
  UNIQUE KEY `user_fold_owner` (`user_fold_owner_type`,`user_fold_owner_id`) USING BTREE,
  UNIQUE KEY `user_fold_channel_type` (`user_fold_channel_type`,`user_fold_channel_user_id`) USING BTREE,
  KEY `user_fold_parent_id` (`user_fold_parent_id`),
  KEY `user_fold_top_id` (`user_fold_top_id`),
  CONSTRAINT `user_fold_ibfk_1` FOREIGN KEY (`user_fold_parent_id`) REFERENCES `user_fold` (`user_fold_id`),
  CONSTRAINT `user_fold_ibfk_2` FOREIGN KEY (`user_fold_top_id`) REFERENCES `user_fold` (`user_fold_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
