CREATE DATABASE  IF NOT EXISTS `reddit`;
use `reddit`;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (1,'USER');
INSERT INTO `role` VALUES (2,'ADMIN');
UNLOCK TABLES;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `karma` int not null,
  `rol_role_id` int(11),
  FOREIGN KEY (`rol_role_id`) REFERENCES role(`role_id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE `user` AUTO_INCREMENT = 2;
insert into `user` (`username`, `password`, `rol_role_id`, `karma`) values ('goku', '$2a$10$FLClj7cHLDxO3EbtKYR29.PrvQxqONtYU6mkGeJQwFG6w2D.J5Bb.', 2, 0)