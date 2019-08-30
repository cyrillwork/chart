CREATE TABLE user_role (
    user_id bigint NOT NULL,
    roles character varying(255)
);

CREATE TABLE usr (
    id bigint NOT NULL,
    active boolean NOT NULL,
    password character varying(255),
    username character varying(255)
);

--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: develop
--

COPY public.user_role (user_id, roles) FROM stdin;
1	ADMIN
2	USER
\.

COPY public.usr (id, active, password, username) FROM stdin;
1	t	12345	root
2	f	12345	user
\.
