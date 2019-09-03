DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user_messages;
DROP TABLE IF EXISTS usr;

CREATE TABLE user_role (
    user_id bigint NOT NULL,
    roles character varying(255)
);

CREATE TABLE usr (
    id bigint NOT NULL,
    active boolean NOT NULL,
    password character varying(255),
    username character varying(255),
    email character varying(255),
    activationcode character varying(255)
);

--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: develop
--

COPY public.user_role (user_id, roles) FROM stdin;
1	ADMIN
2	USER
\.

insert into public.usr values (1, true, '12345', 'root', 'root@mail.com', '');
insert into public.usr values (2, false, '12345', 'user', 'user@mail.com', '');
