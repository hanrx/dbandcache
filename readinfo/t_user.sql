--数据库初始化脚本
SET NAMES  utf8;
 
 SET FOREIGN_KEY_CHECKS = 0;
 
 DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
id bigint(20) NOT NULL AUTO_INCREMENT,
name varchar(50) NOT NULL  ,
phone varchar(30) NOT NULL  ,
PRIMARY KEY(id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO t_user VALUES('1','小娜','15088887777');
COMMIT;
 SET FOREIGN_KEY_CHECKS = 1;

