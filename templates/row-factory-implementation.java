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

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.RecordNotFoundException;
import com.lagovistatech.database.Table;

public class __table_name_camel_singular__RowFactoryImp<R extends __table_name_camel_singular__Row> implements __table_name_camel_singular__RowFactory<R> {
	protected __table_name_camel_singular__RowFactoryImp() {}
	
	@SuppressWarnings("unchecked")
	public R create() { return (R) new __table_name_camel_singular__RowImp(new HashMap<String, Object>()); }

	/* UNIQUES */
	// start uniques
	public R loadBy__unique_column_name_camel__(Connection conn, __unique_java_type__ value) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("__table_name__") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("__unique_column_name__") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", value);
		
		Table<R> table = conn.fill(this, sql, params);
		if(table.size() != 1)
			throw new RecordNotFoundException("Could not load unique row for '__unique_column_name__' having a value of " + value.toString() + "!");
		
		return table.get(0);
	}
	// end uniques
	
}
