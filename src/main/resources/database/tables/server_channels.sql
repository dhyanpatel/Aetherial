-- Table: public.server_channels

-- DROP TABLE public.server_channels;

CREATE TABLE public.server_channels
(
    server_id bigint NOT NULL,
    channel_id bigint NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE public.server_channels
    OWNER to postgres;