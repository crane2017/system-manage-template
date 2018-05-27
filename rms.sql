/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 192.168.31.101:3306
 Source Schema         : mkshop

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 27/05/2018 18:34:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_m_customer
-- ----------------------------
DROP TABLE IF EXISTS `t_m_customer`;
CREATE TABLE `t_m_customer`  (
  `customer_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `member_base_pkid` int(11) NULL DEFAULT NULL COMMENT '用户基本信息pkid',
  `customer_login` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员登录用账号',
  `customer_uuid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `customer_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `customer_pwd` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员登录用密码',
  `customer_mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `customer_gender` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `customer_age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `customer_province_pkid` int(11) NULL DEFAULT NULL COMMENT '省pkid',
  `customer_city_pkid` int(11) NULL DEFAULT NULL COMMENT '市pkid',
  `customer_direct_pkid` int(11) NULL DEFAULT NULL COMMENT '区pkid',
  `customer_address` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `customer_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `customer_birthday` date NULL DEFAULT NULL COMMENT '生日',
  `customer_description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人描述',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`customer_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sys_admin_department
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_department`;
CREATE TABLE `t_sys_admin_department`  (
  `admin_dpt_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `dpt_parent_pkid` int(11) NULL DEFAULT NULL COMMENT '父部门id',
  `store_id` int(11) NULL DEFAULT NULL COMMENT '门店id',
  `admin_dpt_key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门代号(英文名)',
  `admin_dpt_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门中文名',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`admin_dpt_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_department
-- ----------------------------
INSERT INTO `t_sys_admin_department` VALUES (1, NULL, NULL, 'QSummary', '总部', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:15:20', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:15:20', '');
INSERT INTO `t_sys_admin_department` VALUES (2, 1, NULL, 'MKT', '市场部', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:16:02', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:16:02', '');
INSERT INTO `t_sys_admin_department` VALUES (3, 1, NULL, 'ADMIN', '行政部', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:16:21', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:16:21', '');
INSERT INTO `t_sys_admin_department` VALUES (4, 1, NULL, 'WAREHOUSE', '仓库', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:16:43', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-27 13:23:53', '');
INSERT INTO `t_sys_admin_department` VALUES (5, 1, NULL, 'OP', '运营部', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:16:57', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:47:54', '');
INSERT INTO `t_sys_admin_department` VALUES (6, 1, NULL, 'STORE', '门店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:17:28', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:17:28', '');
INSERT INTO `t_sys_admin_department` VALUES (7, 6, NULL, 'QSRL', '静安芮欧百货店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:18:53', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:18:53', '');
INSERT INTO `t_sys_admin_department` VALUES (8, 6, NULL, 'QSPD', '浦东嘉里城店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:19:16', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:19:16', '');
INSERT INTO `t_sys_admin_department` VALUES (9, 6, NULL, 'QSHQ', '虹桥南丰城店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:19:38', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:19:38', '');
INSERT INTO `t_sys_admin_department` VALUES (10, 6, NULL, 'QSHBD', '新天地湖滨道店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:19:58', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:19:58', '');
INSERT INTO `t_sys_admin_department` VALUES (11, 6, NULL, 'QSJC', '闸北大悦城店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:20:39', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:20:39', '');
INSERT INTO `t_sys_admin_department` VALUES (12, 6, NULL, 'QHKR', '杭州嘉里中心店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:21:07', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:21:07', '');
INSERT INTO `t_sys_admin_department` VALUES (13, 6, NULL, 'QSXZ', '上海莘庄店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:21:25', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:21:25', '');
INSERT INTO `t_sys_admin_department` VALUES (14, 6, NULL, 'QSHKC', '上海虹口龙之梦店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:21:40', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:21:40', '');
INSERT INTO `t_sys_admin_department` VALUES (15, 6, NULL, 'QHYY', '杭州远洋乐堤港店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:22:12', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:22:12', '');
INSERT INTO `t_sys_admin_department` VALUES (16, 6, NULL, 'QWHJ', '武汉荟聚店', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:22:33', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:22:33', '');
INSERT INTO `t_sys_admin_department` VALUES (17, 1, NULL, 'NonCore', '非主营业务（线上和场外）', 0, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:23:09', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-31 17:23:09', '');
INSERT INTO `t_sys_admin_department` VALUES (18, 1, NULL, 'PRODUCT', '产品部', 0, 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:48:25', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:48:25', '');
INSERT INTO `t_sys_admin_department` VALUES (19, 1, NULL, 'FINANCE', '财务部', 0, 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:48:47', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:48:47', '');
INSERT INTO `t_sys_admin_department` VALUES (20, 1, NULL, 'PROJECT', '工程部', 0, 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:49:33', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:49:33', '');
INSERT INTO `t_sys_admin_department` VALUES (21, 1, NULL, 'BUYER', '买手', 0, 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:51:32', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-05 13:51:32', '');
INSERT INTO `t_sys_admin_department` VALUES (22, 6, NULL, 'QSZS', '中山龙之梦', 0, 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-28 14:09:36', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-28 14:15:09', '');
INSERT INTO `t_sys_admin_department` VALUES (23, 6, NULL, 'QHFCC', '合肥之心城', 0, 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-28 14:10:06', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-28 22:15:55', '');
INSERT INTO `t_sys_admin_department` VALUES (24, 6, NULL, 'QHFZXC', '合肥之心城', -1, 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-28 14:11:01', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-09-28 14:11:26', '');

-- ----------------------------
-- Table structure for t_sys_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_log`;
CREATE TABLE `t_sys_admin_log`  (
  `admin_log_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `admin_user_pkid` int(11) NOT NULL COMMENT 'admin用户pkid',
  `log_info` json NULL COMMENT 'log内容',
  `log_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ip地址',
  `log_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'URL',
  `log_result` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '结果(OK/NG)',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`admin_log_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 247 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_log
-- ----------------------------
INSERT INTO `t_sys_admin_log` VALUES (228, 1, NULL, '0:0:0:0:0:0:0:1', 'http://localhost:8087/api/login/admin', NULL, 0, 'jh', '2018-05-27 09:18:59', 'jh', '2018-05-27 09:18:59', NULL);
INSERT INTO `t_sys_admin_log` VALUES (229, 1, NULL, '0:0:0:0:0:0:0:1', 'http://localhost:8087/api/login/admin', NULL, 0, 'jh', '2018-05-27 09:20:14', 'jh', '2018-05-27 09:20:14', NULL);
INSERT INTO `t_sys_admin_log` VALUES (230, 1, NULL, '0:0:0:0:0:0:0:1', 'http://localhost:8087/api/login/admin', NULL, 0, 'jh', '2018-05-27 09:20:17', 'jh', '2018-05-27 09:20:17', NULL);
INSERT INTO `t_sys_admin_log` VALUES (231, 1, NULL, '0:0:0:0:0:0:0:1', 'http://localhost:8087/api/login/admin', NULL, 0, 'jh', '2018-05-27 10:07:25', 'jh', '2018-05-27 10:07:25', NULL);
INSERT INTO `t_sys_admin_log` VALUES (232, 1, NULL, '0:0:0:0:0:0:0:1', 'http://localhost:8087/api/login/admin', NULL, 0, 'jh', '2018-05-27 10:07:26', 'jh', '2018-05-27 10:07:26', NULL);
INSERT INTO `t_sys_admin_log` VALUES (233, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 10:17:14', 'jh', '2018-05-27 10:17:14', NULL);
INSERT INTO `t_sys_admin_log` VALUES (234, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 15:36:34', 'jh', '2018-05-27 15:36:34', NULL);
INSERT INTO `t_sys_admin_log` VALUES (235, 2, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, '6ead79b6-a501-4cd1-84ae-9b841b2953f7', '2018-05-27 15:42:40', '6ead79b6-a501-4cd1-84ae-9b841b2953f7', '2018-05-27 15:42:40', NULL);
INSERT INTO `t_sys_admin_log` VALUES (236, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 16:20:43', 'jh', '2018-05-27 16:20:43', NULL);
INSERT INTO `t_sys_admin_log` VALUES (237, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 16:52:30', 'jh', '2018-05-27 16:52:30', NULL);
INSERT INTO `t_sys_admin_log` VALUES (238, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:05:47', 'jh', '2018-05-27 17:05:47', NULL);
INSERT INTO `t_sys_admin_log` VALUES (239, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:06:59', 'jh', '2018-05-27 17:06:59', NULL);
INSERT INTO `t_sys_admin_log` VALUES (240, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:07:09', 'jh', '2018-05-27 17:07:09', NULL);
INSERT INTO `t_sys_admin_log` VALUES (241, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:11:15', 'jh', '2018-05-27 17:11:15', NULL);
INSERT INTO `t_sys_admin_log` VALUES (242, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:39:07', 'jh', '2018-05-27 17:39:07', NULL);
INSERT INTO `t_sys_admin_log` VALUES (243, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:39:07', 'jh', '2018-05-27 17:39:07', NULL);
INSERT INTO `t_sys_admin_log` VALUES (244, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:39:07', 'jh', '2018-05-27 17:39:07', NULL);
INSERT INTO `t_sys_admin_log` VALUES (245, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:39:07', 'jh', '2018-05-27 17:39:07', NULL);
INSERT INTO `t_sys_admin_log` VALUES (246, 1, NULL, '127.0.0.1', 'http://sys.com/api/login/admin', NULL, 0, 'jh', '2018-05-27 17:39:07', 'jh', '2018-05-27 17:39:07', NULL);

-- ----------------------------
-- Table structure for t_sys_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_menu`;
CREATE TABLE `t_sys_admin_menu`  (
  `menu_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `parent_pkid` int(11) NULL DEFAULT NULL COMMENT '父id',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `menu_class` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图表样式',
  `menu_order` int(11) NOT NULL COMMENT '菜单顺序',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`menu_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_menu
-- ----------------------------
INSERT INTO `t_sys_admin_menu` VALUES (1, NULL, '系统管理', '0', 'mkrms', '', 49, 0, '2', '2017-06-21 16:57:27', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-08-08 18:44:12', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (2, NULL, '商城管理', '0', 'mkmall', '', 8, 0, '2', '2017-06-21 16:57:27', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-11-05 22:03:36', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (3, NULL, '库存管理', '0', 'mkwms', '', 9, 0, '2', '2017-06-21 16:57:27', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-11-24 06:24:38', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (4, NULL, '预约管理', '0', 'mkbooking', '', 11, 0, '2', '2017-06-21 16:57:27', 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-11-24 06:25:49', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (5, NULL, '卡券管理', '0', 'mkpos', '', 20, -1, '2', '2017-11-05 22:04:45', 'jh', '2018-05-27 14:20:12', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (6, 1, '系统管理', '0', '', 'el-icon-mk-settings', 5, 0, '2', '2017-06-21 11:32:08', 'c469182c-f285-40ca-b37c-df53ca9b5715', '2017-10-20 21:34:38', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (7, 6, '帐户管理', '0', 'account', '', 6, 0, '2', '2017-06-21 11:32:08', 'jh', '2018-05-27 15:32:03', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (8, 6, '角色管理', '0', 'authority', '', 7, 0, '2', '2017-06-21 11:32:08', 'c87802d9-1822-400d-a031-37b5c161f55f', '2017-06-28 13:28:24', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (9, 6, '菜单管理', '0', 'menu', NULL, 38, 0, '2', '2017-06-24 10:26:20', 'c87802d9-1822-400d-a031-37b5c161f55f', '2017-06-27 16:54:43', NULL);
INSERT INTO `t_sys_admin_menu` VALUES (10, 6, '组织部门管理', '0', 'organization-department', '', 55, -1, 'c87802d9-1822-400d-a031-37b5c161f55d', '2017-07-08 14:51:06', 'jh', '2018-05-27 16:56:16', NULL);

-- ----------------------------
-- Table structure for t_sys_admin_menu_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_menu_auth`;
CREATE TABLE `t_sys_admin_menu_auth`  (
  `menu_auth_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `menu_pkid` int(11) NOT NULL COMMENT '菜单pkid',
  `admin_role_pkid` int(11) NULL DEFAULT NULL COMMENT '角色pkid',
  `view_auth` int(11) NOT NULL DEFAULT 0 COMMENT '浏览权限',
  `create_auth` int(11) NOT NULL DEFAULT 0 COMMENT '新增权限',
  `update_auth` int(11) NOT NULL DEFAULT 0 COMMENT '更新权限',
  `delete_auth` int(11) NOT NULL DEFAULT 0 COMMENT '删除权限',
  `audit_auth` int(11) NOT NULL DEFAULT 0 COMMENT '审核权限',
  `upload_auth` int(11) NOT NULL DEFAULT 0 COMMENT '上传权限',
  `download_auth` int(11) NOT NULL DEFAULT 0 COMMENT '下载权限',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`menu_auth_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_menu_auth
-- ----------------------------
INSERT INTO `t_sys_admin_menu_auth` VALUES (1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 13:52:26', 'jh', '2018-05-27 13:52:26', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (3, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (4, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (5, 6, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (6, 7, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (7, 8, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (8, 9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (9, 10, 1, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:06', 'jh', '2018-05-27 15:42:06', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (10, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:15', 'jh', '2018-05-27 15:42:15', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (11, 6, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:15', 'jh', '2018-05-27 15:42:15', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (12, 7, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:15', 'jh', '2018-05-27 15:42:15', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (13, 8, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:15', 'jh', '2018-05-27 15:42:15', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (14, 9, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:15', 'jh', '2018-05-27 15:42:15', NULL);
INSERT INTO `t_sys_admin_menu_auth` VALUES (15, 10, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'jh', '2018-05-27 15:42:15', 'jh', '2018-05-27 15:42:15', NULL);

-- ----------------------------
-- Table structure for t_sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_role`;
CREATE TABLE `t_sys_admin_role`  (
  `admin_role_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `admin_role_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员角色英文名',
  `admin_role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员角色名',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`admin_role_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_role
-- ----------------------------
INSERT INTO `t_sys_admin_role` VALUES (1, 'ADMIN', '最高权限管理员', 0, 'jh', '2018-05-27 10:54:51', 'jh', '2018-05-27 10:54:51', NULL);
INSERT INTO `t_sys_admin_role` VALUES (2, 'RMS', 'RMS管理员', 0, 'jh', '2018-05-27 15:08:06', 'jh', '2018-05-27 15:08:51', NULL);

-- ----------------------------
-- Table structure for t_sys_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_user`;
CREATE TABLE `t_sys_admin_user`  (
  `admin_user_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `admin_user_login` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登录用账号',
  `admin_user_uuid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员UUID',
  `admin_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
  `admin_user_pwd` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `admin_user_email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'email',
  `user_type` int(11) NULL DEFAULT NULL COMMENT '用户类型',
  `store_id` int(11) NULL DEFAULT NULL COMMENT '门店id',
  `weapp_open_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信小程序openid',
  `public_account_open_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信公众号openid',
  `wechat_union_id` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信union_id',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '开始日期',
  `expire_date` datetime(0) NULL DEFAULT NULL COMMENT '截止日期',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`admin_user_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_user
-- ----------------------------
INSERT INTO `t_sys_admin_user` VALUES (1, 'admin', 'jh', '管理员', '65829e542dd151f443cc997270c61e78c042a82d687cc13844bf2c1813714600', 'test@qq.com', 1, NULL, NULL, NULL, NULL, '2018-05-26 09:14:47', '2018-12-01 09:14:54', 0, 'jh', '2018-05-27 09:15:32', 'jh', '2018-05-27 10:07:14', NULL);
INSERT INTO `t_sys_admin_user` VALUES (2, 'rms', '6ead79b6-a501-4cd1-84ae-9b841b2953f7', 'rms管理员', '65829e542dd151f443cc997270c61e78c042a82d687cc13844bf2c1813714600', 'test@163.com', NULL, NULL, NULL, NULL, NULL, '2018-05-27 14:32:25', '2019-05-27 14:32:26', 0, 'jh', '2018-05-27 15:07:09', 'jh', '2018-05-27 15:07:09', NULL);

-- ----------------------------
-- Table structure for t_sys_admin_user_dpt
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_user_dpt`;
CREATE TABLE `t_sys_admin_user_dpt`  (
  `admin_user_dpt_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `admin_user_pkid` int(11) NOT NULL COMMENT '用户pkid',
  `admin_dpt_pkid` int(11) NOT NULL COMMENT '部门pkid',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`admin_user_dpt_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_user_dpt
-- ----------------------------
INSERT INTO `t_sys_admin_user_dpt` VALUES (1, 1, 1, 0, 'jh', '2018-05-27 14:31:38', 'jh', '2018-05-27 14:31:38', NULL);
INSERT INTO `t_sys_admin_user_dpt` VALUES (2, 2, 1, 0, 'jh', '2018-05-27 15:07:09', 'jh', '2018-05-27 15:07:09', NULL);

-- ----------------------------
-- Table structure for t_sys_admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_admin_user_role`;
CREATE TABLE `t_sys_admin_user_role`  (
  `admin_user_role_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `admin_user_pkid` int(11) NOT NULL COMMENT '用户id',
  `admin_role_pkid` int(11) NOT NULL COMMENT '角色pkid',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`admin_user_role_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_admin_user_role
-- ----------------------------
INSERT INTO `t_sys_admin_user_role` VALUES (1, 1, 1, 0, 'jh', '2018-05-27 12:26:47', 'jh', '2018-05-27 12:26:47', NULL);
INSERT INTO `t_sys_admin_user_role` VALUES (2, 2, 2, 0, 'jh', '2018-05-27 15:08:24', 'jh', '2018-05-27 15:08:24', NULL);

-- ----------------------------
-- Table structure for t_sys_picture
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_picture`;
CREATE TABLE `t_sys_picture`  (
  `picture_pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pkid',
  `picture_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片名',
  `picture_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片URL',
  `picture_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片类型',
  `picture_description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `picture_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `valid_date_start` datetime(0) NULL DEFAULT NULL COMMENT '有效期开始日',
  `valid_date_end` datetime(0) NULL DEFAULT NULL COMMENT '有效期截止日',
  `ver` int(11) NOT NULL DEFAULT 0 COMMENT 'version',
  `cid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'creator uuid',
  `cdt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create date',
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'updater uuid',
  `udt` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update date',
  `note` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`picture_pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
