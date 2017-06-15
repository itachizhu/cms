CREATE DATABASE IF NOT EXISTS `cms` DEFAULT CHARACTER SET utf8mb4;

grant all privileges on cms.* to 'cmsdemo'@'%' identified by 'cmsdemo@123';

flush privileges;

CREATE TABLE `cms`.`admin_users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `user_no` VARCHAR(20) NULL COMMENT '员工号',
  `user_name` VARCHAR(20) NULL COMMENT '用户名',
  `pass_word` VARCHAR(64) NULL COMMENT '用户密码',
  `role_id` BIGINT NULL COMMENT '权限id',
  `role_no` VARCHAR(50) NULL COMMENT '权限编码',
  `role_name` VARCHAR(50) NULL COMMENT '权限名称',
  `last_login_ip` VARCHAR(20) NULL COMMENT '最后登录ip',
  `last_login_time` BIGINT NULL COMMENT '最后登录时间',
  `last_logout_time` BIGINT NULL COMMENT '最后正常登出时间',
  `login_times` INT NULL DEFAULT 0 COMMENT '登录次数',
  `admin_list` VARCHAR(1024) NULL COMMENT '',
  `dept_id` BIGINT NULL COMMENT '部门id',
  `dept_no` VARCHAR(50) NULL COMMENT '部门编码',
  `duty` VARCHAR(50) NULL COMMENT '职位',
  `relation` VARCHAR(50) NULL COMMENT '',
  `create_time` BIGINT NULL COMMENT '创建时间',
  `update_time` BIGINT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除？',
  PRIMARY KEY (`id`)  COMMENT '主键索引')
ENGINE = InnoDB
AUTO_INCREMENT=2
DEFAULT CHARACTER SET = utf8mb4
COLLATE=utf8mb4_unicode_ci
COMMENT = '后台管理用户表';

INSERT INTO `cms`.`admin_users`
(
  `id`,
  `user_no`,
  `user_name`,
  `pass_word`,
  `role_id`,
  `role_no`,
  `role_name`,
  `last_login_ip`,
  `last_login_time`,
  `last_logout_time`,
  `login_times`,
  `dept_id`,
  `dept_no`,
  `duty`,
  `create_time`,
  `update_time`)
VALUES
  (
    1,
    '00000001',
    'admin',
    password('111111'),
    1,
    '0001',
    '超级管理员',
    '127.0.0.1',
    1497335025953,
    1497335025953,
    1,
    1,
    '0001',
    'CTO',
    1497335025953,
    1497335025953
  );

CREATE TABLE `cms`.`admin_menus` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父节点ID',
  `menu_no` VARCHAR(50) NULL COMMENT '栏目编码',
  `menu_name` varchar(64) DEFAULT NULL COMMENT '栏目名称',
  `menu_url` VARCHAR(45) NULL COMMENT '栏目链接',
  `menu_type` INT NULL DEFAULT 0 COMMENT '栏目类型',
  `menu_order` INT NULL COMMENT '栏目排序',
  `is_child` TINYINT NULL COMMENT '是否叶子节点',
  PRIMARY KEY (`id`)  COMMENT '主键索引')
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT = '后台管理菜单表';

INSERT INTO `admin_menus` (`id`,`parent_id`,`menu_no`,`menu_name`,`menu_url`,`menu_type`,`menu_order`,`is_child`) VALUES (1,0,'001','账号管理','',0,1,0);
INSERT INTO `admin_menus` (`id`,`parent_id`,`menu_no`,`menu_name`,`menu_url`,`menu_type`,`menu_order`,`is_child`) VALUES (2,1,'001001','管理员管理','/admin/users',0,1,1);