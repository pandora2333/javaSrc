drop database if exists cloudDB01;
create database cloudDB01 character set utf8;
use cloudDB01;
create table dept
(
  deptn0 bigint not null primary key auto_increment,
  dname varchar(60),
  db_source varchar(60) 
);
insert into dept(dname,db_source) values('开发部',DATABASE());
insert into dept(dname,db_source) values('人事部',DATABASE());
insert into dept(dname,db_source) values('财务部',DATABASE());
insert into dept(dname,db_source) values('市场部',DATABASE());
insert into dept(dname,db_source) values('运维部',DATABASE());
select * from dept;
