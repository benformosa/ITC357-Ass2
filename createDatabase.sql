create database ST11429074email;
create table ST11429074email.users (
  username varchar(40) not null,
  hashedpassword blob not null,
  salt blob not null,
  primary key (username));
alter table ST11429074email.users auto_increment = 1;
