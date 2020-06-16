CREATE OR REPLACE FUNCTION public.set_birthday_channel(
	_server_id bigint,
	_channel_id bigint)
	RETURNS boolean
	LANGUAGE 'plpgsql'

	COST 100
	VOLATILE

	AS $BODY$BEGIN
	INSERT INTO server_channels(server_id, channel_id)
	VALUES (_server_id, _channel_id);
	RETURN (SELECT COUNT(*) FROM server_channels WHERE server_id = _server_id);
	END;$BODY$;

	ALTER FUNCTION public.set_birthday_channel(bigint, bigint)
		OWNER to nikhilprasad