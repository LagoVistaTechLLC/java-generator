SELECT * FROM (
	SELECT
		rc.constraint_name AS "foreign_key",
		parent.table_name AS "parent_table",
		parent.column_name AS "parent_column",
		child.table_name AS "child_table",
		child.column_name AS "child_column"
	FROM 
		information_schema.referential_constraints rc
		JOIN information_schema.key_column_usage parent ON parent.constraint_name = rc.unique_constraint_name  
		JOIN information_schema.key_column_usage child ON child.constraint_name  = rc.constraint_name 
) TBL