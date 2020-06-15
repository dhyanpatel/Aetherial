-- FUNCTION: public.fn_set_user_birthday(bigint, date)

-- DROP FUNCTION public.fn_set_user_birthday(bigint, date);

CREATE OR REPLACE FUNCTION public.fn_set_user_birthday(
	_user_id bigint,
	_birthday date)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$BEGIN
INSERT INTO user_birthdays(user_id, birthday)
VALUES (_user_id, _birthday);
RETURN (SELECT COUNT(*) FROM user_birthdays WHERE user_id = _user_id);
END;$BODY$;

ALTER FUNCTION public.fn_set_user_birthday(bigint, date)
    OWNER TO postgres;
