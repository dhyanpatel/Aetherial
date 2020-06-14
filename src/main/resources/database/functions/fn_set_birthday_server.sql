-- FUNCTION: public.fn_set_birthday_server(bigint, bigint)

-- DROP FUNCTION public.fn_set_birthday_server(bigint, bigint);

CREATE OR REPLACE FUNCTION public.fn_set_birthday_server(
	_user_id bigint,
	_server_id bigint)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE

AS $BODY$BEGIN
	INSERT INTO user_birthday_servers(user_id, server_id)
	VALUES (_user_id, _server_id);
	RETURN (SELECT COUNT(*)
			FROM user_birthday_servers
			WHERE user_id = _user_id
			AND server_id = _server_id);
END;$BODY$;

ALTER FUNCTION public.fn_set_birthday_server(bigint, bigint)
    OWNER TO postgres;
