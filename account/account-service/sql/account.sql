USE platform_account;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `weixin_id` varchar(128) DEFAULT NULL COMMENT '微信ID',
  `office_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(128) NOT NULL COMMENT '账号',
  `password` VARCHAR(128) NOT NULL COMMENT '密码',
  `email` VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `avatar` VARCHAR(256) DEFAULT NULL COMMENT '头像URL',
  `status` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '状态 0:正常 1:锁定',
  `is_admin` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '是否管理员 0:不是 1:是',
  `password_time` datetime DEFAULT NULL COMMENT '密码时间',
  `password_ip` VARCHAR(128) DEFAULT NULL COMMENT '密码ip',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `login_ip` VARCHAR(128) DEFAULT NULL COMMENT '登录IP',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` VARCHAR(128) NOT NULL COMMENT '注册IP',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `weixin_id` (`weixin_id`),
  KEY `login_name` (`login_name`),
  KEY `email` (`email`),
  KEY `mobile` (`mobile`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='系统用户表';

DROP TABLE IF EXISTS `office`;
CREATE TABLE `office` (
  `id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父部门ID',
  `weixin_id` varchar(128) DEFAULT NULL COMMENT '微信ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '状态 0:正常 1:停用',
  `operation_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='系统部门表';

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `permission` varchar(255) NOT NULL COMMENT '权限关键字',
  `sort` int(20) DEFAULT NULL COMMENT '排序ASC',
  `url` varchar(255) NOT NULL COMMENT '链接',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `hidden` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '是否显示 0显示1隐藏',
  `status` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '状态 0:正常 1:停用',
  `operation_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='系统权限表';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `office_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `status` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '状态 0:正常 1:停用',
  `operation_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='系统角色表';

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `status` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '状态 0:正常 1:停用',
  `operation_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `permission_id` (`permission_id`),
  KEY `role_id_p` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='系统角色权限表';

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` TINYINT(2) DEFAULT 0 NOT NULL COMMENT '状态 0:正常 1:停用',
  `operation_id` bigint(20) DEFAULT NULL COMMENT '操作人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id_p` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  COMMENT='系统用户角色表';