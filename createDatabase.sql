create database ST11429074email;
create table ST11429074email.users (
  username varchar(40) not null,
  hashedpassword blob not null,
  salt blob not null,
  primary key (username));
create table ST11429074email.messages (
  id int not null auto_increment,
  sender varchar(40) not null,
  recipient varchar(40) not null,
  subject varchar(200) not null,
  body text,
  primary key (id));
alter table ST11429074email.messages auto_increment=1;
