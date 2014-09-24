create database ST11429074email;
create table ST11429074email.users (
  id integer not null auto_increment,
  username varchar(40) not null,
  hashedpassword blob not null,
  salt blob not null,
  primary key (id));
alter table ST11429074email.users auto_increment = 1;