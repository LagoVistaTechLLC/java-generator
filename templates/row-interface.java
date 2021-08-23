package __package__.generated;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.Row;
import com.lagovistatech.database.Table;
import com.lagovistatech.database.Versioned;

@SuppressWarnings("unused")
public interface __table_name_camel_singular__Row extends Row, Versioned {
	static final String TABLE_NAME = "__table_name__";
	static final String SECURABLE = "__table_name_md5__";

	/* COLUMNS */
	// start columns
	static final String __column_name_constant__ = "__column_name__";
	__column_java_type__ get__column_name_camel__();
	void set__column_name_camel__(__column_java_type__ value);
	// end columns
	
	
	/* UNIQUES */
	// start uniques
	public static <R extends __table_name_camel_singular__Row> R loadBy__unique_column_name_camel__(Connection conn, Table<R> table, __unique_java_type__ value) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + TABLE_NAME + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("__unique_column_name__") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", value);
		
		table.clear();
		conn.fill(table, sql, params);
		if(table.size() != 1)
			throw new Exception("Could not load unique row for '__unique_column_name__' having a value of " + value.toString() + "!");
		
		return table.get(0);
	}
	// end uniques
	
	
	/* CHILDREN */
	// start children
	<R extends __child_child_table_camel_singular__Row> Table<R> load__child_child_table_camel_plural__By__child_child_column_camel__EqualsMy__child_parent_column_camel__(Connection conn, Table<R> table) throws Exception;	
	// end children	

	
	/* PARENTS */
	// start parents
	<R extends __parent_parent_table_camel_singular__Row> R load__parent_parent_table_camel_singular__ByMy__parent_child_column_camel__(Connection conn, Table<R> table) throws Exception;	
	// end parents	

}
