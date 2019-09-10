delete from user_role;
delete from usr;

insert into usr (id, username, password, email, active)
values (1, 'admin', '$2a$08$SivvfTwjseKsnzjRsSEzd..X9WqAAlx8GvpV9pWf2FhmuESFtCx7O', 'admin@mail.com', true);

insert into usr (id, username, password, email, active)
values (2, 'user', '$2a$08$SivvfTwjseKsnzjRsSEzd..X9WqAAlx8GvpV9pWf2FhmuESFtCx7O', 'user@mail.com', true);

insert into usr (id, username, password, email, active)
values (3, 'user2', '$2a$08$SivvfTwjseKsnzjRsSEzd..X9WqAAlx8GvpV9pWf2FhmuESFtCx7O', 'user2@mail.com', true);

insert into user_role (user_id, roles)
values (1, 'ADMIN');

insert into user_role (user_id, roles)
values (2, 'USER');

insert into user_role (user_id, roles)
values (3, 'USER');

alter sequence hibernate_sequence restart with 4;
