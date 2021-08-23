package __package__.generated;

import java.util.HashMap;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.Table;
import com.lagovistatech.database.VersionedRow;

@SuppressWarnings("unused")
public class __table_name_camel_singular__RowImp extends VersionedRow implements __table_name_camel_singular__Row {
	public static final String TABLE_NAME = __table_name_camel_singular__Row.TABLE_NAME;
	public static final String SECURABLE = __table_name_camel_singular__Row.SECURABLE;

	public __table_name_camel_singular__RowImp(HashMap<String, Object> values) { super(values); }	
	
	/* COLUMNS */
	// start columns
	private __column_java_type__ __column_name_lower_camel__;
	public __column_java_type__ get__column_name_camel__() { return __column_name_lower_camel__; }
	public void set__column_name_camel__(__column_java_type__ value) { this.__column_name_lower_camel__ = value; }
	// end columns
		
	/* CHILDREN */
	// start children
	public <R extends __child_child_table_camel_singular__Row> Table<R> load__child_child_table_camel_plural__By__child_child_column_camel__EqualsMy__child_parent_column_camel__(Connection conn, Table<R> table) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("__child_child_table__") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("__child_child_column__") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.get__child_parent_column_camel__());
		
		return conn.fill(table, sql, params);
	}
	// end children
		
	/* PARENTS */
	// start parents
	public <R extends __parent_parent_table_camel_singular__Row> R load__parent_parent_table_camel_singular__ByMy__parent_child_column_camel__(Connection conn, Table<R> table) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("__parent_parent_table__") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("__parent_parent_column__") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.get__parent_child_column_camel__());
		
		table.clear();
		conn.fill(table, sql, params);
		if(table.size() != 1)
			throw new Exception("Could not load unique row for '__parent_parent_table__'.'__parent_parent_column__' having a value of " + this.get__parent_child_column_camel__().toString() + "!");
		
		return table.get(0);
	}
	// end parents
	
}
