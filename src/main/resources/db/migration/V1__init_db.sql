create sequence hibernate_sequence start 1 increment 1;

create table user_messages (
	id bigint not null,
	create_at timestamp, 
	text varchar(255) not null, 
	user_id bigint, primary key (id));

create table user_role (
	user_id bigint not null,
	roles varchar(255)
);

create table usr (
	id bigint not null,
	activation_code varchar(255), 
	active boolean not null, 
	email varchar(64) not null, 
	password varchar(20) not null, 
	username varchar(30) not null, primary key (id)
);

alter table if exists user_messages 
	add constraint massage_user_fk 
	foreign key (user_id) references usr;

alter table if exists user_role 
	add constraint user_role_user_fk 
	foreign key (user_id) references usr;
