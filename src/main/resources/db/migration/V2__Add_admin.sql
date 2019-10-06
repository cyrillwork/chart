insert into usr (id, username, password, email, active)
    values (1, 'admin', '12345', 'admin@mail.com', true);
insert into usr (id, username, password, email, active)
    values (2, 'root', '12345', 'root@mail.com', true);

insert into user_role (user_id, roles)
    values (1, 'ADMIN');

alter sequence hibernate_sequence restart with 3;
