CREATE DATABASE IF NOT EXISTS `docker_test`;

USE `docker_test`;

CREATE TABLE docker_test.kitten
(
    id       int auto_increment primary key,
    name     varchar(20) null,
    gender   int         null,
    birthday date        null
) charset = utf8;


INSERT INTO docker_test.kitten (name, gender, birthday)
VALUES ('miaomiao1', 1, CURRENT_DATE);
INSERT INTO docker_test.kitten (name, gender, birthday)
VALUES ('miaomiao2', 0, CURRENT_DATE);
INSERT INTO docker_test.kitten (name, gender, birthday)
VALUES ('miaomiao3', 1, CURRENT_DATE);