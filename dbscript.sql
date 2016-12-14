drop table weeklywinners;
drop table cart;
drop table media;
drop table users;

CREATE TABLE users
(
email		VARCHAR(50) not null constraint email_pk primary key,
password	VARCHAR(30) not null,
fullname	VARCHAR(100) not null,
subscribed	BOOLEAN default false,
userlevel	VARCHAR(30) default 'standard',
phonenumber	VARCHAR(20) not null,
verified 	BOOLEAN default false
);

CREATE TABLE media
(
uid int not null generated always as identity constraint media_pk primary key,
url blob,
price double not null,
author VARCHAR(50) references users(email),
rating int not null default 0,
tags varchar(200),
youtubelink varchar(100)
);

CREATE TABLE weeklywinners
(
win_date date constraint win_key primary key,
winner_media_id int references media(uid),
beenpaid boolean default false
);

CREATE TABLE cart
(
cart_uid int not null generated always as identity constraint cart_key primary key,
email VARCHAR(50) references users(email),
media_id int references media(uid)
);

CREATE TABLE sponsers (
	url_list VARCHAR(200)
);

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('ahinh@ilstu.edu','secret','Andrew Hinh',true,'admin','8476685186');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('ashesh@ilstu.edu','password','Amit Shesh',false,'standard','3098881234');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('temp@ilstu.edu','password','Bobby Flay',false,'standard','1234567898');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('jalltop@ilstu.edu','password','Jesse Alltop',true,'admin','3098887777');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('artinstitute@ai.com','password','Michael Mike',true,'standard','7734871248');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('chou7@ilstu.edu','password','Cindy H',true,'standard','309887944');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('alltopafi@gmail.com','password','Jesse Alltawp',true,'admin','3094561237');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('rdevitt@ilstu.edu','password','Randy Devitt',true,'admin','3098881234');

INSERT INTO USERS (email,password,fullname,subscribed,userlevel,phonenumber) 
VALUES ('jgarc@ilstu.edu','password','Julian Garcia',true,'admin','3091234657');

INSERT INTO SPONSERS(url_list)
VALUES ('www.amazon.com');
