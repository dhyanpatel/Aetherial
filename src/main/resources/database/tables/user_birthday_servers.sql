-- Table: public.user_birthday_servers

-- DROP TABLE public.user_birthday_servers;

CREATE TABLE public.user_birthday_servers
(
    user_id bigint NOT NULL,
    server_id bigint NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE public.user_birthday_servers
    OWNER to postgres;