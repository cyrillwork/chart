insert into usr (id, username, password, email, active)
    values (42, 'admin', '12345', 'admin@mail.com', true);

insert into user_role (user_id, roles)
    values (42, 'ADMIN');
