/*

	Copyright (C) 2021 Lago Vista Technologies LLC

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
	
*/
package __package__.generated;

import java.util.HashMap;

import com.lagovistatech.Factory;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.RecordNotFoundException;
import com.lagovistatech.database.Table;
import com.lagovistatech.database.VersionedRow;

@SuppressWarnings("unused")
public class __table_name_camel_singular__RowImp extends VersionedRow implements __table_name_camel_singular__Row {
	public static final String TABLE_NAME = __table_name_camel_singular__Row.TABLE_NAME;
	public static final String SECURABLE = __table_name_camel_singular__Row.SECURABLE;

	protected __table_name_camel_singular__RowImp(HashMap<String, Object> values) { super(values); }	
	
	/* COLUMNS */
	// start columns
	public __column_java_type__ get__column_name_camel__() {
		Object ret = this.get(__column_name_constant__);
		return ret == null ? null : (__column_java_type__) ret;		
	}
	public void set__column_name_camel__(__column_java_type__ value) { this.set(__column_name_constant__, value); }
	// end columns
		
	/* CHILDREN */
	// start children
	public <R extends __child_child_table_camel_singular__Row> Table<R> load__relation_many_camel__(Connection conn, __child_child_table_camel_singular__RowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("__child_child_table__") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("__child_child_column__") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.get__child_parent_column_camel__());
		
		return conn.fill(factory, sql, params);
	}
	// end children
		
	/* PARENTS */
	// start parents
	public <R extends __parent_parent_table_camel_singular__Row> R load__relation_one_camel__(Connection conn, __parent_parent_table_camel_singular__RowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("__parent_parent_table__") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("__parent_parent_column__") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.get__parent_child_column_camel__());
		
		Table<R> table = conn.fill(factory, sql, params);
		if(table.size() != 1)
			throw new RecordNotFoundException("Could not load unique row for '__parent_parent_table__'.'__parent_parent_column__' having a value of " + this.get__parent_child_column_camel__().toString() + "!");
		
		return table.get(0);
	}
	// end parents
	
}
