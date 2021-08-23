SELECT * 
FROM 
	(
		SELECT
			kcu.table_name,
			kcu.column_name,
			tc.constraint_type,
			kcu.ordinal_position,
			(
				SELECT COUNT(*) 
				FROM information_schema.key_column_usage kcu2 
				WHERE 
					kcu.table_catalog = kcu2.table_catalog
					AND kcu.table_schema = kcu2.table_schema
					AND kcu.table_name = kcu2.table_name
					AND kcu.constraint_name = kcu2.constraint_name
			) AS "count"	
		FROM 
			information_schema.table_constraints tc
			JOIN information_schema.key_column_usage kcu ON kcu.constraint_name = tc.constraint_name 
		WHERE 
			constraint_type IN ('UNIQUE', 'PRIMARY KEY')
			AND kcu.table_name = @Table
	) TBL
WHERE 
	TBL."count" = 1