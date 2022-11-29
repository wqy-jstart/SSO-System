-- sso

DROP DATABASE IF EXISTS sso;
CREATE DATABASE sso;
use sso;
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
    username     varchar(50)         NOT NULL COMMENT '用户名',
    password     char(64)    DEFAULT NULL COMMENT '密码(密文)',
    nickname     varchar(25) DEFAULT NULL COMMENT '昵称',
    gmt_create   datetime    DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime    DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='本地用户表';
INSERT INTO user(username, password, nickname, gmt_create, gmt_modified)
VALUES ('root', '123456', '超级管理员', '2022-11-20 19:43:53', '2022-11-20 19:43:53');

DROP TABLE IF EXISTS git;
CREATE TABLE git
(
    id           int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
    login        varchar(25)  DEFAULT NULL COMMENT '用户名',
    name         varchar(50)  DEFAULT NULL COMMENT '昵称',
    email        varchar(50)  DEFAULT NULL COMMENT '电子邮箱',
    bio          varchar(255) DEFAULT NULL COMMENT '个签',
    avatar_url   varchar(255) DEFAULT NULL COMMENT '头像路径',
    public_repos int unsigned     NOT NULL COMMENT '公开作品数量',
    followers    int unsigned     NOT NULL COMMENT '粉丝数量',
    following    int unsigned     NOT NULL COMMENT '关注数量',
    stared       int unsigned     NOT NULL COMMENT '星选集数量',
    watched      int unsigned     NOT NULL comment '浏览量',
    gmt_create   datetime     DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime     DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='第三方gitee用户登录表';
