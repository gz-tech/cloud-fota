/*
 Navicat Premium Data Transfer

 Source Server         : huawei
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 10.164.126.60:3306
 Source Schema         : cloudplatform

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 15/11/2021 16:05:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fota_version
-- ----------------------------
DROP TABLE IF EXISTS `fota_version`;
CREATE TABLE `fota_version`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '版本名',
  `logs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '版本日志',
  `date` datetime NOT NULL COMMENT '发布时间',
  `fileid` int NULL DEFAULT NULL COMMENT '关联包id',
  `status` int NULL DEFAULT NULL COMMENT '发布状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
