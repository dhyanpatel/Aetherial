-- Table: public.server_channels

-- DROP TABLE public.server_channels;

CREATE TABLE public.server_channels
(
    server_id bigint NOT NULL,
    channel_id bigint NOT NULL,
    CONSTRAINT "ServerChannels_pkey" PRIMARY KEY (server_id)

)

TABLESPACE pg_default;

ALTER TABLE public.server_channels
    OWNER to postgres;