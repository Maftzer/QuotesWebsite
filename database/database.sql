create database if not exists quotesDB
	CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use quotesDB;

create table categories(
	id int auto_increment primary key,
    name varchar(50) not null unique
);

create table quotes(
	id int auto_increment primary key,
    text varchar(500) not null,
    author varchar (100) null,
    category_id int not null,
    created_at datetime default current_timestamp not null,
    
    foreign key (category_id) references categories(id)
    on delete restrict
);