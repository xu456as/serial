CREATE TABLE test
(
    `id` bigint primary key comment '主键'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='测试';

select  * from  user_group;

use serial;
drop  table `user_group`;
CREATE TABLE `user_team`
(
    `id`           bigint AUTO_INCREMENT comment '主键id',
    `group_id`     varchar(64) NOT NULL DEFAULT '' COMMENT '凭证',
    `token`        varchar(64) NOT NULL DEFAULT '' COMMENT '登录的凭证',
    `is_deleted`   tinyint     NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `gmt_create`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_groupid` (`group_id`, `is_deleted`),
    KEY `idx_gmtcreate` (`gmt_create`),
    KEY `idx_gmtmodified` (`gmt_modified`)
) ENGINE = InnoDB
  default charset = 'utf8'
  AUTO_INCREMENT = 1 comment '用户组';

drop table image_meta;
CREATE TABLE image_meta
(
    `id`           bigint AUTO_INCREMENT comment '主键id',
    `group_id`     varchar(64) NOT NULL DEFAULT '' COMMENT '凭证',
    `name`         varchar(64) NOT NULL DEFAULT '' COMMENT '',
    `file_hash`    varchar(64) NOT NULL DEFAULT '' COMMENT 'file_hash',
    `gmt_create`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_groupid_filehash` (`group_id`, `file_hash`),
    KEY `idx_gmtcreate` (`gmt_create`),
    KEY `idx_gmtmodified` (`gmt_modified`)
) ENGINE = InnoDB
  default charset = 'utf8'
  AUTO_INCREMENT = 1 comment 'image_meta';


