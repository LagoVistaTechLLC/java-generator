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

import com.lagovistatech.Factory;
import com.lagovistatech.database.Connection;
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
	
	/* CHILDREN */
	// start children
	<R extends __child_child_table_camel_singular__Row> Table<R> load__child_child_table_camel_plural__By__child_child_column_camel__EqualsMy__child_parent_column_camel__(Connection conn, __child_child_table_camel_singular__RowFactory<R> factory) throws Exception;	
	// end children	

	/* PARENTS */
	// start parents
	<R extends __parent_parent_table_camel_singular__Row> R load__parent_parent_table_camel_singular__ByMy__parent_child_column_camel__(Connection conn, __parent_parent_table_camel_singular__RowFactory<R> factory) throws Exception;	
	// end parents	

}
