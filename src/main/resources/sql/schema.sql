drop table if exists person;

create table person (
    id int unsigned auto_increment,
    name varchar(100),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;