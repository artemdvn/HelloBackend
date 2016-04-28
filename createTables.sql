DROP DATABASE IF EXISTS helloBackend;

CREATE DATABASE helloBackend DEFAULT CHARACTER SET 'utf8';

USE helloBackend;

create table contacts
(
  id bigint unsigned not null auto_increment PRIMARY KEY,
  name varchar(255) not null
) engine=InnoDB;

set names 'utf8';

insert into contacts (name) 
values ('Zaporizke shosse str. 22');
insert into contacts (name) 
values ('Nissan Center');
insert into contacts (name) 
values ('Laguna');
insert into contacts (name) 
values ('Dnipropetrivsk, Slobozhanski avn. 127');
