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

 Date: 15/11/2021 16:04:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fota_file
-- ----------------------------
DROP TABLE IF EXISTS `fota_file`;
CREATE TABLE `fota_file`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bucketname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `objectname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `endpoint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `filesize` bigint NULL DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `filelogs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `storageProviderName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ak` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sk` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `apkType` tinyint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
