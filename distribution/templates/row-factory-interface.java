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

public interface __table_name_camel_singular__RowFactory<R extends __table_name_camel_singular__Row> extends Factory<R> {
	
	/* UNIQUES */
	// start uniques
	R loadBy__unique_column_name_camel__(Connection conn, __unique_java_type__ value) throws Exception;
	// end uniques
	
}