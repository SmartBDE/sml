-----------------------------------------
-- 测试用的数据表person
-----------------------------------------
drop table if exists person;

create table person (
    id int unsigned auto_increment,
    name varchar(100),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----------------------------------------
-- 系统配置表
-----------------------------------------
drop table if exists configs;

create table configs (
    cname varchar(100),
    ckey varchar(100),
    cvalue varchar(100),
    CONSTRAINT sys_pk_configs PRIMARY KEY (cname, ckey)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
