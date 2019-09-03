--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.19
-- Dumped by pg_dump version 9.5.19


DROP TABLE public.user_messages;
DROP TABLE public.user_role;
DROP TABLE public.usr;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: develop
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO develop;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: user_messages; Type: TABLE; Schema: public; Owner: develop
--

CREATE TABLE public.user_messages (
    id bigint NOT NULL,
    create_at timestamp without time zone,
    text character varying(255) NOT NULL,
    user_id bigint
);


ALTER TABLE public.user_messages OWNER TO develop;

--
-- Name: user_role; Type: TABLE; Schema: public; Owner: develop
--

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    roles character varying(255)
);


ALTER TABLE public.user_role OWNER TO develop;

--
-- Name: usr; Type: TABLE; Schema: public; Owner: develop
--

CREATE TABLE public.usr (
    id bigint NOT NULL,
    activation_code character varying(255),
    active boolean NOT NULL,
    email character varying(64) NOT NULL,
    password character varying(20) NOT NULL,
    username character varying(30) NOT NULL
);


ALTER TABLE public.usr OWNER TO develop;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: develop
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, true);


--
-- Data for Name: user_messages; Type: TABLE DATA; Schema: public; Owner: develop
--

COPY public.user_messages (id, create_at, text, user_id) FROM stdin;
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: develop
--

COPY public.user_role (user_id, roles) FROM stdin;
1	ADMIN
\.


--
-- Data for Name: usr; Type: TABLE DATA; Schema: public; Owner: develop
--

COPY public.usr (id, activation_code, active, email, password, username) FROM stdin;
1	6ba57a2e-d8ff-4824-83ec-846fd8df12ac	t	root@mail.com	12345	root
\.


--
-- Name: user_messages_pkey; Type: CONSTRAINT; Schema: public; Owner: develop
--

ALTER TABLE ONLY public.user_messages
    ADD CONSTRAINT user_messages_pkey PRIMARY KEY (id);


--
-- Name: usr_pkey; Type: CONSTRAINT; Schema: public; Owner: develop
--

ALTER TABLE ONLY public.usr
    ADD CONSTRAINT usr_pkey PRIMARY KEY (id);


--
-- Name: fkfpm8swft53ulq2hl11yplpr5; Type: FK CONSTRAINT; Schema: public; Owner: develop
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkfpm8swft53ulq2hl11yplpr5 FOREIGN KEY (user_id) REFERENCES public.usr(id);


--
-- Name: fkgfi9esjf61vygvvi7reyo25qf; Type: FK CONSTRAINT; Schema: public; Owner: develop
--

ALTER TABLE ONLY public.user_messages
    ADD CONSTRAINT fkgfi9esjf61vygvvi7reyo25qf FOREIGN KEY (user_id) REFERENCES public.usr(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

