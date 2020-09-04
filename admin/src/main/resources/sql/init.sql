-----------------------------------------
-- MySQL初始化
-----------------------------------------

CREATE USER 'springtest'@'%' IDENTIFIED BY '123456';
CREATE DATABASE springtest;
GRANT ALL ON springtest.* to 'springtest'@'%';
FLUSH PRIVILEGES;