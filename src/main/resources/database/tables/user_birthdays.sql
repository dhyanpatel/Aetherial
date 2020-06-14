-- Table: public.user_birthdays

-- DROP TABLE public.user_birthdays;

CREATE TABLE public.user_birthdays
(
    user_id bigint NOT NULL,
    birthday date NOT NULL,
    CONSTRAINT "UserBirthdays_pkey" PRIMARY KEY (user_id)
)

TABLESPACE pg_default;

ALTER TABLE public.user_birthdays
    OWNER to postgres;