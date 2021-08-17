CREATE TABLE `sys_log` (
  `log_id` varchar(32) NOT NULL COMMENT '日志ID',
  `log_info` varchar(256) DEFAULT NULL COMMENT '日志信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志信息'



CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户姓名',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户信息'